package my.edu.nottingham.firebasetestlab_demo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentNumber = ""
    private var currentOperator = ""
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var isNewCalculation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.display)
        setupNumberButtons()
        setupOperatorButtons()
        setupClearButton()
        setupEqualsButton()
        setupDecimalButton()
    }

    private fun setupNumberButtons() {
        val numberButtonIds = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )

        numberButtonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { button ->
                val number = (button as Button).text.toString()
                if (isNewCalculation) {
                    currentNumber = number
                    isNewCalculation = false
                } else {
                    currentNumber += number
                }
                updateDisplay()
            }
        }
    }

    private fun setupOperatorButtons() {
        val operatorButtonIds = listOf(
            R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide
        )

        operatorButtonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { button ->
                if (currentNumber.isNotEmpty()) {
                    firstNumber = currentNumber.toDouble()
                    currentOperator = (button as Button).text.toString()
                    currentNumber = ""
                    updateDisplay()
                }
            }
        }
    }

    private fun setupClearButton() {
        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            currentNumber = ""
            currentOperator = ""
            firstNumber = 0.0
            secondNumber = 0.0
            isNewCalculation = true
            display.text = "0"
        }
    }

    private fun setupEqualsButton() {
        findViewById<Button>(R.id.btn_equals).setOnClickListener {
            if (currentOperator.isNotEmpty() && currentNumber.isNotEmpty()) {
                secondNumber = currentNumber.toDouble()
                val result = calculate(firstNumber, secondNumber, currentOperator)

                display.text = formatResult(result)

                // Reset for next calculation
                currentNumber = result.toString()
                currentOperator = ""
                isNewCalculation = true
            }
        }
    }

    private fun setupDecimalButton() {
        findViewById<Button>(R.id.btn_decimal).setOnClickListener {
            if (isNewCalculation) {
                currentNumber = "0."
                isNewCalculation = false
            } else if (!currentNumber.contains(".")) {
                currentNumber += "."
            }
            updateDisplay()
        }
    }

    private fun calculate(num1: Double, num2: Double, operator: String): Double {
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "×" -> num1 * num2
            "÷" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> num2
        }
    }

    private fun formatResult(result: Double): String {
        return if (result.isNaN()) {
            "Error"
        } else if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            result.toString()
        }
    }

    private fun updateDisplay() {
        if (currentNumber.isNotEmpty()) {
            display.text = currentNumber
        }
    }
}