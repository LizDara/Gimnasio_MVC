package com.example.gym.Controllers;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.SalaModel;
import com.example.gym.R;
import com.example.gym.Views.SalaView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SalaController extends AppCompatActivity {
    private SalaView salaView;
    private SalaModel salaModel;
    private View view;

    @BindView(R.id.numero_sala)
    EditText numero_sala;
    @BindView(R.id.dimension_sala)
    EditText dimension_sala;
    @BindView(R.id.ubicacion_sala)
    EditText ubicacion_sala;
    @BindView(R.id.insertar_sala)
    Button insertar_sala;
    @BindView(R.id.modificar_sala)
    Button modificar_sala;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        getSupportActionBar().setTitle("SALA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        LinearLayout layout = findViewById(R.id.fragment_sala);
        view = getLayoutInflater().inflate(R.layout.fragment_sala, null);
        layout.addView(view);
        ButterKnife.bind(this);

        salaView = new SalaView(this, view);
        salaModel = new SalaModel();

        listar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.insertar_sala)
    public void insertar(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                salaModel.insertarDatos(Integer.parseInt(dimension_sala.getText().toString()), ubicacion_sala.getText().toString());
                salaModel.insertar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dimension_sala.setText("");
                        ubicacion_sala.setText("");
                        listar();
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.modificar_sala)
    public void editar(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                salaModel.insertarDatos(Integer.parseInt(dimension_sala.getText().toString()), ubicacion_sala.getText().toString());
                salaModel.editar(Integer.parseInt(numero_sala.getText().toString()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numero_sala.setText("");
                        dimension_sala.setText("");
                        ubicacion_sala.setText("");
                        insertar_sala.setVisibility(View.VISIBLE);
                        modificar_sala.setVisibility(View.GONE);
                        listar();
                    }
                });
            }
        }).start();
    }

    public void listar() {
        salaView.listar();
    }
}
