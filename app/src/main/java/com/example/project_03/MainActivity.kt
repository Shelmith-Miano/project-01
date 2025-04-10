package com.example.project_03

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

import androidx.activity.enableEdgeToEdge
import androidx.tracing.perfetto.handshake.protocol.Response
import com.example.project_03.model.Todo


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        val editTextTodoId = findViewById<EditText>(R.id.editTextText) 
        val buttonSubmit = findViewById<Button>(R.id.button) 

       
        buttonSubmit.setOnClickListener { // lambda function/ anonymous function
           
            val todoId = editTextTodoId.text.toString().toIntOrNull()

            // input validation
            if (todoId != null) {
                fetchTodoById(todoId) // Call the function to fetch Todo by ID
            } else {
                // Show a Toast message if the input is invalid
                Toast.makeText(this, "Invalid Todo ID", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun fetchTodoById(todoId: Int) {
        RetrofitClient.apiService.getTodoById(todoId).enqueue(object : Callback<Todo>{
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if (response.isSuccessful) {
                    val todo = response.body()
                    // You can now use 'todo' and update the UI with the result
                    Log.d("API_SUCCESS", "Todo: $todo")
                } else {
                    Log.e("API_ERROR", "Failed to fetch todo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                Log.e("API_ERROR", "Network error: ${t.message}")
            }
        })
    }
    }
}

