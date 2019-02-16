package com.example.miguelangelloor.proyectofinal;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class register extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    //objetos volley para enviar un cadena http
    RequestQueue rq;
    JsonRequest jrq;

    Button btn_regresar,btn_enviar;
    EditText et_nombre, et_direccion, et_telefono,et_correo,et_clave;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //action bar transparente
        this.getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        //llamar a la funcion de la configuracion del action bar
        setupActionBar();

        //iniciamos un nuevo request
        rq  = Volley.newRequestQueue(register.this);

        et_nombre = (EditText) findViewById(R.id.et_nombre_register);
        et_direccion = (EditText) findViewById(R.id.et_direccion_register);
        et_telefono = (EditText) findViewById(R.id.et_telefono_register);
        et_correo = (EditText) findViewById(R.id.et_correo_register);
        et_clave = (EditText) findViewById(R.id.et_clave_register);
        btn_enviar = (Button) findViewById(R.id.btn_enviar_register);
        btn_regresar = (Button) findViewById(R.id.btn_regresar);
        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,Login.class));
                finish();
            }
        });
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar campos
                if ((et_nombre.length() == 0 || et_direccion.length() == 0 || et_telefono.length() == 0 || et_correo.length() == 0 || et_clave.length() == 0)==true){
                    new SweetAlertDialog(register.this).setTitleText("Ingrese todos los campos!").show();
                }else {
                    if (validarCorreo(et_correo.getText().toString())==false){
                        new SweetAlertDialog(register.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Correo Invalido").show();
                    }else {
                        if ((et_telefono.length()<=15 && validarNumero(et_telefono.getText().toString()))==false){
                            new SweetAlertDialog(register.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Telefono Incorrecto").show();
                        }else {
                            guardarUsuario();
                        }
                    }
                }
            }
        });
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

    @Override
    public void onErrorResponse(VolleyError error) {
        new SweetAlertDialog(register.this,SweetAlertDialog.ERROR_TYPE).setTitleText("problema con la conexión!!").show();
        //Toast.makeText(register.this,"Error!! "+ error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //obtener la respuesto del json enviado desde el servidor
        mensajePersonalizado mensaje = new mensajePersonalizado();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            mensaje.setMensaje(jsonObject.optString("respuesta"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sms = mensaje.getMensaje();
        //condicionamos el mensaje de acuerdo a lo que nos enviar nuestro servidor
        if (sms.equals("existente")){
            new SweetAlertDialog(register.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Usuario no disponible").show();
            et_correo.setText("");
        }else {
            new SweetAlertDialog(register.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("Usuario Registrado!").show();
            et_nombre.setText("");
            et_direccion.setText("");
            et_telefono.setText("");
            et_correo.setText("");
            et_clave.setText("");
        }

    }
    //funcion para enviar un cadena http al servidoor
    private void guardarUsuario(){
        String url ="http://uevalladares.edu.ec/vetmascot/crearusuario.php?nombre="+et_nombre.getText().toString().trim()+
                "&direccion="+et_direccion.getText().toString().trim()+"&telefono="+et_telefono.getText().toString().trim()+"&correo="+et_correo.getText().toString().trim()+"&clave="+et_clave.getText().toString().trim();
        jrq = new JsonObjectRequest(Request.Method.GET,url, null,this,this);
        rq.add(jrq);
    }
}
