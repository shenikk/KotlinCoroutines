package com.example.kotlincoroutines

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kotlincoroutines.data.CatFactsResponse
import com.example.kotlincoroutines.data.RetrofitInstance
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var catFactTextView: TextView
    private lateinit var getCatFactButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catFactTextView = findViewById(R.id.catFactTextView)
        getCatFactButton = findViewById(R.id.getCatFactButton)
        progressBar = findViewById(R.id.progressBar)

        getCatFactButton.setOnClickListener {
            fetchCatFact()
        }
    }

    private fun fetchCatFact() {
        progressBar.visibility = ProgressBar.VISIBLE
        catFactTextView.text = ""

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            progressBar.visibility = ProgressBar.GONE // Скрыть лоадер при ошибке
            catFactTextView.text = "Ошибка: ${throwable.message}"
        }

        lifecycleScope.launch(exceptionHandler) {
            try {
                val response: CatFactsResponse = RetrofitInstance.api.getRandomCatFact()
                catFactTextView.text = response.data.joinToString(", ")
            } catch (e: Exception) {
                catFactTextView.text = "Ошибка: ${e.message}"
            } finally {
                progressBar.visibility = ProgressBar.GONE
            }
        }
    }
}
