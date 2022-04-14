package ru.gb.mytranslator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbrains.ru.translator.R
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*
import ru.gb.data.DataModel

class MainAdapter(
    val onItemClick: (itemData: DataModel) -> Unit,
    private var data: List<DataModel>
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {

        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View

        return RecyclerItemViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.headerTextviewRecyclerItem.text = data.text
                itemView.descriptionTextviewRecyclerItem.text =
                    data.meanings?.get(0)?.translation?.translation
                itemView.setOnClickListener { onItemClick(data) }
            }
        }
    }
}
