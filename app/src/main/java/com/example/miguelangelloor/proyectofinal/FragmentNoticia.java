package com.example.miguelangelloor.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentNoticia extends Fragment {
    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<itemNoticia> arrayentidad;
    View vista ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment_noticia, container, false);
        //item noticias
        lvItems = (ListView) vista.findViewById(R.id.lvItems);
        arrayentidad = GetArrayItems();
        adaptador = new Adaptador(arrayentidad, getContext());
        lvItems.setAdapter(adaptador);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), detalle_item.class);
                intent.putExtra("objetoData", arrayentidad.get(position));
                startActivity(intent);
            }
        });
        return vista;
    }
    //funcion para cargar item estaticos en el fragmento
    private ArrayList<itemNoticia> GetArrayItems(){
        ArrayList<itemNoticia> listItems = new ArrayList<>();
        listItems.add(new itemNoticia(R.drawable.cachorro,"¡Bienvenido a casa!","Cuando llevamos a nuestro nuevo cachorro a casa, debemos tener en cuenta que deja un entorno con su madre y hermanos, donde se siente seguro, para ir a un lugar completamente desconocido. Como esto puede ser una experiencia traumática para él, trataremos de que esa transición sea lo más reconfortante posible."));
        listItems.add(new itemNoticia(R.drawable.comida,"Alimentación mixta para perros. ¿es posible algo mejor?","La alimentación del perro es clave para mantener su salud y aumentar su esperanza de vida. A la hora de elegir la alimentación de nuestro perro debemos tener en cuenta que sea saludable, nutritiva y adaptada a sus necesidades."));
        listItems.add(new itemNoticia(R.drawable.perros,"Descubre las razas de perros","Sólo una nutrición adaptada puede satisfacer sus necesidades. Cada tipo de perro, según su tamaño o raza y edad, tiene diferentes características, y en VetMascot estamos comprometidos con cada uno de ellos, adaptándonos a sus necesidades nutricionales a lo largo de toda su vida."));
        listItems.add(new itemNoticia(R.drawable.gatos,"Descubre las razas de gatos ","Sólo una nutrición adaptada puede satisfacer sus necesidades. Cada tipo de gato, según su pelaje, raza y edad, tiene diferentes características, y en VetMascot estamos comprometidos con cada uno de ellos, adaptándonos a sus necesidades nutricionales a lo largo de toda su vida."));
        listItems.add(new itemNoticia(R.drawable.casa,"Las primeras noches del cachorro en casa ","Es normal que un cachorro llore, gima o ladre cuando le dejamos en su sitio para dormir las primeras noches en su nueva casa. Se debe tener en cuenta que estar solo es algo completamente nuevo para él; desde que nació, ha estado acompañando en todo momento por su madre y sus hermanos."));
        return listItems;
    }
}
