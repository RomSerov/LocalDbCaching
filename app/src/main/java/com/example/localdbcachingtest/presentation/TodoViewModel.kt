package com.example.localdbcachingtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localdbcachingtest.domain.base.common.BaseResult
import com.example.localdbcachingtest.domain.todo.entity.TodoEntity
import com.example.localdbcachingtest.domain.todo.usecase.FetchTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val fetchTodosUseCase: FetchTodosUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<MainActivityState>(MainActivityState.Init)
    val state: StateFlow<MainActivityState> get() = _state

    private val _todos = MutableStateFlow(mutableListOf<TodoEntity>())
    val todos: StateFlow<List<TodoEntity>> get() = _todos

    private fun setLoading() {
        _state.value = MainActivityState.Loading(true)
    }

    private fun hideLoading() {
        _state.value = MainActivityState.Loading(false)
    }

    private fun showToast(msg: String) {
        _state.value = MainActivityState.ShowToast(message = msg)
    }

    fun fetchTodos() {
        viewModelScope.launch {
            fetchTodosUseCase.invoke()
                .onStart { setLoading() }
                .catch { e ->
                    hideLoading()
                    showToast(e.message.toString())
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> {
                            _todos.value = it.data as MutableList<TodoEntity>
                        }
                        is BaseResult.Error -> {
                            if (it.err.code != 0) {
                                showToast(msg = it.err.message)
                            }
                        }
                    }
                }
        }
    }
}

sealed class MainActivityState {
    object Init : MainActivityState()
    data class Loading(val isLoading: Boolean) : MainActivityState()
    data class ShowToast(val message: String) : MainActivityState()
}