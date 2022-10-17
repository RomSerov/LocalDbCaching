package com.example.localdbcachingtest.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.localdbcachingtest.databinding.ActivityMainBinding
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity
import com.example.localdbcachingtest.presentation.adapter.TodoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TodoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observe()
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModel.fetchTodos()
    }

    private fun setupRecyclerView() {
        binding.todoRecyclerView.apply {
            adapter = TodoAdapter(mutableListOf())
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observe() {
        observeState()
        observeTodos()
    }

    private fun observeTodos() {
        viewModel.todos.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleTodos(it) }
            .launchIn(lifecycleScope)
    }

    private fun observeState() {
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleState(it) }
            .launchIn(lifecycleScope)
    }

    private fun handleTodos(listTodo: List<TodoEntity>) {
        binding.todoRecyclerView.adapter?.let {
            if (it is TodoAdapter) {
                it.update(list = listTodo)
            }
        }
    }

    private fun handleState(state: MainActivityState) {
        when (state) {
            is MainActivityState.ShowToast -> Toast.makeText(this, state.message, Toast.LENGTH_LONG)
                .show()
            is MainActivityState.Loading -> handleLoading(state.isLoading)
            is MainActivityState.Init -> Unit
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.loadingBar.visibility = View.VISIBLE
        } else {
            binding.loadingBar.visibility = View.GONE
        }
    }
}