package com.example.project1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import android.content.res.Configuration
import android.util.Log


/**
 * MainActivity for the application that replicates the functionality of a calculator.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var currentNumber = StringBuilder()
    private var currentOperation: Char? = null
    private var expression = ArrayList<String>()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the currentNumber and expression
        outState.putString("currentNumber", currentNumber.toString())
        outState.putStringArrayList("expression", expression)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore the currentNumber and expression
        currentNumber = StringBuilder(savedInstanceState.getString("currentNumber", ""))
        expression = savedInstanceState.getStringArrayList("expression") ?: ArrayList()
        updateTextView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Determining the current orientation (i.e vertical or horizontal)
         */
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        /**
         * Inflating the appropriate layout XML file based on orientation
         */
        val layoutId = if (isLandscape) R.layout.activity_horizontal else R.layout.activity_main
        setContentView(layoutId)

        //setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        /**
         * Defining additional functionality of application for the horizontal orientation
         */
        if (isLandscape) {
            /**
             * Restoring user input (if it exists) after orientation change
             */
            savedInstanceState?.let {
                currentNumber = StringBuilder(it.getString("currentNumber", ""))
                expression = it.getStringArrayList("expression") ?: ArrayList()
                updateTextView()
            }
            /**
             * Logging button clicks using LogCat
             */
            val buttons = arrayOf<Button>(
                // List of buttons present only in the horizontal orientation
                findViewById(R.id.buttonSin),
                findViewById(R.id.buttonCos),
                findViewById(R.id.buttonTan),
                findViewById(R.id.buttonLog10),
                findViewById(R.id.buttonLn)
            )
            /**
             * Setting click listeners for buttons present only in the horizontal orientation
             */
            // Set a click listener for the sin button
            val buttonSin = findViewById<Button>(R.id.buttonSin)
            buttonSin.setOnClickListener {
                Log.d("ButtonClicked", "sin button clicked")

                if (currentNumber.isNotEmpty()) {
                    val number =
                        currentNumber.toString().toDoubleOrNull() ?: return@setOnClickListener
                    val result = Math.sin(Math.toRadians(number))
                    currentNumber.setLength(0)
                    currentNumber.append(result.toString())
                    expression.removeAt(expression.size - 1)
                    expression.add(result.toString())
                    updateTextView()
                }
            }
            // Set a click listener for the cos button
            val buttonCos = findViewById<Button>(R.id.buttonCos)
            buttonCos.setOnClickListener {
                Log.d("ButtonClicked", "cos button clicked")

                if (currentNumber.isNotEmpty()) {
                    val number =
                        currentNumber.toString().toDoubleOrNull() ?: return@setOnClickListener
                    val result = Math.cos(Math.toRadians(number))
                    currentNumber.setLength(0)
                    currentNumber.append(result.toString())
                    expression.removeAt(expression.size - 1)
                    expression.add(result.toString())
                    updateTextView()
                }
            }
            // Set a click listener for the tan button
            val buttonTan = findViewById<Button>(R.id.buttonTan)
            buttonTan.setOnClickListener {
                Log.d("ButtonClicked", "tan button clicked")

                if (currentNumber.isNotEmpty()) {
                    val number =
                        currentNumber.toString().toDoubleOrNull() ?: return@setOnClickListener
                    val result = Math.tan(Math.toRadians(number))
                    currentNumber.setLength(0)
                    currentNumber.append(result.toString())
                    expression.removeAt(expression.size - 1)
                    expression.add(result.toString())
                    updateTextView()
                }
            }
            // Set a click listener for the Log 10 button
            val buttonLog10 = findViewById<Button>(R.id.buttonLog10)
            buttonLog10.setOnClickListener {
                Log.d("ButtonClicked", "Log 10 button clicked")

                if (currentNumber.isNotEmpty()) {
                    val number =
                        currentNumber.toString().toDoubleOrNull() ?: return@setOnClickListener
                    val result = Math.log10(number)
                    currentNumber.setLength(0)
                    currentNumber.append(result.toString())
                    expression.removeAt(expression.size - 1)
                    expression.add(result.toString())
                    updateTextView()
                }
            }
            // Set a click listener for the ln button
            val buttonLn = findViewById<Button>(R.id.buttonLn)
            buttonLn.setOnClickListener {
                Log.d("ButtonClicked", "ln button clicked")

                if (currentNumber.isNotEmpty()) {
                    val number =
                        currentNumber.toString().toDoubleOrNull() ?: return@setOnClickListener
                    val result = Math.log(number)
                    currentNumber.setLength(0)
                    currentNumber.append(result.toString())
                    expression.removeAt(expression.size - 1)
                    expression.add(result.toString())
                    updateTextView()
                }
            }
        }


        /**
         * Logging button clicks using LogCat
         */
        val buttons = arrayOf<Button>(
            // List all your buttons here
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.buttonAdd),
            findViewById(R.id.buttonSubtract),
            findViewById(R.id.buttonMultiply),
            findViewById(R.id.buttonDivide),
            findViewById(R.id.buttonDecimal),
            findViewById(R.id.buttonArithmetic),
            findViewById(R.id.buttonPercentage),
            findViewById(R.id.buttonEquals),
            findViewById(R.id.buttonClear)
        )

        /**
         * Defining click listeners for the buttons in the vertical and horizontal orientations
         */

        /**
         * Click listeners for digits
         */
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("button$i", "id", packageName)
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { v ->
                val digit = (v as Button).text.toString()

                Log.d("ButtonClicked", digit + " button clicked")

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
                Log.d("ButtonClicked", operator + " button clicked")
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
            Log.d("ButtonClicked","Decimal button clicked")
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
            Log.d("ButtonClicked","Equal button clicked")

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
            Log.d("ButtonClicked","Clear button clicked")

            clearCalculator()
        }

        /**
         * Click listener to change number from positive to negative, or negative to positive
         */
        val buttonArithmetic = findViewById<Button>(R.id.buttonArithmetic)
        buttonArithmetic.setOnClickListener {
            Log.d("ButtonClicked","Arithmetic sign button clicked")

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
            Log.d("ButtonClicked","Percentage button clicked")

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