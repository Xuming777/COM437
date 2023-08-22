package com.cool.weather;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author yejiasun
 * @date Create on 2023/8/20
 */
@Entity(tableName = "user")
class UserEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "password")
    public String password;
}
