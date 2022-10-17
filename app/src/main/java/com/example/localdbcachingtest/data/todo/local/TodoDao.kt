package com.example.localdbcachingtest.data.todo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoEntity: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todoEntity: List<TodoEntity>)

    @Query("SELECT * FROM todos")
    fun findAll(): List<TodoEntity>

    @Query("DELETE FROM todos")
    fun deleteAll()
}