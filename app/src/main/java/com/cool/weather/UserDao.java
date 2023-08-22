package com.cool.weather;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author yejiasun
 * @date Create on 2023/8/20
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertList(List<UserEntity> UserEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(UserEntity UserEntity);

    @Query("SELECT * FROM user")
     List<UserEntity> getAll();
}
