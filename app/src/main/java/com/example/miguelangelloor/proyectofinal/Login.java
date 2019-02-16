package com.example.miguelangelloor.proyectofinal;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.Toast.LENGTH_LONG;

public class Login extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    //objetos volley para enviar un cadena http
    RequestQueue rq;
    JsonRequest jrq;

    EditText correo,clave;
    public String nombre_usuario;
    Button btn_iniciar,btn_crearcuenta,btn_olvidaste,btn_atras;
    private android.widget.CheckBox CheckBox;
    public static String id=null,nombre=null,correos=null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //action bar transparente
        this.getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        //llamar a la funcion de la configuracion del action bar
        setupActionBar();
        //iniciamos un nuevo request
        rq  = Volley.newRequestQueue(Login.this);

        correo = (EditText) findViewById(R.id.et_correo_login);
        clave = (EditText) findViewById(R.id.et_clave_login);
        btn_iniciar= (Button) findViewById(R.id.btn_iniciar);
        btn_crearcuenta=(Button) findViewById(R.id.btn_crearcuenta);
        btn_olvidaste=(Button) findViewById(R.id.btn_olvidaste);
        btn_atras=(Button) findViewById(R.id.btn_atras);
        CheckBox = (CheckBox) findViewById(R.id.checkbox);

        //boton atras
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
            }
        });

        //invocar activity register
        btn_crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,register.class));
            }
        });
        //invocar activity recovery
        btn_olvidaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,recovery.class));
            }
        });

        //consultar el usuario en un bd externa
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validadar los campos
                if ((correo.length() == 0 || clave.length() == 0)==true){
                    new SweetAlertDialog(Login.this).setTitleText("Ingrese todos los campos!").show();
                }else {
                    if (validarCorreo(correo.getText().toString())==false){
                        new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Correo inválido").show();
                    }else {
                        if(CheckBox.isChecked()){
                            iniciarSesion();
                        }else {
                            iniciarSesion();
                        }
                    }
                }
            }
        });
    }
    //Metodos libreria volley
    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(Login.this,"Error!! "+ error.toString(),Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE).setTitleText("problema con la conexión!!").show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario user = new Usuario();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            //obtener los valores del json y almacenarlos un nuestra clase usuario
            jsonObject = jsonArray.getJSONObject(0);
            user.setUsuario(jsonObject.optString("correo"));
            user.setClave(jsonObject.optString("clave"));
            user.setNombre(jsonObject.optString("nombre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nombre_usuario=user.getNombre();
        String usuario = user.getUsuario();
        correos=usuario;
        //verificamos si el retorno del request es igual a "" significa que las credenciales estan incorrectas segun la logica de nuestro php
        if (usuario.equals("")){
            new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Usuario o contraseña incorrectos").show();
        }else {
            //invocamos un inten con los datos correo y nombre
            Intent intent = new Intent(Login.this,home.class);
            intent.putExtra("correo",user.getUsuario());
            intent.putExtra("nombre",user.getNombre());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            //enviar datos al formulario de contacto
            id=user.getUsuario();
            nombre=user.getNombre();
            correos=user.getUsuario();
            guardar(1,nombre_usuario,correo.getText().toString(),"on");
        }
    }
    //funcion para enviar un cadena http al servidoor
    private void iniciarSesion(){
        String url ="http://uevalladares.edu.ec/vetmascot/sesion.php?correo="+correo.getText().toString().trim()+
                "&clave="+clave.getText().toString().trim();
        jrq = new JsonObjectRequest(Request.Method.GET,url, null,this,this);
        rq.add(jrq);
    }

    //funcion para validar correo
    private boolean validarCorreo(String correo) {
        String emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
    //validacion numero
    public boolean validarNumero(String numero){
        int num;
        try{
            num = Integer.parseInt(numero);
            return true;

        }catch (Exception e){
            return false;
        }
    }
    //configuracion actionbar
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
    //funcion para guardar datos en sqlite (en la parte grafica nos siirve para "Recuerdame")
    public void guardar(Integer Id, String Nombre, String Correo, String Estado){
        //llamado a la base de datos y ponerla en modo escritura
        BaseHelper helper = new BaseHelper(this,"account",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        try {
            //creamos un contenedor de valores
            ContentValues c= new ContentValues();
            c.put("Id",Id);
            c.put("Nombre",Nombre);
            c.put("Correo",Correo);
            c.put("Estado",Estado);
            //ingresamos lo valores a la base de datos
            db.insert("USUARIO","",c);
            //cerramos la conexion
            db.close();
            //mensaje de confimacion
        }catch (Exception e){
            //mensaje de confimacion
            Toast.makeText(this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
