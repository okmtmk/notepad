package jp.okmt.notepad.domain.list

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import jp.okmt.notepad.Constants
import jp.okmt.notepad.R
import jp.okmt.notepad.domain.editor.EditActivity
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex
import jp.okmt.notepad.store.sqlite.MemoDatabaseStore
import jp.okmt.notepad.views.Dialogs
import jp.okmt.notepad.views.memo_list.MemoRecyclerViewAdapter
import jp.okmt.notepad.views.memo_list.OnMemoListClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MemoListActivityContract.View {
    companion object {
        const val RESULT_ID = "resultId"
        const val EDITOR_RETURN_RESULT_ID = 3939
        const val MEMO_ADDED_RESULT_ID = 4000
        const val MEMO_UPDATED_RESULT_ID = 4001

    }

    lateinit var presenter: MemoListActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = MemoListPresenter(this)

        fab.setOnClickListener {
            Intent(applicationContext, EditActivity::class.java).apply {
                startActivityForResult(this, EDITOR_RETURN_RESULT_ID)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        recyclerView.let { view ->
            view.layoutManager = LinearLayoutManager(this)
            view.adapter = MemoRecyclerViewAdapter(
                layoutInflater,
                presenter.getMemoIndexes(this),
                this,
                object : OnMemoListClickListener {
                    // アイテムをクリックしたときのイベント
                    override fun onClick(view: View, position: Int, index: MemoIndex) {
                        Intent(applicationContext, EditActivity::class.java).apply {
                            putExtra(Constants.MEMO_ID, index.id)
                            startActivityForResult(this, EDITOR_RETURN_RESULT_ID)
                        }
                    }
                },
                object : OnMemoListClickListener {
                    // アイテムを長押ししたときのイベント
                    override fun onClick(view: View, position: Int, index: MemoIndex) {
                        Dialogs.showMemoDeleteDialog(
                            this@MainActivity,
                            DialogInterface.OnClickListener { _, _ ->
                                presenter.deleteListItem(index)
                                Memo.load(index.id, MemoDatabaseStore(this@MainActivity)).delete()
                                recyclerView.adapter?.notifyItemRemoved(position)
                            }
                        )
                    }
                })
        }
    }

    // コンテキストメニューを使わないのでコメントアウト
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun notifySnackBar(message: String) {
        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // エディタ画面から戻ってきた時
        if (resultCode == Activity.RESULT_OK &&
            requestCode == EDITOR_RETURN_RESULT_ID &&
            data != null
        ) {
            when (data.getIntExtra(RESULT_ID, -1)) {
                MEMO_ADDED_RESULT_ID -> {
                    presenter.addMemoToList(this, data.getLongExtra(Constants.MEMO_ID, -1)).apply {
                        recyclerView.adapter?.notifyItemInserted(this)
                    }
                }

                MEMO_UPDATED_RESULT_ID -> {
                    val position = presenter.updateMemoItem(this, data.getLongExtra(Constants.MEMO_ID, -1))
                    recyclerView.adapter?.notifyItemChanged(position)
                }
            }


        }
    }
}
