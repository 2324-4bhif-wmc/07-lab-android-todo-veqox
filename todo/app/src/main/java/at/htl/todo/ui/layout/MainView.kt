package at.htl.todo.ui.layout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView @Inject constructor() {

    @Inject
    lateinit var store: ModelStore

    @Inject
    lateinit var todoView: TodoView

    fun buildContent(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        activity.setContent {
            val navController = rememberNavController();

            val viewModel = store
                .pipe
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAsState(initial = Model())
                .value

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box (
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        Text(
                            text = "${viewModel.todos.size} Todos",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )

                        Button(onClick = {
                            navController.navigate("todoView")
                        }) {
                            Text(text = "View Todos")
                        }
                    }
                }
            }

            NavHost(navController = navController, startDestination = "mainView") {
                composable("mainView") {

                }
                composable("todoView") {
                    todoView.buildContent(activity, navController)
                }
            }
        }
    }
}
