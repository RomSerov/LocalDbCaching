package com.example.localdbcachingtest.data.todo

import com.example.localdbcachingtest.data.todo.local.TodoDao
import com.example.localdbcachingtest.data.todo.remote.TodoRemoteSource
import com.example.localdbcachingtest.domain.base.common.BaseResult
import com.example.localdbcachingtest.domain.base.common.Failure
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity
import com.example.localdbcachingtest.domain.todo.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepositoryImpl constructor(
    private val todoRemoteSource: TodoRemoteSource,
    private val todoDao: TodoDao ) :
    TodoRepository {

    override suspend fun fetchTodos(): Flow<BaseResult<List<TodoEntity>, Failure>> {
        return flow {
            val localTodos = todoDao.findAll()
            emit(BaseResult.Success(data = localTodos))

            if (localTodos.isEmpty()) {
                val result = todoRemoteSource.fetchTodos()
                if (result is BaseResult.Success) {
                    todoDao.deleteAll()
                    todoDao.insertAll(result.data)
                }
                emit(result)
            }
        }
    }
}