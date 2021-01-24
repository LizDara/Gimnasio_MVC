package com.example.gym.Controllers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gym.Models.ClienteModel;
import com.example.gym.R;
import com.example.gym.Views.ClienteView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteController extends AppCompatActivity {
    private ClienteView clienteView;
    private ClienteModel clienteModel;
    private View view;

    @BindView(R.id.ci_cliente)
    EditText ci_cliente;
    @BindView(R.id.nombre_cliente)
    EditText nombre_cliente;
    @BindView(R.id.apellido_cliente)
    EditText apellido_cliente;
    @BindView(R.id.femenino_cliente)
    RadioButton femenino_cliente;
    @BindView(R.id.masculino_cliente)
    RadioButton masculino_cliente;
    @BindView(R.id.telefono_cliente)
    EditText telefono_cliente;
    @BindView(R.id.correo_cliente)
    EditText correo_cliente;
    @BindView(R.id.insertar_cliente)
    Button insertar_cliente;
    @BindView(R.id.modificar_cliente)
    Button modificar_cliente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        getSupportActionBar().setTitle("CLIENTE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        LinearLayout layout = findViewById(R.id.fragment_cliente);
        view = getLayoutInflater().inflate(R.layout.fragment_cliente, null);
        layout.addView(view);
        ButterKnife.bind(this);

        clienteView = new ClienteView(this, view);
        clienteModel = new ClienteModel();

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

    @OnClick(R.id.insertar_cliente)
    public void insertar(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clienteModel.insertarDatos(Integer.parseInt(ci_cliente.getText().toString()), nombre_cliente.getText().toString(),
                        apellido_cliente.getText().toString(), (femenino_cliente.isChecked() ? "F" : "M"), Integer.parseInt(telefono_cliente.getText().toString()),
                        correo_cliente.getText().toString());
                clienteModel.insertar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_cliente.setText("");
                        nombre_cliente.setText("");
                        apellido_cliente.setText("");
                        femenino_cliente.setChecked(true);
                        telefono_cliente.setText("");
                        correo_cliente.setText("");
                        listar();
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.modificar_cliente)
    public void editar(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clienteModel.insertarDatos(Integer.parseInt(ci_cliente.getText().toString()), nombre_cliente.getText().toString(),
                        apellido_cliente.getText().toString(), (femenino_cliente.isChecked() ? "F" : "M"),
                        Integer.parseInt(telefono_cliente.getText().toString()), correo_cliente.getText().toString());
                clienteModel.editar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_cliente.setText("");
                        nombre_cliente.setText("");
                        apellido_cliente.setText("");
                        femenino_cliente.setChecked(true);
                        telefono_cliente.setText("");
                        correo_cliente.setText("");
                        insertar_cliente.setVisibility(View.VISIBLE);
                        modificar_cliente.setVisibility(View.GONE);
                        listar();
                    }
                });
            }
        }).start();
    }

    public void listar() {
        clienteView.listar();
    }
}
