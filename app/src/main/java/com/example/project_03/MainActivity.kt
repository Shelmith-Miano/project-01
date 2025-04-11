package com.example.project_03

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_03.todos.TodoAdapter
import com.example.project_03.todos.TodoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    // Get the API service instance
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoAdapter
    private val api = TodoApi.create()

    // views
    private lateinit var editTextTodoId: EditText
    private lateinit var submitButton: Button
    private lateinit var textViewResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        editTextTodoId = findViewById(R.id.editTextTodoId)
        submitButton = findViewById(R.id.buttonSubmit)
        textViewResults = findViewById(R.id.textViewResults)

        recyclerView = findViewById(R.id.recyclerViewTodos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchTodos()

        submitButton.setOnClickListener { // lambda function/ anonymous function
            val todoId = editTextTodoId.text.toString().toIntOrNull()

            if (todoId != null) {
                fetchTodoById(todoId)
            } else {
                Toast.makeText(this, "Invalid Todo ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchTodoById(todoId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val todo = api.getTodo(todoId)
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplicationContext(), "Todo: ${todo.title}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    // Handle error appropriately, e.g., show an error message
                    Toast.makeText(getApplicationContext(), "Failed to fetch todo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val todos = api.getTodos()
                withContext(Dispatchers.Main) {
                    adapter.updateTodos(todos)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
