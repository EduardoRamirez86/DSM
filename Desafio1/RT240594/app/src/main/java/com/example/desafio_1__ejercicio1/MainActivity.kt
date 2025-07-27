package com.example.desafio_1__ejercicio1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStudentAverage: Button = findViewById(R.id.PromediodelEstudiante)
        val btnSalaryDiscount: Button = findViewById(R.id.DescuentoAlSalario)
        val btnBasicCalculator: Button = findViewById(R.id.CalculadoraBasica)

        btnStudentAverage.setOnClickListener {
            startActivity(Intent(this, StudentAverageActivity::class.java))
        }

        btnSalaryDiscount.setOnClickListener {
            startActivity(Intent(this, SalaryDiscountActivity::class.java))
        }

        btnBasicCalculator.setOnClickListener {
            startActivity(Intent(this, BasicCalculatorActivity::class.java))
        }
    }
}