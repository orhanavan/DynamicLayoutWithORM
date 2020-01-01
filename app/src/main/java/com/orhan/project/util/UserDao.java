package com.orhan.project.util;

import com.orhan.project.model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();
}
