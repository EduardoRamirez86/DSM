package com.example.desafio_1__ejercicio1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import kotlin.math.pow
import kotlin.math.sqrt

class BasicCalculatorActivity : AppCompatActivity() {
    private lateinit var number1EditText: EditText
    private lateinit var number2EditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_calculator)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inicializar las vistas
        number1EditText = findViewById(R.id.number1)
        number2EditText = findViewById(R.id.number2)
        resultTextView = findViewById(R.id.result)

        val addButton: Button = findViewById(R.id.add)
        val subtractButton: Button = findViewById(R.id.subtract)
        val multiplyButton: Button = findViewById(R.id.multiply)
        val divideButton: Button = findViewById(R.id.divide)
        val exponentButton: Button = findViewById(R.id.exponent)
        val sqrtButton: Button = findViewById(R.id.sqrt)

        addButton.setOnClickListener {
            performCalculation("SUMA")
        }
        subtractButton.setOnClickListener {
            performCalculation("RESTA")
        }
        multiplyButton.setOnClickListener {
            performCalculation("MULTIPLICACION")
        }
        divideButton.setOnClickListener {
            performCalculation("DIVISION")
        }
        exponentButton.setOnClickListener {
            performCalculation("EXPONENTE")
        }
        sqrtButton.setOnClickListener {
            performCalculation("RAIZ")
        }
    }

    private fun clearInputErrors() {
        number1EditText.error = null
        number2EditText.error = null
    }

    private fun validateAndGetNumber(editText: EditText, fieldName: String): Double? {
        val text = editText.text.toString()
        if (text.isBlank()) {
            editText.error = "$fieldName es obligatorio"
            return null
        }
        return try {
            text.toDouble()
        } catch (e: NumberFormatException) {
            editText.error = "Ingrese un número válido para $fieldName"
            null
        }
    }

    private fun performCalculation(operation: String) {
        clearInputErrors()
        resultTextView.text = ""

        val n1 = validateAndGetNumber(number1EditText, "Número 1")
        val n2 = if (operation.uppercase() != "RAIZ") {
            validateAndGetNumber(number2EditText, "Número 2")
        } else {
            0.0
        }

        if (n1 == null) {
            resultTextView.text = "Corrija los errores en el Número 1."
            return
        }
        if (operation.uppercase() != "RAIZ" && n2 == null) {
            resultTextView.text = "Corrija los errores en el Número 2."
            return
        }


        try {
            var calculationResult: Double? = null
            var calculationDisplayString = ""

            when (operation.uppercase()) {
                "SUMA" -> {
                    calculationResult = n1 + n2!!
                    calculationDisplayString = String.format("%.2f + %.2f =", n1, n2)
                }
                "RESTA" -> {
                    calculationResult = n1 - n2!!
                    calculationDisplayString = String.format("%.2f - %.2f =", n1, n2)
                }
                "MULTIPLICACION" -> {
                    calculationResult = n1 * n2!!
                    calculationDisplayString = String.format("%.2f * %.2f =", n1, n2)
                }
                "DIVISION" -> {
                    if (n2!! == 0.0) {
                        resultTextView.text = "Error: No se puede dividir por cero."
                        number2EditText.error = "No puede ser cero para división"
                        return
                    }
                    calculationResult = n1 / n2
                    calculationDisplayString = String.format("%.2f / %.2f =", n1, n2)
                }
                "EXPONENTE" -> {
                    calculationResult = n1.pow(n2!!)
                    calculationDisplayString = String.format("%.2f ^ %.2f =", n1, n2)
                }
                "RAIZ" -> {
                    if (n1 < 0) {
                        resultTextView.text = "Error: No se puede calcular la raíz de un número negativo."
                        number1EditText.error = "Debe ser no negativo para raíz"
                        return
                    }
                    calculationResult = sqrt(n1)
                    calculationDisplayString = String.format("√%.2f =", n1)
                }
                else -> {
                    resultTextView.text = "Operación no reconocida"
                    return
                }
            }

            if (calculationResult != null) {
                resultTextView.text = String.format("%s %.2f", calculationDisplayString, calculationResult)
            }

        } catch (e: Exception) {
            resultTextView.text = "Error: Por favor, ingrese números válidos."
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
