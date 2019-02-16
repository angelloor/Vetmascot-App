package com.example.miguelangelloor.proyectofinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//clase extendida de sqlite
public class BaseHelper extends SQLiteOpenHelper {
    //sentencia sql para crear la tabla usuarios
    String tabla="CREATE TABLE Usuario(Id INTEGER PRIMARY KEY, Nombre TEXT, Correo TEXT, Estado TEXT)";
    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //metodo para crear la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }
    //metodo para actualizar la version de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Usuario");
        db.execSQL(tabla);
    }
}
