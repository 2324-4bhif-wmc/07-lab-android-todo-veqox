package at.htl.todo.ui.layout

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.Todo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView @Inject constructor() {

    @Inject
    lateinit var store: ModelStore

    @SuppressLint("RestrictedApi", "UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value;

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold (
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Todos")
                            })
                    }
                ) {
                    LazyColumn (
                        modifier = Modifier.padding(it)
                    ) {
                        items(viewModel.todos.size) { index ->
                            TodoRow(todo  = viewModel.todos[index], index = index)
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TodoRow(todo: Todo, index: Int) {
        ListItem(
            headlineContent = {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            trailingContent = {
                Checkbox(
                    checked = todo.completed,
                    onCheckedChange = {
                        store.apply { model ->
                            model.todos[index].completed = it;
                        }
                    }
                )
            });

    }
}
