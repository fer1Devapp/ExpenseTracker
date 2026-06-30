package com.vego.expensetracker.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vego.expensetracker.R
import com.vego.expensetracker.data.local.AppDatabase
import com.vego.expensetracker.data.repository.ExpenseRepository
import com.vego.expensetracker.ui.expenses.ExpensesViewModel
import com.vego.expensetracker.ui.expenses.ExpensesViewModelFactory

class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {

    private lateinit var viewModel: ExpensesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ Crear repository correctamente
        val database = AppDatabase.getInstance(requireContext())
        val repository = ExpenseRepository(database.expenseDao())

        // ✅ Pasar repository (NO context)
        val factory = ExpensesViewModelFactory(repository)

        viewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[ExpensesViewModel::class.java]

        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val etCategory = view.findViewById<EditText>(R.id.etCategory)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val category = etCategory.text.toString()
            val description = etDescription.text.toString()

            viewModel.addExpense(
                amount = amount,
                category = category,
                description = description
            )

            parentFragmentManager.popBackStack()
        }
    }
}
