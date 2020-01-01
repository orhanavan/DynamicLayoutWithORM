package com.orhan.project.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.orhan.project.model.User;

import java.util.ArrayList;

public class UserRepository {

    private UserDao userDao;
    private Context context;

    public UserRepository(Context context) {
        UserDatabase database = UserDatabase.getInstance(context);
        userDao = database.userDao();
        this.context = context;
    }

    public void insert(User user) {

        new InsertNoteAsyncTask(userDao).execute(user);
    }

    public void getUserList(OnDataPass onDataPass) {
        new GetUserAsyncTask(userDao, onDataPass).execute();
    }

    private static class GetUserAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;
        private ArrayList<User> users;
        private OnDataPass onDataPass;

        public GetUserAsyncTask(UserDao userDao, OnDataPass onDataPass) {
            this.userDao = userDao;
            this.onDataPass = onDataPass;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            users = (ArrayList<User>) userDao.getAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            onDataPass.OnTaskComplete(users);

        }
    }

    private static class InsertNoteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertNoteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
