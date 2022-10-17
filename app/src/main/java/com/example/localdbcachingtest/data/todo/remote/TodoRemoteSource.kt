package com.example.localdbcachingtest.data.todo.remote

import com.example.localdbcachingtest.data.common.exception.NoInternetException
import com.example.localdbcachingtest.data.todo.remote.api.TodoApi
import com.example.localdbcachingtest.domain.base.common.BaseResult
import com.example.localdbcachingtest.domain.base.common.Failure
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity

class TodoRemoteSource constructor(private val todoApi: TodoApi) {

    suspend fun fetchTodos(): BaseResult<List<TodoEntity>, Failure> {
        try {
            val response = todoApi.fetchTodos()
            if (response.isSuccessful) {
                val todos = mutableListOf<TodoEntity>()
                response.body()?.forEach {
                    todos.add(
                        TodoEntity(
                            it.id,
                            it.title,
                            it.isCompleted
                        )
                    )
                }
                return BaseResult.Success(data = todos)
            } else {
                return BaseResult.Error(
                    Failure(
                        code = response.code(),
                        message = response.message()
                    )
                )
            }
        } catch (e: NoInternetException) {
            return BaseResult.Error(
                Failure(
                    code = 0,
                    message = e.message
                )
            )
        } catch (e: Exception) {
            return BaseResult.Error(
                Failure(
                    code = -1,
                    message = e.message.toString()
                )
            )
        }
    }
}