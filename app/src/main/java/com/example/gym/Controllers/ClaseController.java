package com.example.gym.Controllers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.ClaseModel;
import com.example.gym.R;
import com.example.gym.Views.ClaseView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClaseController extends AppCompatActivity {
    private ClaseView claseView;
    private ClaseModel claseModel;
    private View view;

    @BindView(R.id.numero_clase)
    EditText numero_clase;
    @BindView(R.id.nombre_clase)
    EditText nombre_clase;
    @BindView(R.id.descripcion_clase)
    EditText descripcion_clase;
    @BindView(R.id.dia_clase)
    EditText dia_clase;
    @BindView(R.id.hora_clase)
    EditText hora_clase;
    @BindView(R.id.precio_clase)
    EditText precio_clase;
    @BindView(R.id.numero_sala_clase)
    Spinner numero_sala_clase;
    @BindView(R.id.ci_instructor_clase)
    Spinner ci_instructor_clase;
    @BindView(R.id.insertar_clase)
    Button insertar_clase;
    @BindView(R.id.modificar_clase)
    Button modificar_clase;
    @BindView(R.id.clases)
    RecyclerView clases;

    public List<Map<String, Object>> listSala;
    public List<Map<String, Object>> listInstructor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase);

        getSupportActionBar().setTitle("CLASE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        LinearLayout layout = findViewById(R.id.fragment_clase);
        view = getLayoutInflater().inflate(R.layout.fragment_clase, null);
        layout.addView(view);
        ButterKnife.bind(this);

        claseView = new ClaseView(this, view);
        claseModel = new ClaseModel();

        listar();
        listarSala();
        listarInstructor();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.insertar_clase)
    public void insertar() {
        listSala = claseView.listSala;
        listInstructor = claseView.listInstructor;
        new Thread(new Runnable() {
            @Override
            public void run() {
                claseModel.insertarDatos(nombre_clase.getText().toString(), descripcion_clase.getText().toString(),
                        dia_clase.getText().toString(), hora_clase.getText().toString(), Float.parseFloat(precio_clase.getText().toString()),
                        Integer.parseInt(listSala.get(numero_sala_clase.getSelectedItemPosition()).get("numero").toString()),
                        Integer.parseInt(listInstructor.get(ci_instructor_clase.getSelectedItemPosition()).get("ci").toString()));
                claseModel.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nombre_clase.setText("");
                        descripcion_clase.setText("");
                        dia_clase.setText("");
                        hora_clase.setText("");
                        precio_clase.setText("");
                        numero_sala_clase.setSelection(0);
                        ci_instructor_clase.setSelection(0);
                        listar();
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.modificar_clase)
    public void editar() {
        listSala = claseView.listSala;
        listInstructor = claseView.listInstructor;
        new Thread(new Runnable() {
            @Override
            public void run() {
                claseModel.insertarDatos(nombre_clase.getText().toString(), descripcion_clase.getText().toString(),
                        dia_clase.getText().toString(), hora_clase.getText().toString(), Float.parseFloat(precio_clase.getText().toString()),
                        Integer.parseInt(listSala.get(numero_sala_clase.getSelectedItemPosition()).get("numero").toString()),
                        Integer.parseInt(listInstructor.get(ci_instructor_clase.getSelectedItemPosition()).get("ci").toString()));
                claseModel.editar(Integer.parseInt(numero_clase.getText().toString()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numero_clase.setText("");
                        nombre_clase.setText("");
                        descripcion_clase.setText("");
                        dia_clase.setText("");
                        hora_clase.setText("");
                        precio_clase.setText("");
                        numero_sala_clase.setSelection(0);
                        ci_instructor_clase.setSelection(0);
                        insertar_clase.setVisibility(View.VISIBLE);
                        modificar_clase.setVisibility(View.GONE);
                        listar();
                    }
                });
            }
        }).start();
    }

    public void listar() {
        claseView.listar();
    }

    public void listarSala() {
        claseView.listarSala();
    }

    public void listarInstructor() {
        claseView.listarInstructor();
    }
}
