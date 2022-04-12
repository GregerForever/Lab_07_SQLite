package com.medvedev.lab_07_sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

//Lab_07_SQLite Автор: Медведев Алексей 393 группа

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE my_test (my_key TEXT, my_value TEXT);";
        db.execSQL(sql);
    }

    //Поиск значений
    public boolean Search(String key)
    {
        String check = "SELECT my_key FROM my_test WHERE my_key = '" + key + "';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery(check, null);
        if (cur.moveToFirst() == true)
        { return true; }
        else
        { return false; }
    }

    //Добавление значений
    public boolean doInsert(String key, String value)
    {
        if (Search(key) == false)
        {
            String sql = "INSERT INTO my_test VALUES('" + key + "', '" + value + "');";
            SQLiteDatabase db = getWritableDatabase();
            getWritableDatabase().execSQL(sql);
            return true;
        }
        else
        { return false; }
    }

    //выборка значений
    public String doSelect(String key)
    {
        String sql = "SELECT my_value FROM my_test WHERE my_key = '" + key + "';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true)
        { return cur.getString(0); }
        return "Error: not found";
    }

    //Обновление значений
    public boolean doUpdate(String key, String value)
    {
        if (Search(key) == true)
        {
            String upd = "UPDATE my_test SET my_value = '" + value + "' WHERE my_key = '" + key + "';";
            getWritableDatabase().execSQL(upd);
            return true;
        }
        else
        { return false; }
    }

    //Удаление значений
    public boolean doDelete(String key)
    {
        if (Search(key) == true)
        {
            String del = "DELETE FROM my_test WHERE my_key='" + key + "';";
            getWritableDatabase().execSQL(del);
            return true;
        }
        else
        { return false; }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
