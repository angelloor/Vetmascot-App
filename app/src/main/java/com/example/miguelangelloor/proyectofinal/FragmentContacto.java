package com.example.miguelangelloor.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentContacto extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    //objetos de volley para transferencia http
    RequestQueue rq;
    JsonRequest jrq;

    Button btn_enviar;
    EditText et_mensaje;
    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_fragment_contacto, container, false);
        //inicializar request
        rq  = Volley.newRequestQueue(getContext());

        et_mensaje=(EditText) vista.findViewById(R.id.et_Mensaje_contacto);

        //boton regresar
        btn_enviar = (Button) vista.findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validacion de campos
                if ((et_mensaje.length() == 0)==true){
                    new SweetAlertDialog(getContext()).setTitleText("Ingrese el mensaje antes de enviarlo").show();
                }else {
                    if (et_mensaje.length()>254){
                        new SweetAlertDialog(getContext()).setTitleText("Mensaje muy extenso!! (255 max)").show();
                    }else {
                        guardar();
                        et_mensaje.setText("");
                    }
                }
            }
        });
        return vista;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(getContext(),"Error!! "+ error.toString(),Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("problema con la conexión!!").show();
    }

    @Override
    public void onResponse(JSONObject response) {
        new SweetAlertDialog(getContext()).setTitleText("Enviado!!").show();
    }
    //funcion para el envio de la cadena http al servidor
    private void guardar(){
        String url ="http://uevalladares.edu.ec/vetmascot/guardarcontacto.php?correo="+Login.id+
                "&mensaje="+et_mensaje.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url, null,this,this);
        rq.add(jrq);
    }
}
