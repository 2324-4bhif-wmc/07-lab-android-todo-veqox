package at.htl.todo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;

import java.util.Arrays;

import javax.inject.Inject;

import at.htl.todo.model.ModelStore;
import at.htl.todo.model.TodoService;
import at.htl.todo.ui.layout.MainView;
import at.htl.todo.ui.layout.MainView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends ComponentActivity {
    static final String TAG = TodoApplication.class.getSimpleName();

    @Inject
    MainView mainView;

    @Inject
    ModelStore store;

    @Inject
    TodoService todoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todoService.getAll();

        mainView.buildContent(this);
    }

}