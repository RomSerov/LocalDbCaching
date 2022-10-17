package com.example.localdbcachingtest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localdbcachingtest.data.todo.local.TodoDao
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}