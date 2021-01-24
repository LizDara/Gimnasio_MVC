package com.example.gym.Controllers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.example.gym.Models.DetalleModel;
import com.example.gym.Models.NotaModel;
import com.example.gym.R;
import com.example.gym.Views.NotaView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotaController extends AppCompatActivity {
    private NotaView notaView;
    private NotaModel notaModel;
    private DetalleModel detalleModel;
    private View view;

    @BindView(R.id.numero_nota)
    EditText numero_nota;
    @BindView(R.id.monto_nota)
    EditText monto_nota;
    @BindView(R.id.fecha_nota)
    EditText fecha_nota;
    @BindView(R.id.ci_cliente_nota)
    Spinner ci_cliente_nota;
    @BindView(R.id.numero_clase_detalle_1)
    Spinner numero_clase_detalle_1;
    @BindView(R.id.cantidad_detalle_1)
    EditText cantidad_detalle_1;
    @BindView(R.id.precio_detalle_1)
    EditText precio_detalle_1;
    @BindView(R.id.numero_clase_detalle_2)
    Spinner numero_clase_detalle_2;
    @BindView(R.id.cantidad_detalle_2)
    EditText cantidad_detalle_2;
    @BindView(R.id.precio_detalle_2)
    EditText precio_detalle_2;
    @BindView(R.id.numero_clase_detalle_3)
    Spinner numero_clase_detalle_3;
    @BindView(R.id.cantidad_detalle_3)
    EditText cantidad_detalle_3;
    @BindView(R.id.precio_detalle_3)
    EditText precio_detalle_3;
    @BindView(R.id.numero_clase_detalle_4)
    Spinner numero_clase_detalle_4;
    @BindView(R.id.cantidad_detalle_4)
    EditText cantidad_detalle_4;
    @BindView(R.id.precio_detalle_4)
    EditText precio_detalle_4;
    @BindView(R.id.numero_clase_detalle_5)
    Spinner numero_clase_detalle_5;
    @BindView(R.id.cantidad_detalle_5)
    EditText cantidad_detalle_5;
    @BindView(R.id.precio_detalle_5)
    EditText precio_detalle_5;
    @BindView(R.id.insertar_nota)
    Button insertar_nota;
    @BindView(R.id.modificar_nota)
    Button modificar_nota;
    @BindView(R.id.notas)
    RecyclerView notas;

    private int numero;
    private float monto;
    private List<Map<String, Object>> listCliente;
    private List<Map<String, Object>> listClase;
    private List<Map<String, Object>> listDetalle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        getSupportActionBar().setTitle("NOTA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        LinearLayout layout = findViewById(R.id.fragment_nota);
        view = getLayoutInflater().inflate(R.layout.fragment_nota, null);
        layout.addView(view);
        ButterKnife.bind(this);

        notaView = new NotaView(this, view);
        notaModel = new NotaModel();
        detalleModel = new DetalleModel();

        listar();
        listarCliente();
        listarClase();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertarDatos() {
        listDetalle = new ArrayList<>();
        monto = 0;

        numero = Integer.parseInt(numero_nota.getText().toString());

        if (!cantidad_detalle_1.getText().toString().isEmpty() && !precio_detalle_1.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_1.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_1.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_1.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_2.getText().toString().isEmpty() && !precio_detalle_2.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_2.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_2.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_2.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_3.getText().toString().isEmpty() && !precio_detalle_3.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_3.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_3.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_3.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_4.getText().toString().isEmpty() && !precio_detalle_4.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_4.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_4.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_4.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }
        if (!cantidad_detalle_5.getText().toString().isEmpty() && !precio_detalle_5.getText().toString().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("numero_clase", Integer.parseInt(listClase.get(numero_clase_detalle_5.getSelectedItemPosition()).get("numero").toString()));
            param.put("precio", Float.parseFloat(precio_detalle_5.getText().toString()));
            param.put("cantidad", Integer.parseInt(cantidad_detalle_5.getText().toString()));
            monto += ((float)param.get("precio") * (int)param.get("cantidad"));
            listDetalle.add(param);
        }

        monto_nota.setText(String.valueOf(monto));
    }

    @OnClick(R.id.insertar_nota)
    public void insertar() {
        listCliente = notaView.listCliente;
        listClase = notaView.listClase;
        insertarDatos();
        new Thread(new Runnable() {
            @Override
            public void run() {
                notaModel.insertarDatos(numero, monto, fecha_nota.getText().toString(), Integer.parseInt(listCliente.get(ci_cliente_nota.getSelectedItemPosition()).get("ci").toString()));
                notaModel.insertar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Map<String, Object> param : listDetalle)
                            insertarDetalle((int)param.get("numero_clase"), (float)param.get("precio"), (int)param.get("cantidad"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clean();
                                listar();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.modificar_nota)
    public void editar() {
        listCliente = notaView.listCliente;
        listClase = notaView.listClase;
        insertarDatos();
        new Thread(new Runnable() {
            @Override
            public void run() {
                notaModel.insertarDatos(Integer.parseInt(numero_nota.getText().toString()), monto, fecha_nota.getText().toString(),
                        Integer.parseInt(listCliente.get(ci_cliente_nota.getSelectedItemPosition()).get("ci").toString()));
                notaModel.editar();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Map<String, Object> param : listDetalle)
                            editarDetalle((int)param.get("numero_clase"), (float)param.get("precio"), (int)param.get("cantidad"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clean();
                                insertar_nota.setVisibility(View.VISIBLE);
                                modificar_nota.setVisibility(View.GONE);

                                listar();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public void insertarDetalle(int numero_clase, float precio, int cantidad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                detalleModel.insertar(numero_clase, numero, precio, cantidad);
            }
        }).start();
    }

    public void editarDetalle(int numero_clase, float precio, int cantidad) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                detalleModel.editar(numero_clase, numero, precio, cantidad);
            }
        }).start();
    }

    public void listar() {
        notaView.listar();
    }

    public void listarCliente() {
        notaView.listarCliente();
    }

    public void listarClase() {
        notaView.listarClase();
    }

    public void clean() {
        numero_nota.setText("");
        numero_nota.setEnabled(true);
        monto_nota.setText("");
        fecha_nota.setText("");
        ci_cliente_nota.setSelection(0);
        numero_clase_detalle_1.setSelection(0);
        numero_clase_detalle_2.setSelection(0);
        numero_clase_detalle_3.setSelection(0);
        numero_clase_detalle_4.setSelection(0);
        numero_clase_detalle_5.setSelection(0);
        cantidad_detalle_1.setText("");
        cantidad_detalle_2.setText("");
        cantidad_detalle_3.setText("");
        cantidad_detalle_4.setText("");
        cantidad_detalle_5.setText("");
        precio_detalle_1.setText("");
        precio_detalle_2.setText("");
        precio_detalle_3.setText("");
        precio_detalle_4.setText("");
        precio_detalle_5.setText("");
    }
}
