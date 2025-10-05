package com.example.cpsc411a_todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp(showToast = { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            })
        }
    }
}

data class TodoItem(
    val id: Int,
    val task: String,
    val isCompleted: Boolean = false
)

class TodoViewModel : ViewModel() {
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    var completedItems = mutableStateListOf<TodoItem>()
        private set

    private var nextId = 0

    fun addItem(task: String) {
        val trimmed = task.trim()
        if (trimmed.isNotEmpty()) {
            todoItems.add(TodoItem(id = nextId++, task = trimmed))
        }
    }

    fun deleteItem(item: TodoItem, completed: Boolean) {
        if (completed) completedItems.remove(item)
        else todoItems.remove(item)
    }

    fun toggleCompleted(item: TodoItem) {
        if (item.isCompleted) {
            completedItems.remove(item)
            todoItems.add(item.copy(isCompleted = false))
        } else {
            todoItems.remove(item)
            completedItems.add(item.copy(isCompleted = true))
        }
    }
}

@Composable
fun TodoApp(
    viewModel: TodoViewModel = viewModel(),
    showToast: (String) -> Unit
) {
    val todoItems = viewModel.todoItems
    val completedItems = viewModel.completedItems
    var text by rememberSaveable { mutableStateOf("") }

    Surface(color = Color(0xFF121212), modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "TODO List",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter the task name") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (text.trim().isEmpty()) {
                            showToast("Please enter a task")
                        } else {
                            viewModel.addItem(text)
                            text = ""
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TodoSection(
                title = "Items",
                items = todoItems,
                onToggle = { viewModel.toggleCompleted(it) },
                onDelete = { viewModel.deleteItem(it, false) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TodoSection(
                title = "Completed Items",
                items = completedItems,
                onToggle = { viewModel.toggleCompleted(it) },
                onDelete = { viewModel.deleteItem(it, true) }
            )
        }
    }
}

@Composable
fun TodoSection(
    title: String,
    items: List<TodoItem>,
    onToggle: (TodoItem) -> Unit,
    onDelete: (TodoItem) -> Unit
) {
    if (items.isEmpty()) {
        Text(
            text = "No $title yet",
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
    } else {
        Text(title, fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        items.forEach { item ->
            TodoRow(item, onToggle, onDelete)
        }
    }
}

@Composable
fun TodoRow(item: TodoItem, onToggle: (TodoItem) -> Unit, onDelete: (TodoItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Cyan)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(item.task, color = Color.White, modifier = Modifier.weight(1f))
        Checkbox(checked = item.isCompleted, onCheckedChange = { onToggle(item) })
        IconButton(onClick = { onDelete(item) }) {
            Icon(Icons.Filled.Close, contentDescription = "Delete", tint = Color.White)
        }
    }
}
