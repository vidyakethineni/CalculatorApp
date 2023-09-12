package com.example.project1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

/**
 * MainActivity for the calculator application that replicates the functionality of the default iPhone calculator .
 */
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var currentNumber = StringBuilder()
    private var currentOperation: Char? = null
    private var expression = ArrayList<String>()

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
                val digit = (v as Button).text.toString()
                if (expression.isNotEmpty() && expression.last().isDigitsOnly()) {
                    // Concatenate the digit to the last number
                    expression[expression.size-1] += digit
                    currentNumber.append(digit)
                } else {
                    // Add it as a new number
                    expression.add(digit)
                    currentNumber.append(digit)
                }
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
                currentOperation = operator
                expression.add(operator.toString())
                currentNumber.setLength(0)
            }
        }

        /**
         * Click listener to insert decimal
         */
        val buttonDecimal = findViewById<Button>(R.id.buttonDecimal)
        buttonDecimal.setOnClickListener {
            if (!currentNumber.contains(".")) {
                expression.add(".")
                currentNumber.append(".")
                updateTextView()
            }
        }

        /**
         * Click listener for equal sign, performs PEMDAS to calculate and output expressions
         */
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        buttonEquals.setOnClickListener {
            //First completing multiplication expressions
            var i = 0
            while (i < expression.size) {
                if (expression[i] == "*") {
                    val answer = expression[i - 1].toDouble() * expression[i + 1].toDouble()
                    expression[i - 1] = answer.toString()
                    expression.removeAt(i)
                    expression.removeAt(i)
                } else {
                    i++
                }
            }
            //Second completing division expressions
            i = 0
            while (i < expression.size) {
                if (expression[i] == "/") {
                    val denominator = expression[i + 1].toDouble()
                    if (denominator == 0.0) {
                        // Handle division by zero error here if needed
                        textView.text = "Error"
                        return@setOnClickListener
                    }
                    val answer = expression[i - 1].toDouble() / denominator
                    expression[i - 1] = answer.toString()
                    expression.removeAt(i)
                    expression.removeAt(i)
                } else {
                    i++
                }
            }
            //Third completing addition expressions
            i = 0
            while (i < expression.size) {
                if (expression[i] == "+") {
                    val answer = expression[i - 1].toDouble() + expression[i + 1].toDouble()
                    expression[i - 1] = answer.toString()
                    expression.removeAt(i)
                    expression.removeAt(i)
                } else {
                    i++
                }
            }
            //Fourth completing addition expressions
            i = 0
            while (i < expression.size) {
                if (expression[i] == "-") {
                    val answer = expression[i - 1].toDouble() - expression[i + 1].toDouble()
                    expression[i - 1] = answer.toString()
                    expression.removeAt(i)
                    expression.removeAt(i)
                } else {
                    i++
                }
            }
            currentNumber = StringBuilder(expression[0])
            updateTextView()
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
                // Toggle the sign of the current number
                val firstChar = currentNumber[0]
                if (firstChar == '-') {
                    currentNumber.deleteCharAt(0) // Remove the minus sign
                } else {
                    currentNumber.insert(0, '-') // Add a minus sign at the beginning
                }
                val lastIndex = expression.lastIndex
                expression[lastIndex] = currentNumber.toString()
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
                expression.removeAt(expression.size - 1)
                expression.add(percentage.toString())
                updateTextView()
            }
        }
    }

    /**
     * Helper function for updating text view
     */
    private fun updateTextView() {
        val displayText = if (currentNumber.isNotEmpty()) currentNumber.toString() else "0"
        val formattedText = when {
            displayText.endsWith(".0") -> displayText.substring(0, displayText.length - 2)
            else -> displayText
        }
        textView.text = formattedText
    }


    /**
     * Helper function to clear the calculator
     */
    private fun clearCalculator() {
        currentNumber.setLength(0)
        expression.clear()
        updateTextView()
    }
}