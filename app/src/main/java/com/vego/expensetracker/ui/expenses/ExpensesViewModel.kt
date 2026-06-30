package com.vego.expensetracker.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vego.expensetracker.data.local.entity.ExpenseEntity
import com.vego.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ExpensesViewModel(
    private val repository: ExpenseRepository

) : ViewModel() {

    val expenses: StateFlow<List<ExpenseEntity>> =
        repository.getAllExpenses()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    fun addExpense(
        amount: Double,
        category: String,
        description: String?
    ) {
        viewModelScope.launch {
            repository.addExpense(
                ExpenseEntity(
                    amount = amount,
                    category = category,
                    description = description,
                    date = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun restoreExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }


}


