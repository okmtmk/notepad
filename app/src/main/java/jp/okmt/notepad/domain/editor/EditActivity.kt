package jp.okmt.notepad.domain.editor

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import jp.okmt.notepad.Constants
import jp.okmt.notepad.R
import jp.okmt.notepad.views.Dialogs
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(), EditorActivityContract.View {

    lateinit var presenter: EditorActivityContract.Presenter

    override var title: String
        get() = titleView.text.toString()
        set(value) {
            titleView.setText(value)
        }

    override var noteText: String
        get() = noteTextView.text.toString()
        set(value) {
            noteTextView.setText(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        setTitle(R.string.edit)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        presenter = EditorActivityPresenter(this, this, intent.getLongExtra(Constants.MEMO_ID, -1))
    }

    override fun onBackPressed() {
        if (title.isEmpty()) {
            Dialogs.showConfirmReturnBackDialog(this, DialogInterface.OnClickListener { dialog, which ->
                super.onBackPressed()
            })
        } else {
            presenter.saveMemo(title, noteText).let { id ->
                if (presenter.isCreateNew) {
                    Intent().apply {
                        putExtra(Constants.MEMO_ID, id)
                        setResult(Activity.RESULT_OK, this)
                    }
                }
                super.onBackPressed()
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun notifySnackBar(message: String) {
        Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT).show()
    }
}
