package com.example.project_03.network

import com.example.project_03.model.Todo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    fun getTodos(): Call<List<Todo>>

    @GET("todos/{id}")
    fun getTodoById(@Path("id") id: Int): Call<Todo>
}