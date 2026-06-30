package com.vego.expensetracker.ui.expenses

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vego.expensetracker.data.local.AppDatabase
import com.vego.expensetracker.data.repository.ExpenseRepository

class ExpensesViewModelFactory(
    private val repository: ExpenseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpensesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
