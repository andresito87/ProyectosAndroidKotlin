package com.example.makeitso.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Task
import com.example.makeitso.theme.MakeItSoTheme
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun TasksScreen(
    openScreen: (String) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {

    val tasks by viewModel
        .tasks
        .collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options

    TasksScreenContent(
        tasks,
        options,
        onAddClick = viewModel::onAddClick,
        onSettingsClick = viewModel::onSettingsClick,
        onTaskCheckChange = viewModel::onTaskCheckChange,
        onTaskActionClick = viewModel::onTaskActionClick,
        openScreen = openScreen
    )

    LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun TasksScreenContent(
    tasks: List<Task>,
    options: List<String>,
    modifier: Modifier = Modifier,
    onAddClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onTaskCheckChange: (Task) -> Unit,
    onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
    openScreen: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolbar(
                title = AppText.tasks,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn {
                items(tasks, key = { it.id }) { taskItem ->
                    TaskItem(
                        task = taskItem,
                        options = options,
                        onCheckChange = { onTaskCheckChange(taskItem) },
                        onActionClick = { action ->
                            onTaskActionClick(
                                openScreen,
                                taskItem,
                                action
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun TasksScreenPreview() {
    val tasks: List<Task> = remember {
        listOf(
            Task(
                id = "1",
                title = "Task 1",
                priority = "High",
                dueDate = "2026-01-27",
                dueTime = "10:00",
                description = "First demo task",
                url = "https://example.com/task-1",
                flag = false,
                completed = false,
                userId = "demo-user"
            ),
            Task(
                id = "2",
                title = "Task 2",
                priority = "Low",
                dueDate = "2026-01-28",
                dueTime = "18:30",
                description = "Second demo task",
                url = "https://example.com/task-2",
                flag = true,
                completed = false,
                userId = "demo-user"
            )
        )
    }
    MakeItSoTheme {
        TasksScreenContent(
            tasks,
            options = listOf(),
            onAddClick = { },
            onSettingsClick = { },
            onTaskCheckChange = { },
            onTaskActionClick = { _, _, _ -> },
            openScreen = { }
        )
    }
}
