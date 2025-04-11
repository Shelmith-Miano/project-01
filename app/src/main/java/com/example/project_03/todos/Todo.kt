package com.example.project_03.todos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

interface TodoApi {
    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): TodoApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(TodoApi::class.java)
        }
    }

    @GET("todos/?_limit=10")
    suspend fun getTodos(): List<Todo>

    @GET("todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): Todo
}
