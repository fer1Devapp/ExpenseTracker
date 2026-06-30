package com.vego.expensetracker.ui.expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vego.expensetracker.R
import com.vego.expensetracker.data.local.entity.ExpenseEntity

class ExpensesAdapter (
    private val onItemClick: (ExpenseEntity) -> Unit
):

    ListAdapter<ExpenseEntity, ExpensesAdapter.ExpenseViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpenseViewHolder(
        itemView: View,
        private val onItemClick: (ExpenseEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(expense: ExpenseEntity) {
            itemView.findViewById<TextView>(R.id.tvAmount).text =
                "$${expense.amount}"

            itemView.findViewById<TextView>(R.id.tvCategory).text =
                expense.category
            itemView.findViewById<TextView>(R.id.tvDescription).text =
                expense.description ?: "Sin descripcion"

            itemView.setOnClickListener { onItemClick(expense) }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<ExpenseEntity>() {
        override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
            return oldItem == newItem
        }
    }
}
