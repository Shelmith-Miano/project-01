package com.example.project_03.todos

// adapter/TodoAdapter.kt
import com.example.project_03.R


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter(private var todos: List<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.todoTitle)
        val completedTextView: TextView = itemView.findViewById(R.id.todoCompleted)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        val todo = todos[position]
        holder.titleTextView.text = todo.title
        holder.completedTextView.text = if (todo.completed) "Completed" else "Pending"

        holder.cardView.setOnClickListener {
            val context = holder.itemView.context
            val details = "User ID: ${todo.userId}\nID: ${todo.id}\nTitle: ${todo.title}\nCompleted: ${todo.completed}"
            Toast.makeText(context, details, Toast.LENGTH_LONG).show()
        }
    }
    override fun getItemCount(): Int = todos.size

    fun updateTodos(newTodos:List<Todo>) {
        todos = newTodos
        notifyDataSetChanged()
    }
}
