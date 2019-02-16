package com.example.miguelangelloor.proyectofinal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentVetmascot extends Fragment {
    View vista;
    Button enviar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_fragment_vetmascot, container, false);

        enviar = (Button) vista.findViewById(R.id.btn_activar_sms);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarMensaje(v);
            }
        });
        return vista;
    }
    //funcion enviar sms
    public void EnviarMensaje(View v){
        String mensaje="ON";
        String numero="0992676024";
        try{
            //verificar si la app tiene permisos para enviar sms
            int permisos = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
            if (permisos != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "No tiene permisos.", Toast.LENGTH_LONG).show();
                //llamar a la ventana de activar permisos
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},255);
            }else{
                //envio del sms con los dos parametros mensaje y numero
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(numero, null, mensaje, null,null);
                Toast.makeText(getContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Mensaje no enviado, datos incorrectos." + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
