package com.vego.expensetracker.data.repository

import com.vego.expensetracker.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import com.vego.expensetracker.data.local.dao.ExpenseDao


class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return expenseDao.getAllExpenses()
    }

    suspend fun addExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }
}
