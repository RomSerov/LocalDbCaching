package com.example.localdbcachingtest.data.todo.remote.api

import com.example.localdbcachingtest.data.todo.remote.dto.TodoResponse
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("todos")
    suspend fun fetchTodos(): Response<List<TodoResponse>>
}