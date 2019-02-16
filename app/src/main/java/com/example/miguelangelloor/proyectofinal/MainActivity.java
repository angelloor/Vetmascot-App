package com.example.miguelangelloor.proyectofinal;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu    ;

public class MainActivity extends AppCompatActivity {
    Button btn_iniciar;
    TextView mensaje;
    //variable para comprobar si hay un usuario guardado en la base de datos sqlite
    Integer state=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //action bar transparente
        this.getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        //llamar a la funcion de la configuracion del action bar
        setupActionBar();
        //verificar si hay un usuario guardado en sqlite
        verificar();

        mensaje =(TextView) findViewById(R.id.txt_mensaje);

        btn_iniciar=findViewById(R.id.btn_iniciar);
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state==1){
                    startActivity(new Intent(MainActivity.this,home.class));
                }else {
                    startActivity(new Intent(MainActivity.this,Login.class));
                }
            }
        });

    }

    //funcion para verificar si hay un usuario logueado
    public void verificar() {
        //creamos un basehelper la cual nos identifica la base de datos
        BaseHelper helper = new BaseHelper(this, "account", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "select Id, Nombre, Correo, Estado from Usuario WHERE Id=1";
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            //concatenams el item para mostrarlo en el listview
            state=state+1;
        }
        db.close();
    }

    //configuracion actionbar
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle("");
        }
    }

    //inflar el menu en el actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    //selector de opciones del menu
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.acerca:
                startActivity(new Intent(getApplicationContext(), acerca.class));
                finish();
                break;
            case R.id.salir:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

}
