package com.example.miguelangelloor.proyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
//adartador para inflar elementos en un listview
public class Adaptador extends BaseAdapter {
    private ArrayList<itemNoticia> listItems;
    private Context context;

    public Adaptador(ArrayList<itemNoticia> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        itemNoticia item = (itemNoticia) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
        ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        TextView tvContenido = (TextView) convertView.findViewById(R.id.tvContenido);

        imgFoto.setImageResource(item.getImgFoto());
        tvTitulo.setText(item.getTitulo());
        tvContenido.setText(item.getContenido());
        return convertView;
    }
}
