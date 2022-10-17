package com.example.localdbcachingtest.data.todo.module

import com.example.localdbcachingtest.data.AppDatabase
import com.example.localdbcachingtest.data.todo.TodoRepositoryImpl
import com.example.localdbcachingtest.data.todo.local.TodoDao
import com.example.localdbcachingtest.data.todo.remote.TodoRemoteSource
import com.example.localdbcachingtest.data.todo.remote.api.TodoApi
import com.example.localdbcachingtest.domain.todo.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun provideTodoApi(retrofit: Retrofit): TodoApi {
        return retrofit.create(TodoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTodoRemoteSource(todoApi: TodoApi): TodoRemoteSource {
        return TodoRemoteSource(todoApi)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoRemoteSource: TodoRemoteSource, todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoRemoteSource, todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: AppDatabase): TodoDao {
        return appDatabase.todoDao()
    }
}