package com.example.project1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity for the calculator application that replicates the functionality of the default iPhone calculator .
 */
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var currentNumber = StringBuilder()
    private var previousNumber = ""
    private var currentOperation: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        /**
         * Defining click listeners for the buttons in the Linear Layout
         */

        /**
         * Click listeners for digits
         */
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("button$i", "id", packageName)
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { v ->
                currentNumber.append((v as Button).text)
                updateTextView()
            }
        }

        /**
         * Defining a map that pairs IDs of buttons in Linear Layout with their corresponding mathematical operator symbols
         */
        val operatorButtons = mapOf(
            R.id.buttonDivide to '/',
            R.id.buttonMultiply to '*',
            R.id.buttonSubtract to '-',
            R.id.buttonAdd to '+'
        )

        /**
         * Click listener for operator symbols
         * The performCalculation() helper function is called to calculate and output expressions
         */
        operatorButtons.forEach { (buttonId, operator) ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener {
                if (currentNumber.isNotEmpty()) {
                    if (currentOperation != null) {
                        performCalculation()
                    }
                    previousNumber = currentNumber.toString()
                    currentNumber.setLength(0)
                    currentOperation = operator
                    updateTextView()
                }
            }
        }

        /**
         * Click listener to insert decimal
         */
        val buttonDecimal = findViewById<Button>(R.id.buttonDecimal)
        buttonDecimal.setOnClickListener {
            if (!currentNumber.contains(".")) {
                currentNumber.append(".")
                updateTextView()
            }
        }

        /**
         * Click listener for equal sign, which calls the performCalculation() helper function to calculate and output expressions
         */
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        buttonEquals.setOnClickListener {
            if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && currentOperation != null) {
                performCalculation()
                currentOperation = null
                previousNumber = "" // Clear the previousNumber after calculation
            }
        }

        /**
         * Click listener to clear and reset text view to 0
         */
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        buttonClear.setOnClickListener {
            clearCalculator()
        }

        /**
         * Click listener to change number from positive to negative, or negative to positive
         */
        val buttonArithmetic = findViewById<Button>(R.id.buttonArithmetic)
        buttonArithmetic.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                if (currentNumber[0] == '-') {
                    currentNumber.deleteCharAt(0)
                } else {
                    currentNumber.insert(0, "-")
                }
                updateTextView()
            }
        }

        /**
         * Click listener to find the decimal value of an inputted number
         */
        val buttonPercentage = findViewById<Button>(R.id.buttonPercentage)
        buttonPercentage.setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                val number = currentNumber.toString().toDoubleOrNull() ?: return@setOnClickListener
                val percentage = number / 100.0
                currentNumber.setLength(0)
                currentNumber.append(percentage.toString())
                updateTextView()
            }
        }
    }

    /**
     * Helper function for updating text view
     */
    private fun updateTextView() {
        val displayText = if (currentNumber.isNotEmpty()) currentNumber.toString() else if (previousNumber.isNotEmpty()) previousNumber else "0"
        val formattedText = when {
            displayText.endsWith(".0") -> displayText.substring(0, displayText.length - 2)
            else -> displayText
        }
        textView.text = formattedText
    }


    /**
     * Helper function for evaluating expressions based on the operator, and handles division by zero
     */
    private fun performCalculation() {
        val num1 = previousNumber.toDoubleOrNull() ?: return
        val num2 = currentNumber.toString().toDoubleOrNull() ?: return

        when (currentOperation) {
            '+' -> currentNumber = StringBuilder((num1 + num2).toString())
            '-' -> currentNumber = StringBuilder((num1 - num2).toString())
            '*' -> currentNumber = StringBuilder((num1 * num2).toString())
            '/' -> {
                if (num2 != 0.0) {
                    currentNumber = StringBuilder((num1 / num2).toString())
                } else {
                    currentNumber = StringBuilder("Error")
                }
            }
            else -> return
        }
        updateTextView()
    }

    /**
     * Helper function to clear the calculator
     */
    private fun clearCalculator() {
        currentNumber.setLength(0)
        previousNumber = ""
        currentOperation = null
        updateTextView()
    }
}
