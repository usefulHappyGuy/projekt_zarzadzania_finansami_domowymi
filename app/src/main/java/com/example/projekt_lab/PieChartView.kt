package com.example.projekt_lab

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min
import kotlin.math.*

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var data: Map<String, Double> = emptyMap()
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = 32f
        textAlign = Paint.Align.CENTER
    }

    private val colors = listOf(
        Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isEmpty()) return

        val total = data.values.sum()
        var startAngle = 0f

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = min(width, height) / 2 * 0.7f
        val centerX = width / 2
        val centerY = height / 2
        val rect = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        data.entries.forEachIndexed { index, entry ->
            val sweepAngle = (entry.value / total * 360).toFloat()
            paint.color = colors[index % colors.size]
            canvas.drawArc(rect, startAngle, sweepAngle, true, paint)

            // Dodaj podpis kategorii
            val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
            val labelRadius = radius * 0.6f // środek wycinka, bliżej środka
            val labelX = (centerX + labelRadius * cos(angle)).toFloat()
            val labelY = (centerY + labelRadius * sin(angle)).toFloat()

            val label = entry.key // tylko nazwa kategorii
            canvas.drawText(label, labelX, labelY, textPaint)

            startAngle += sweepAngle
        }
    }
}