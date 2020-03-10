package com.example.a20151094010099.mqttteste;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import AppBD.BD;
import Models.Placa;

public class ListaDePlacas extends AppCompatActivity {

    private ArrayAdapter<Placa> placaArrayAdapter = null;
    Placa placa;
    ArrayList<Placa> aux = new ArrayList<>();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BD.placas.clear();
        Intent intent = new Intent(getApplicationContext(),Carrega.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_placas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Lista de Placas");
        aux.clear();
        for (Placa p: BD.placas){
            aux.add(p);
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);

        final ListView listaPlacas = (ListView) findViewById(R.id.placas);
        //Set conjuntoDeBeans = new HashSet(BD.placas);
        //ArrayList<Placa> t = new ArrayList<>(conjuntoDeBeans);
        Collections.sort(BD.placas);
        if (BD.cont == 1) {

            placaArrayAdapter = new ArrayAdapter<Placa>(getApplicationContext(), android.R.layout.simple_list_item_1,BD.placas);
            listaPlacas.setAdapter(placaArrayAdapter);
            listaPlacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    messageDialog(position);
                    placa = (Placa) parent.getItemAtPosition(position);
                }

            });
        }
    }

    public void messageDialog(final int id){
        new AlertDialog.Builder(ListaDePlacas.this,R.style.Theme_AppCompat_DayNight).setTitle("Deletando Placa").
                setMessage("Tem certeza que deseja deletar a placa "+BD.placas.get(id).getPlaca()+"?").setIcon(R.drawable.ic_error_outline_black_24dp).setPositiveButton("sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BD.getFirebase().child("placas").child(BD.placas.get(id).getId()).removeValue();
                        BD.placas.clear();
                        Intent intent = new Intent(getApplicationContext(),Carrega.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("não", null)
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cadastra_placa:
                Intent intent = new Intent(getApplicationContext(), CadastrarPlaca.class);
                startActivity(intent);
                break;
            case R.id.listar_placas:
                Toast.makeText(getApplicationContext(), "Lista de Placas", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
