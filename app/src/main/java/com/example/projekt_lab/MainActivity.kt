package com.example.projekt_lab

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var incomeEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var amountEditText: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expensesListView: ListView
    private lateinit var summaryButton: Button

    private val expenses = mutableListOf<Expense>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incomeEditText = findViewById(R.id.incomeEditText)
        categorySpinner = findViewById(R.id.categorySpinner)
        amountEditText = findViewById(R.id.amountEditText)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        expensesListView = findViewById(R.id.expensesListView)
        summaryButton = findViewById(R.id.summaryButton)

        val categories = listOf("Jedzenie", "Transport", "Rozrywka", "Inne")
        categorySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        expensesListView.adapter = adapter

        addExpenseButton.setOnClickListener {
            val category = categorySpinner.selectedItem.toString()
            val amount = amountEditText.text.toString().toDoubleOrNull()
            if (amount != null) {
                val expense = Expense(category, amount)
                expenses.add(expense)
                adapter.add("${category}: ${"%.2f".format(amount)} zł")
                amountEditText.text.clear()
            } else {
                Toast.makeText(this, "Wprowadź poprawną kwotę", Toast.LENGTH_SHORT).show()
            }
        }

        summaryButton.setOnClickListener {
            val income = incomeEditText.text.toString().toDoubleOrNull() ?: 0.0
            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("income", income)
            intent.putParcelableArrayListExtra("expenses", ArrayList(expenses))
            startActivity(intent)
        }


    }
}