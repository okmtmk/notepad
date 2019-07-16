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
    private val listener: OnRecyclerClickListener
) : RecyclerView.Adapter<MemoRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.recycler_item_memo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].apply {
            holder.title.text = title
            holder.subTitle.text = updated_at
        }

        holder.itemView.setOnClickListener { view ->
            listener.onClick(view, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val title: TextView = viewItem.title
        val subTitle: TextView = viewItem.subtitle
    }
}