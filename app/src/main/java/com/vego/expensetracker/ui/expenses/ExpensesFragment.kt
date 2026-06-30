package com.vego.expensetracker.ui.expenses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
//import com.vego.expensetracker.ExpenseTrackerApp
import com.vego.expensetracker.R
import com.vego.expensetracker.data.local.AppDatabase
import com.vego.expensetracker.data.repository.ExpenseRepository
import com.vego.expensetracker.ui.add.AddExpenseFragment
import kotlinx.coroutines.launch

class ExpensesFragment : Fragment(R.layout.fragment_expenses) {

    private lateinit var viewModel: ExpensesViewModel
    private lateinit var adapter: ExpensesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Repository
       // val app = requireActivity().application as ExpenseTrackerApp
        //val repository = ExpenseRepository(app.database.expenseDao())

        val database = AppDatabase.getInstance(requireContext())
        val repository = ExpenseRepository(database.expenseDao())




        // ViewModel con Factory (CRÍTICO)
        val factory = ExpensesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ExpensesViewModel::class.java]

        // RecyclerView
        adapter = ExpensesAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvExpenses)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // StateFlow correctamente observado
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.expenses.collect { list ->
                adapter.submitList(list)
            }
        }

        // FAB
        view.findViewById<FloatingActionButton>(R.id.fabAddExpense).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddExpenseFragment())
                .addToBackStack(null)
                .commit()
        }

        attachSwipeToDelete(recyclerView)
    }

    private fun attachSwipeToDelete(recyclerView: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val expense = adapter.currentList[position]

                viewModel.deleteExpense(expense)

                Snackbar.make(recyclerView, "Gasto eliminado", Snackbar.LENGTH_LONG)
                    .setAction("DESHACER") {
                        viewModel.restoreExpense(expense)
                    }
                    .show()
            }
        }

        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }
}
