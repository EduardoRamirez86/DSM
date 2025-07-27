package com.example.desafio_1__ejercicio1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import java.text.NumberFormat
import java.util.Locale


class SalaryDiscountActivity : AppCompatActivity() {

    private lateinit var employeeNameEditText: EditText
    private lateinit var baseSalaryEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTitleTextView: TextView
    private lateinit var resultTextView: TextView

    private val AFP_PERCENTAGE = 0.0725
    private val ISSS_PERCENTAGE = 0.03

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_discount)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        employeeNameEditText = findViewById(R.id.employeeName)
        baseSalaryEditText = findViewById(R.id.baseSalary)
        calculateButton = findViewById(R.id.calculateButton)
        resultTitleTextView = findViewById(R.id.resultTitle)
        resultTextView = findViewById(R.id.resultTextView)

        calculateButton.setOnClickListener {
            hideKeyboard()
            calculateAndDisplaySalary()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        employeeNameEditText.error = null
        baseSalaryEditText.error = null

        if (employeeNameEditText.text.toString().trim().isBlank()) {
            employeeNameEditText.error = "El nombre es obligatorio"
            isValid = false
        }

        val salaryStr = baseSalaryEditText.text.toString()
        if (salaryStr.isBlank()) {
            baseSalaryEditText.error = "El salario es obligatorio"
            isValid = false
        } else {
            try {
                val salary = salaryStr.toDouble()
                if (salary <= 0) {
                    baseSalaryEditText.error = "El salario debe ser un número positivo"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                baseSalaryEditText.error = "Ingrese un salario válido"
                isValid = false
            }
        }
        return isValid
    }


    private fun calculateIncomeTax(taxableSalary: Double): Double {
        return when {
            taxableSalary <= 0.0 -> 0.0
            taxableSalary <= 472.00 -> 0.0
            taxableSalary <= 895.24 -> ((taxableSalary - 472.00) * 0.10) + 17.67
            taxableSalary <= 2038.10 -> ((taxableSalary - 895.24) * 0.20) + 60.00
            else -> ((taxableSalary - 2038.10) * 0.30) + 288.57
        }
    }

    private fun calculateAndDisplaySalary() {
        if (!validateInputs()) {
            resultTitleTextView.visibility = View.GONE
            resultTextView.visibility = View.GONE
            resultTextView.text = ""
            return
        }

        val employeeName = employeeNameEditText.text.toString().trim()
        val baseSalary = baseSalaryEditText.text.toString().toDouble()

        val afpDiscount = baseSalary * AFP_PERCENTAGE
        val isssDiscount = baseSalary * ISSS_PERCENTAGE

        val salaryAfterAfpIsss = baseSalary - afpDiscount - isssDiscount
        val taxableForIncomeTax = if (salaryAfterAfpIsss > 0) salaryAfterAfpIsss else 0.0
        val incomeTaxDiscount = calculateIncomeTax(taxableForIncomeTax)

        val netSalary = salaryAfterAfpIsss - incomeTaxDiscount

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "US"))

        val resultBuilder = StringBuilder()
        resultBuilder.append("Empleado: $employeeName\n")
        resultBuilder.append("Salario Base: ${currencyFormat.format(baseSalary)}\n\n")
        resultBuilder.append("Descuentos Legales:\n")
        resultBuilder.append("  AFP (${(AFP_PERCENTAGE * 100).format(2)}%): ${currencyFormat.format(afpDiscount)}\n")
        resultBuilder.append("  ISSS (${(ISSS_PERCENTAGE * 100).format(2)}%): ${currencyFormat.format(isssDiscount)}\n\n")
        resultBuilder.append("Salario después de AFP e ISSS: ${currencyFormat.format(salaryAfterAfpIsss)}\n")
        resultBuilder.append("Retención de Renta: ${currencyFormat.format(incomeTaxDiscount)}\n\n")
        resultBuilder.append("SALARIO NETO A RECIBIR: ${currencyFormat.format(netSalary)}")

        resultTitleTextView.visibility = View.VISIBLE
        resultTextView.visibility = View.VISIBLE
        resultTextView.text = resultBuilder.toString()
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
