package com.vego.expensetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vego.expensetracker.ui.expenses.ExpensesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ExpensesFragment())
                .commit()
        }
    }
}
