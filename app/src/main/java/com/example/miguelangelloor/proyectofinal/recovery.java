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

public class recovery extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    //objetos volley para enviar un cadena http
    RequestQueue rq;
    JsonRequest jrq;

    Button btn_regresar,btn_enviar;
    EditText et_correo;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        //action bar transparente
        this.getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        //llamar a la funcion de la configuracion del action bar
        setupActionBar();

        //iniciamos un nuevo request
        rq  = Volley.newRequestQueue(recovery.this);

        et_correo = (EditText) findViewById(R.id.et_correo_recovery);
        btn_enviar = (Button) findViewById(R.id.btn_enviar_recovery);
        btn_regresar = (Button) findViewById(R.id.btn_regresar);
        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(recovery.this,Login.class));
                finish();
            }
        });

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar campos
                if ((et_correo.length() == 0 )==true){
                    new SweetAlertDialog(recovery.this).setTitleText("Ingrese el correo electronico!").show();
                }else {
                    if (validarCorreo(et_correo.getText().toString())==false){
                        new SweetAlertDialog(recovery.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Correo Invalido!").show();
                    }else {
                        recovery();
                        et_correo.setText("");
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
        //Toast.makeText(recovery.this,"Error!! "+ error.toString(),Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(recovery.this,SweetAlertDialog.ERROR_TYPE).setTitleText("problema con la conexión!!").show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //obtener la respuesto del json enviado desde el servidor
        mensajePersonalizado mensaje = new mensajePersonalizado();
        JSONArray jsonArray = response.optJSONArray("respuesta");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            mensaje.setMensaje(jsonObject.optString("mensaje"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ///mensaje
        new SweetAlertDialog(recovery.this).setTitleText(mensaje.getMensaje()).show();
    }

    //funcion para enviar un cadena http al servidoor
    private void recovery(){
        String url ="http://uevalladares.edu.ec/vetmascot/recoveryapk.php?correo="+et_correo.getText().toString().trim();
        jrq = new JsonObjectRequest(Request.Method.GET,url, null,this,this);
        rq.add(jrq);
    }
}
