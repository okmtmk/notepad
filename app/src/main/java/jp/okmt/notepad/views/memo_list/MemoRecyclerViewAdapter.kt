package jp.okmt.notepad.views.memo_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.okmt.notepad.R
import jp.okmt.notepad.memo.MemoIndex
import kotlinx.android.synthetic.main.recycler_item_memo.view.*

class MemoRecyclerViewAdapter(
    private val inflater: LayoutInflater,
    private val list: MutableList<MemoIndex>,
    private val context: Context,
    private val listener: OnMemoListClickListener,
    private val longListener: OnMemoListClickListener
) : RecyclerView.Adapter<MemoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.recycler_item_memo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.index = list[position]

        holder.itemView.setOnClickListener { view ->
            listener.onClick(view, position, list[position])
        }

        holder.itemView.setOnLongClickListener {
            longListener.onClick(it, position, list[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val title: TextView = viewItem.title
        val subTitle: TextView = viewItem.subtitle

        private var _index: MemoIndex? = null

        var index: MemoIndex?
            set(value) {
                _index = value
                value ?: return

                title.text = value.title
                subTitle.text = value.updated_at
            }
            get() = _index
    }
}