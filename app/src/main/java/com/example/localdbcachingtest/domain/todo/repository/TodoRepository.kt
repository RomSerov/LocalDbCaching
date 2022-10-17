package com.example.localdbcachingtest.domain.todo.repository

import com.example.localdbcachingtest.domain.base.common.BaseResult
import com.example.localdbcachingtest.domain.base.common.Failure
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun fetchTodos(): Flow<BaseResult<List<TodoEntity>, Failure>>
}