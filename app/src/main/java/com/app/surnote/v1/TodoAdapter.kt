package com.app.surnote.v1

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todoList: MutableList<TodoItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filteredList: MutableList<TodoItem> = todoList.toMutableList()

    fun filter(date: String) {
        filteredList = todoList.filter { it.date == date }.toMutableList()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.cbTask)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun getItemViewType(position: Int) = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = filteredList[position]
            holder.checkBox.text = item.taskName
            holder.tvTime.text = item.taskTime
            holder.checkBox.isChecked = item.isDone

            if (item.isDone) {
                holder.checkBox.paintFlags = holder.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.checkBox.paintFlags = holder.checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isDone = isChecked
                notifyItemChanged(position)
            }

            holder.btnDelete.setOnClickListener {
                todoList.remove(item)
                filteredList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, filteredList.size)
            }
        }
    }

    override fun getItemCount() = filteredList.size
}