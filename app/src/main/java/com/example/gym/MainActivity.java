package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.gym.Controllers.AparatoController;
import com.example.gym.Controllers.ClaseController;
import com.example.gym.Controllers.ClienteController;
import com.example.gym.Controllers.InstructorController;
import com.example.gym.Controllers.NotaController;
import com.example.gym.Controllers.SalaController;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("GIMNASIO");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }

    @OnClick(R.id.sala)
    public void onClickSala(View v) {
        Intent intent = new Intent(this, SalaController.class);
        startActivity(intent);
    }

    @OnClick(R.id.cliente)
    public void onClickCliente(View v) {
        Intent intent = new Intent(this, ClienteController.class);
        startActivity(intent);
    }

    @OnClick(R.id.instructor)
    public void onClickInstructor(View v) {
        Intent intent = new Intent(this, InstructorController.class);
        startActivity(intent);
    }

    @OnClick(R.id.aparato)
    public void onClickAparato(View v) {
        Intent intent = new Intent(this, AparatoController.class);
        startActivity(intent);
    }

    @OnClick(R.id.clase)
    public void onClickClase(View v) {
        Intent intent = new Intent(this, ClaseController.class);
        startActivity(intent);
    }

    @OnClick(R.id.nota)
    public void onClickNota(View v) {
        Intent intent = new Intent(this, NotaController.class);
        startActivity(intent);
    }
}
