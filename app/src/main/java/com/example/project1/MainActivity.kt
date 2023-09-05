package com.example.project1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private var currentNumber = ""
    private var previousNumber = ""
    private var currentOperation: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        // Click listeners for digits
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("button$i", "id", packageName)
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { v ->
                currentNumber += (v as Button).text
                updateTextView()
            }
        }

        val operatorButtons = mapOf(
            R.id.buttonDivide to '/',
            R.id.buttonMultiply to '*',
            R.id.buttonSubtract to '-',
            R.id.buttonAdd to '+'
        )

        // Click listener to insert respective operand
        operatorButtons.forEach { (buttonId, operator) ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener {
                if (currentNumber.isNotEmpty()) {
                    if (currentOperation != null) {
                        performCalculation()
                    }
                    previousNumber = currentNumber
                    currentNumber = ""
                    currentOperation = operator
                    updateTextView(previousNumber)
                }
            }
        }

        // Click listener to insert decimal
        val buttonDecimal = findViewById<Button>(R.id.buttonDecimal)
        buttonDecimal.setOnClickListener {
            if (!currentNumber.contains(".")) {
                currentNumber += "."
                updateTextView()
            }
        }

        // Click listener to evaluate expressions
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        buttonEquals.setOnClickListener {
            if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && currentOperation != null) {
                performCalculation()
                currentOperation = null
            }
        }

        // Click listener to clear text view
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        buttonClear.setOnClickListener {
            currentNumber = ""
            previousNumber = ""
            currentOperation = null
            updateTextView()
        }

        // Click listener to change number from positive to negative, or negative to positive
        val buttonArithmetic = findViewById<Button>(R.id.buttonArithmetic)
        buttonArithmetic.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber = if (currentNumber.startsWith("-")) {
                    currentNumber.substring(1)
                } else {
                    "-$currentNumber"
                }
                updateTextView()
            }
        }

        // Click listener to find the decimal value of a number
        val buttonPercentage = findViewById<Button>(R.id.buttonPercentage)
        buttonPercentage.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                val number = currentNumber.toDouble()
                val percentage = number / 100.0
                currentNumber = percentage.toString()
                updateTextView()
            }
        }

    }

    // Helper function for updating text view
    private fun updateTextView(text: String? = null) {
        val displayText = text ?: if (currentNumber.isNotEmpty()) currentNumber else "0"

        // Format the number based on whether it's a whole number or not
        val isWholeNumber = currentNumber.toDouble() % 1 == 0.0
        textView!!.text = if (isWholeNumber) {
            displayText.toInt().toString()
        } else {
            displayText
        }
    }

    // Helper function for evaluating expressions
    private fun performCalculation() {
        val num1 = previousNumber.toDouble()
        val num2 = currentNumber.toDouble()
        val result = when (currentOperation) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> num1 / num2
            else -> return
        }
        currentNumber = result.toString()
        previousNumber = ""
        updateTextView(currentNumber)
    }
}
