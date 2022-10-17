package com.example.localdbcachingtest.domain.todo.usecase

import com.example.localdbcachingtest.domain.base.common.BaseResult
import com.example.localdbcachingtest.domain.base.common.Failure
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity
import com.example.localdbcachingtest.domain.todo.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTodosUseCase @Inject constructor(private val todoRepository: TodoRepository) {

    suspend fun invoke(): Flow<BaseResult<List<TodoEntity>, Failure>> {
        return todoRepository.fetchTodos()
    }
}