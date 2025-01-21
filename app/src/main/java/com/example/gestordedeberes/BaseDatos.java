package com.example.gestordedeberes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String nombreBD = "BaseDeDatos";
    private static final int versionBD = 1;

    public BaseDatos(@Nullable Context context) {
        super(context, nombreBD, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table tareas (" +
                "id integer primary key autoincrement," +
                "asignatura text," +
                "titulo text," +
                "descripcion text," +
                "fecha text," +
                "hora text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE usuarios");
        onCreate(sqLiteDatabase);
    }
}
