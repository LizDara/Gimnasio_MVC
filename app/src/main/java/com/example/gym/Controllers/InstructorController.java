package com.example.gym.Controllers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.Models.InstructorModel;
import com.example.gym.R;
import com.example.gym.Views.InstructorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InstructorController extends AppCompatActivity {
    private InstructorView instructorView;
    private InstructorModel instructorModel;
    private View view;

    @BindView(R.id.ci_instructor)
    EditText ci_instructor;
    @BindView(R.id.nombre_instructor)
    EditText nombre_instructor;
    @BindView(R.id.apellido_instructor)
    EditText apellido_instructor;
    @BindView(R.id.femenino_instructor)
    RadioButton femenino_instructor;
    @BindView(R.id.masculino_instructor)
    RadioButton masculino_instructor;
    @BindView(R.id.telefono_instructor)
    EditText telefono_instructor;
    @BindView(R.id.direccion_instructor)
    EditText direccion_instructor;
    @BindView(R.id.insertar_instructor)
    Button insertar_instructor;
    @BindView(R.id.modificar_instructor)
    Button modificar_instructor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        getSupportActionBar().setTitle("INSTRUCTOR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        LinearLayout layout = findViewById(R.id.fragment_instructor);
        view = getLayoutInflater().inflate(R.layout.fragment_instructor, null);
        layout.addView(view);
        ButterKnife.bind(this);

        instructorView = new InstructorView(this, view);
        instructorModel = new InstructorModel();

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

    @OnClick(R.id.insertar_instructor)
    public void insertar(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                instructorModel.insertarDatos(Integer.parseInt(ci_instructor.getText().toString()), nombre_instructor.getText().toString(),
                        apellido_instructor.getText().toString(), (femenino_instructor.isChecked() ? "F" : "M"),
                        Integer.parseInt(telefono_instructor.getText().toString()), direccion_instructor.getText().toString());
                instructorModel.insertar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_instructor.setText("");
                        nombre_instructor.setText("");
                        apellido_instructor.setText("");
                        femenino_instructor.setChecked(true);
                        telefono_instructor.setText("");
                        direccion_instructor.setText("");
                        listar();
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.modificar_instructor)
    public void editar(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                instructorModel.insertarDatos(Integer.parseInt(ci_instructor.getText().toString()), nombre_instructor.getText().toString(),
                        apellido_instructor.getText().toString(), (femenino_instructor.isChecked() ? "F" : "M"),
                        Integer.parseInt(telefono_instructor.getText().toString()), direccion_instructor.getText().toString());
                instructorModel.editar();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ci_instructor.setText("");
                        nombre_instructor.setText("");
                        apellido_instructor.setText("");
                        femenino_instructor.setChecked(true);
                        telefono_instructor.setText("");
                        direccion_instructor.setText("");
                        insertar_instructor.setVisibility(View.VISIBLE);
                        modificar_instructor.setVisibility(View.GONE);
                        listar();
                    }
                });
            }
        }).start();
    }

    public void listar() {
        instructorView.listar();
    }
}
