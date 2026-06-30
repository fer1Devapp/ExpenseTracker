package com.vego.expensetracker.ui.detail


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vego.expensetracker.R
import com.vego.expensetracker.data.local.entity.ExpenseEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseDetailFragment : Fragment(R.layout.fragment_expense_detail) {

    companion object {
        private const val ARG_EXPENSE_ID = "expense_id"
        private const val ARG_AMOUNT = "amount"
        private const val ARG_CATEGORY = "category"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_DATE = "date"

        fun newInstance(expense: ExpenseEntity): ExpenseDetailFragment {
            return ExpenseDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_EXPENSE_ID, expense.id)
                    putDouble(ARG_AMOUNT, expense.amount)
                    putString(ARG_CATEGORY, expense.category)
                    putString(ARG_DESCRIPTION, expense.description)
                    putLong(ARG_DATE, expense.date)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val amount = arguments?.getDouble(ARG_AMOUNT) ?: 0.0
        val category = arguments?.getString(ARG_CATEGORY) ?: ""
        val description = arguments?.getString(ARG_DESCRIPTION) ?: "Sin descripción"
        val date = arguments?.getLong(ARG_DATE) ?: 0L

        view.findViewById<TextView>(R.id.tvDetailCategory).text = category
        view.findViewById<TextView>(R.id.tvDetailAmount).text = "$$amount"
        view.findViewById<TextView>(R.id.tvDetailDescription).text = description

        val dateFormatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            .format(Date(date))
        view.findViewById<TextView>(R.id.tvDetailDate).text = "Fecha: $dateFormatted"
    }
}