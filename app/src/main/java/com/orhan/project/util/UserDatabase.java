package com.orhan.project.util;

import android.content.Context;
import android.os.AsyncTask;

import com.orhan.project.model.User;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;
    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        private PopulateDbAsyncTask(UserDatabase db) {
            userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
