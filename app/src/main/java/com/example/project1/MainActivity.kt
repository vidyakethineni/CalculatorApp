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

        val buttonDecimal = findViewById<Button>(R.id.buttonDecimal)
        buttonDecimal.setOnClickListener {
            if (!currentNumber.contains(".")) {
                currentNumber += "."
                updateTextView()
            }
        }

        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        buttonEquals.setOnClickListener {
            if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && currentOperation != null) {
                performCalculation()
                currentOperation = null
            }
        }

        val buttonClear = findViewById<Button>(R.id.buttonClear)
        buttonClear.setOnClickListener {
            currentNumber = ""
            previousNumber = ""
            currentOperation = null
            updateTextView()
        }

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

    }

    private fun updateTextView(text: String? = null) {
        val displayText = text ?: if (currentNumber.isNotEmpty()) currentNumber else "0"
        textView!!.text = displayText
    }

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
