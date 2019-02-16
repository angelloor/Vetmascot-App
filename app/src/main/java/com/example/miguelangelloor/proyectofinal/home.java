package com.example.miguelangelloor.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nombre_home,correo;
    ViewGroup layout;
    String name,cor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LayoutInflater inflater = LayoutInflater.from(this);
        //action bar transparente
        this.getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        nombre_home = (TextView) findViewById(R.id.nombre);
        correo = (TextView) findViewById(R.id.correo);
        //cargar la informacion del usuario logueado
        cargar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        final FragmentManager fragmentManager=getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentVetmascot()).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //configuracion actionbar
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentHome()).commit();
        } else if (id == R.id.nav_sistem) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentVetmascot()).commit();

        } else if (id == R.id.nav_centroMascota) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentCentro()).commit();

        } else if (id == R.id.nav_catalogo) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentCatalogo()).commit();

        } else if (id == R.id.nav_ubicacion) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentUbicacion()).commit();

        } else if (id == R.id.nav_contacto) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentContacto()).commit();

        } else if (id == R.id.nav_noticias) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentNoticia()).commit();

        } else if (id == R.id.nav_configuracion) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentConf()).commit();

        }else if (id == R.id.nav_cerrar_sesion) {
            Eliminar();
            startActivity(new Intent(home.this,MainActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //funcion eliminar por id (cerrar sesion)
    private void Eliminar(){
        //llamado a la base de datos y ponerla en modo edicion
        BaseHelper helper = new BaseHelper(this,"account",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="delete from Usuario";
        db.execSQL(sql);
        db.close();
        System.exit(0);
    }
    //cargar la informacion del usuario logueado
    public void cargar(){
        //creamos un basehelper la cual nos identifica la base de datos
        BaseHelper helper = new BaseHelper(this,"account",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();

        String sql="select * from Usuario order by id desc limit 1";
        Cursor c= db.rawQuery(sql,null);

        while (c.moveToNext()){
            //concatenams el item para mostrarlo en el listview
            name =c.getString(1);
            cor =c.getString(2);
        }
        nombre_home.setText(name);
        correo.setText(cor);
        db.close();
    }
}
