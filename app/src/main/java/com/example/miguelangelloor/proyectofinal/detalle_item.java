package com.example.miguelangelloor.proyectofinal;

import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Build;
import android.service.autofill.ImageTransformation;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class detalle_item extends AppCompatActivity {
    private itemNoticia Item;
    private TextView tvTitulo, tvDescripcion;
    private ImageView imgFoto;
    private Button btn_salir;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_item);
        //action bar transparente
        this.getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        //llamar a la funcion de la configuracion del action bar
        setupActionBar();
        //recibir los valores enviados desde el Fragmento noticia
        Item = (itemNoticia) getIntent().getSerializableExtra("objetoData");

        tvTitulo= (TextView) findViewById(R.id.tvTitulo);
        tvDescripcion=(TextView) findViewById(R.id.TvDescripcion);
        imgFoto =(ImageView) findViewById(R.id.imgFoto);

        tvTitulo.setText(Item.getTitulo());
        imgFoto.setImageResource(Item.getImgFoto());
        tvDescripcion.setText(Item.getContenido());

        btn_salir= (Button) findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //configuracion actionbar
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
