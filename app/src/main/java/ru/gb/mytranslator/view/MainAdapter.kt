package ru.gb.mytranslator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import geekbrains.ru.translator.R
import ru.gb.data.DataModel
import kotlin.reflect.KProperty

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

            val header by ViewByIdDelegate<TextView>(R.id.header_textview_recycler_item, itemView)
            val description by ViewByIdDelegate<TextView>(R.id.description_textview_recycler_item, itemView)

            if (layoutPosition != RecyclerView.NO_POSITION) {
                header.text = data.text
                description.text = data.meanings?.get(0)?.translation?.translation
                itemView.setOnClickListener { onItemClick(data) }
            }
        }
    }
}

class ViewByIdDelegate<out T : View?>(private val viewId: Int, private val itemView: View) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return itemView.findViewById<T>(viewId)
    }
}