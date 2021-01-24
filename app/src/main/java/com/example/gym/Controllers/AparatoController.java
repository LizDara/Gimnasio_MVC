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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.AparatoModel;
import com.example.gym.R;
import com.example.gym.Views.AparatoView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AparatoController extends AppCompatActivity {
    private AparatoView aparatoView;
    private AparatoModel aparatoModel;
    private View view;

    @BindView(R.id.codigo_aparato)
    EditText codigo_aparato;
    @BindView(R.id.nombre_aparato)
    EditText nombre_aparato;
    @BindView(R.id.descripcion_aparato)
    EditText descripcion_aparato;
    @BindView(R.id.estado_aparato)
    EditText estado_aparato;
    @BindView(R.id.numero_sala_aparato)
    Spinner numero_sala_aparato;
    @BindView(R.id.insertar_aparato)
    Button insertar_aparato;
    @BindView(R.id.modificar_aparato)
    Button modificar_aparato;
    @BindView(R.id.aparatos)
    RecyclerView aparatos;
    public List<Map<String, Object>> listSala;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparato);

        getSupportActionBar().setTitle("APARATO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        LinearLayout layout = findViewById(R.id.fragment_aparato);
        View view = getLayoutInflater().inflate(R.layout.fragment_aparato, null);
        layout.addView(view);
        ButterKnife.bind(this);

        aparatoView = new AparatoView(this, view);
        aparatoModel = new AparatoModel();

        listar();
        listarSala();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.insertar_aparato)
    public void insertar() {
        listSala = aparatoView.listSala;
        new Thread(new Runnable() {
            @Override
            public void run() {
                aparatoModel.insertarDatos(Integer.parseInt(codigo_aparato.getText().toString()), nombre_aparato.getText().toString(),
                        descripcion_aparato.getText().toString(), estado_aparato.getText().toString(),
                        Integer.parseInt(listSala.get(numero_sala_aparato.getSelectedItemPosition()).get("numero").toString()));
                aparatoModel.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        codigo_aparato.setText("");
                        nombre_aparato.setText("");
                        descripcion_aparato.setText("");
                        estado_aparato.setText("");
                        numero_sala_aparato.setSelection(0);
                        listar();
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.modificar_aparato)
    public void editar() {
        listSala = aparatoView.listSala;
        new Thread(new Runnable() {
            @Override
            public void run() {
                aparatoModel.insertarDatos(Integer.parseInt(codigo_aparato.getText().toString()), nombre_aparato.getText().toString(),
                        descripcion_aparato.getText().toString(), estado_aparato.getText().toString(),
                        Integer.parseInt(listSala.get(numero_sala_aparato.getSelectedItemPosition()).get("numero").toString()));
                aparatoModel.editar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        codigo_aparato.setText("");
                        codigo_aparato.setEnabled(true);
                        nombre_aparato.setText("");
                        descripcion_aparato.setText("");
                        estado_aparato.setText("");
                        numero_sala_aparato.setSelection(0);
                        insertar_aparato.setVisibility(View.VISIBLE);
                        modificar_aparato.setVisibility(View.GONE);
                        listar();
                    }
                });
            }
        }).start();
    }

    public void listar() {
        aparatoView.listar();
    }

    public void listarSala() {
        aparatoView.listarSala();
    }
}
