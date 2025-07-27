package com.example.desafio_1__ejercicio1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class StudentAverageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_average)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val studentNameEditText: EditText = findViewById(R.id.studentName)
        val nota1EditText: EditText = findViewById(R.id.nota1)
        val nota2EditText: EditText = findViewById(R.id.nota2)
        val nota3EditText: EditText = findViewById(R.id.nota3)
        val nota4EditText: EditText = findViewById(R.id.nota4)
        val nota5EditText: EditText = findViewById(R.id.nota5)

        val calculateButton: Button = findViewById(R.id.calculateAverage)
        val resultTextView: TextView = findViewById(R.id.result)


        calculateButton.setOnClickListener {
            studentNameEditText.error = null
            nota1EditText.error = null
            nota2EditText.error = null
            nota3EditText.error = null
            nota4EditText.error = null
            nota5EditText.error = null
            resultTextView.text = ""

            val studentName = studentNameEditText.text.toString().trim()
            if (studentName.isBlank()) {
                studentNameEditText.error = "El nombre es obligatorio"
                resultTextView.text = "Por favor, ingrese el nombre del estudiante."
                return@setOnClickListener
            }

            try {
                val nota1Value = validateAndGetGrade(nota1EditText)
                val nota2Value = validateAndGetGrade(nota2EditText)
                val nota3Value = validateAndGetGrade(nota3EditText)
                val nota4Value = validateAndGetGrade(nota4EditText)
                val nota5Value = validateAndGetGrade(nota5EditText)

                if (nota1Value == null || nota2Value == null || nota3Value == null || nota4Value == null || nota5Value == null) {
                    if (resultTextView.text.isNullOrEmpty()) {
                        resultTextView.text = "Corrija los errores en las notas."
                    }
                    return@setOnClickListener
                }

                val average = (nota1Value + nota2Value + nota3Value + nota4Value + nota5Value) / 5.0

                resultTextView.text = String.format("%s, tu promedio es: %.2f", studentName, average)

            } catch (e: NumberFormatException) {
                resultTextView.text = "Error inesperado al procesar números."
            }
        }
    }

    private fun validateAndGetGrade(editText: EditText): Double? {
        val text = editText.text.toString()
        if (text.isBlank()) {
            editText.error = "Este campo es obligatorio"
            return null
        }
        return try {
            val value = text.toDouble()
            if (value < 0.0 || value > 10.0) {
                editText.error = "La nota debe estar entre 0 y 10"
                null
            } else {
                editText.error = null
                value
            }
        } catch (e: NumberFormatException) {
            editText.error = "Ingrese un número válido (ej: 7 o 8.5)"
            null
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
