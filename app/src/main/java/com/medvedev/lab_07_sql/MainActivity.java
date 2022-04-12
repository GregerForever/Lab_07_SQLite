package com.medvedev.lab_07_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Lab_07_SQLite Автор: Медведев Алексей 393 группа

public class MainActivity extends AppCompatActivity {

    //Обьявление текстового поля и базы данных
    EditText key;
    EditText value;
    DataBase DB;

    //Появление диалога при удалении значений
    public Dialog onCreateDialog(String key)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder
                .setTitle("Delete Value")
                .setIcon(R.drawable.eraser)
                .setMessage("Do you want to delete value?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (DB.doDelete(key))
                        { Toast("Delete is succesfully"); }
                        else
                        { Toast("Error: failed to delete value"); }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                })
                .create();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        key = findViewById(R.id.EditKey);
        value = findViewById(R.id.EditValue);

        DB = new DataBase(this, "database.db", null, 1);
    }

    //Действие при добавлении значений
    public void onInsert(View v)
    {
        String keyText = key.getText().toString();
        String valueText = value.getText().toString();

        boolean possib = DB.doInsert(keyText, valueText);
        if (possib == false)
        {
            Toast("Error: failed to insert value");
        }
        else
        {
            Toast("Insert is succesfully");
        }
    }

    //Действие при обновлении значений
    public void onReplace(View v)
    {
        String keyText = key.getText().toString();
        String valueText = value.getText().toString();

        boolean possib = DB.doUpdate(keyText, valueText);
        if (possib)
        { Toast("Update is succesfully"); }
        else
        { Toast("Error: failed to update value"); }
    }

    //Действие при выборке значений
    public void onSelect(View v)
    {
        String keyText = key.getText().toString();
        String res = DB.doSelect(keyText);
        if (res == "Error: not found")
        { Toast(res); }
        else
        {
            value.setText(DB.doSelect(keyText));
            Toast("Select is succesfully");
        }
    }

    //Подпрограмма вывод уведомления
    public void Toast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    //Действие при удалении значения
    public void onDelete(View v)
    {
        String keyText = key.getText().toString();

        Dialog delDialog = onCreateDialog(keyText);
        delDialog.show();
    }
}