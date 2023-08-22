package com.cool.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author yejiasun
 * @date Create on 2023/8/20
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "AppDataBase.db";
    private static volatile AppDatabase instance;


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = createDatabase(context);
        }
        return instance;
    }

    private static AppDatabase createDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).addCallback(new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        }).build();
    }

    public abstract UserDao userDao();


}
