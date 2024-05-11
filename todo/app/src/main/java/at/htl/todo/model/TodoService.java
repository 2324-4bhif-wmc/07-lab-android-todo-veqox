package at.htl.todo.model;


import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.todo.util.Config;
import at.htl.todo.util.resteasy.RestApiClientBuilder;

@Singleton
public class TodoService {
    static final String TAG = TodoService.class.getSimpleName();
    public final TodoClient todoClient;
    public final ModelStore store;

    @Inject
    TodoService(RestApiClientBuilder builder, ModelStore store) {
        Log.i(TAG, "Creating TodoService with base url: " + Config.getProperty("json.placeholder.baseurl"));
        todoClient = builder.build(TodoClient.class, Config.getProperty("json.placeholder.baseurl"));
        this.store = store;
    }

    public void getAll() {
        CompletableFuture
                .supplyAsync(todoClient::all)
                .thenAccept(store::setTodos);
    }
}

