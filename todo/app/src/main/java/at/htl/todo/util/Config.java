package at.htl.todo.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static final String TAG = Config.class.getSimpleName();
    private static Properties properties;

    public static void load(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("application.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}