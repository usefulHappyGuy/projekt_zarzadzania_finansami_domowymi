package com.example.projekt_lab


import android.widget.*
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {

    private lateinit var Mainbutton: Button
    private lateinit var pieChart: PieChartView
    private lateinit var balanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        pieChart = findViewById(R.id.pieChart)
        balanceTextView = findViewById(R.id.balanceTextView)
        Mainbutton = findViewById(R.id.Mainbutton)

        val income = intent.getDoubleExtra("income", 0.0)
        val expenses = intent.getParcelableArrayListExtra<Expense>("expenses") ?: arrayListOf()

        val totalExpenses = expenses.sumOf { it.amount }
        val balance = income - totalExpenses

        balanceTextView.text = "Bilans: ${"%.2f".format(balance)} zł"
        balanceTextView.setTextColor(if (balance >= 0) Color.GREEN else Color.RED)

        val grouped = expenses.groupBy { it.category }.mapValues { entry ->
            entry.value.sumOf { it.amount }
        }

        pieChart.data = grouped


        Mainbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}