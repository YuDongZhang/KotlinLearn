package com.pdxx.kotlinlearn.mvi

data class Todo(val id: Int, val title: String, var isCompleted: Boolean)

class TodoListModel {

    private val todoList = mutableListOf<Todo>()

    fun getTodoList(): List<Todo> {
        return todoList
    }

    fun addTodo(title: String) {
        val newTodo = Todo(todoList.size + 1, title, false)
        todoList.add(newTodo)
    }

    fun markTodoAsCompleted(todoId: Int) {
        val todo = todoList.find { it.id == todoId }
        todo?.isCompleted = true
    }
}
