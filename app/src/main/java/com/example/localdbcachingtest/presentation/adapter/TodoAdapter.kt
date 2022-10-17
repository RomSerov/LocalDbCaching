package com.example.localdbcachingtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.localdbcachingtest.databinding.ItemTodoBinding
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity

class TodoAdapter(private val todos: MutableList<TodoEntity>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        return holder.bind(todoEntity = todos[position])
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoEntity: TodoEntity) {
            binding.titleTextView.text = todoEntity.title
            binding.isCompleteTextView.text = if (todoEntity.isComplete) "Выполнено" else "Не выполнено"
        }
    }

    fun update(list: List<TodoEntity>) {
        todos.clear()
        todos.addAll(list)
        notifyDataSetChanged()
    }
}