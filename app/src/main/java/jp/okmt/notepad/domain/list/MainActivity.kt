package jp.okmt.notepad.domain.list

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import jp.okmt.notepad.R
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex
import jp.okmt.notepad.store.sqlite.MemoDatabaseStore
import jp.okmt.notepad.views.Dialogs
import jp.okmt.notepad.views.memo_list.MemoRecyclerViewAdapter
import jp.okmt.notepad.views.memo_list.OnMemoListClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MemoListActivityContract.View {
    lateinit var presenter: MemoListActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = MemoListPresenter(this)

        fab.setOnClickListener { view ->
            //todo fabクリックイベント
        }

        recyclerView.let { view ->
            view.layoutManager = LinearLayoutManager(this)
            view.adapter = MemoRecyclerViewAdapter(
                layoutInflater,
                presenter.getMemoIndexes(this).toMutableList(),
                this,
                object : OnMemoListClickListener {
                    // アイテムをクリックしたときのイベント
                    override fun onClick(view: View, position: Int, index: MemoIndex) {
                        notifySnackBar("position : $position")
                    }
                },
                object : OnMemoListClickListener {
                    // アイテムを長押ししたときのイベント
                    override fun onClick(view: View, position: Int, index: MemoIndex) {
                        Dialogs.showDialog(
                            this@MainActivity,
                            resources.getString(R.string.memo_delete_confirm),
                            index.title,
                            DialogInterface.OnClickListener { dialog, which ->
                                Memo.load(index.id, MemoDatabaseStore(this@MainActivity))
                                    .remove(MemoDatabaseStore(this@MainActivity))
                                recyclerView.removeItemDecorationAt(position)
                            }, null
                        )
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun notifySnackBar(message: String) {
        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun deleteRecyclerViewElem(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
