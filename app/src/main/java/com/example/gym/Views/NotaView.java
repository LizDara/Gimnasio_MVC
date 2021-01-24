package com.example.gym.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.ClaseModel;
import com.example.gym.Models.ClienteModel;
import com.example.gym.Models.DetalleModel;
import com.example.gym.Models.NotaModel;
import com.example.gym.R;

import java.util.List;
import java.util.Map;

public class NotaView {
    private NotaModel notaModel;
    private DetalleModel detalleModel;
    private ClienteModel clienteModel;
    private ClaseModel claseModel;

    private View view;
    private Context context;

    EditText numero_nota;
    EditText monto_nota;
    EditText fecha_nota;
    Spinner ci_cliente_nota;
    Spinner numero_clase_detalle_1;
    EditText cantidad_detalle_1;
    EditText precio_detalle_1;
    Spinner numero_clase_detalle_2;
    EditText cantidad_detalle_2;
    EditText precio_detalle_2;
    Spinner numero_clase_detalle_3;
    EditText cantidad_detalle_3;
    EditText precio_detalle_3;
    Spinner numero_clase_detalle_4;
    EditText cantidad_detalle_4;
    EditText precio_detalle_4;
    Spinner numero_clase_detalle_5;
    EditText cantidad_detalle_5;
    EditText precio_detalle_5;
    Button insertar_nota;
    Button modificar_nota;
    RecyclerView notas;
    public List<Map<String, Object>> listCliente;
    public List<Map<String, Object>> listClase;
    public List<Map<String, Object>> listDetalle;

    public NotaView(Context context, View view) {
        this.context = context;
        this.view = view;
        notaModel = new NotaModel();
        detalleModel = new DetalleModel();
        clienteModel = new ClienteModel();
        claseModel = new ClaseModel();

        numero_nota = view.findViewById(R.id.numero_nota);
        monto_nota = view.findViewById(R.id.monto_nota);
        fecha_nota = view.findViewById(R.id.fecha_nota);
        ci_cliente_nota = view.findViewById(R.id.ci_cliente_nota);
        numero_clase_detalle_1 = view.findViewById(R.id.numero_clase_detalle_1);
        numero_clase_detalle_2 = view.findViewById(R.id.numero_clase_detalle_2);
        numero_clase_detalle_3 = view.findViewById(R.id.numero_clase_detalle_3);
        numero_clase_detalle_4 = view.findViewById(R.id.numero_clase_detalle_4);
        numero_clase_detalle_5 = view.findViewById(R.id.numero_clase_detalle_5);
        cantidad_detalle_1 = view.findViewById(R.id.cantidad_detalle_1);
        cantidad_detalle_2 = view.findViewById(R.id.cantidad_detalle_2);
        cantidad_detalle_3 = view.findViewById(R.id.cantidad_detalle_3);
        cantidad_detalle_4 = view.findViewById(R.id.cantidad_detalle_4);
        cantidad_detalle_5 = view.findViewById(R.id.cantidad_detalle_5);
        precio_detalle_1 = view.findViewById(R.id.precio_detalle_1);
        precio_detalle_2 = view.findViewById(R.id.precio_detalle_2);
        precio_detalle_3 = view.findViewById(R.id.precio_detalle_3);
        precio_detalle_4 = view.findViewById(R.id.precio_detalle_4);
        precio_detalle_5 = view.findViewById(R.id.precio_detalle_5);
        insertar_nota = view.findViewById(R.id.insertar_nota);
        modificar_nota = view.findViewById(R.id.modificar_nota);
        notas = view.findViewById(R.id.notas);
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = notaModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NotaAdapter notaAdapter = new NotaAdapter(context, list);
                        notas.setAdapter(notaAdapter);
                        notas.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
            }
        }).start();
    }

    public void listarCliente() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listCliente = clienteModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listCliente.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listCliente.get(i).get("nombre").toString() + " " + listCliente.get(i).get("apellido");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ci_cliente_nota.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void listarClase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listClase = claseModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listClase.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listClase.get(i).get("nombre").toString() + "(" + listClase.get(i).get("precio").toString() + "Bs.)";
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        numero_clase_detalle_1.setAdapter(adapter);
                        numero_clase_detalle_2.setAdapter(adapter);
                        numero_clase_detalle_3.setAdapter(adapter);
                        numero_clase_detalle_4.setAdapter(adapter);
                        numero_clase_detalle_5.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void listarDetalle(int numero) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listDetalle = detalleModel.listar(numero);

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listDetalle.size() >= 1) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(0).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_1.setSelection(position);
                            cantidad_detalle_1.setText(listDetalle.get(0).get("cantidad").toString());
                            precio_detalle_1.setText(listDetalle.get(0).get("precio").toString());
                        }
                        if (listDetalle.size() >= 2) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(1).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_2.setSelection(position);
                            cantidad_detalle_2.setText(listDetalle.get(1).get("cantidad").toString());
                            precio_detalle_2.setText(listDetalle.get(1).get("precio").toString());
                        }
                        if (listDetalle.size() >= 3) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(2).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_3.setSelection(position);
                            cantidad_detalle_3.setText(listDetalle.get(2).get("cantidad").toString());
                            precio_detalle_3.setText(listDetalle.get(2).get("precio").toString());
                        }
                        if (listDetalle.size() >= 4) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(3).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_4.setSelection(position);
                            cantidad_detalle_4.setText(listDetalle.get(3).get("cantidad").toString());
                            precio_detalle_4.setText(listDetalle.get(3).get("precio").toString());
                        }
                        if (listDetalle.size() >= 5) {
                            int position = 0;
                            for (int i = 0; i < listClase.size(); i++) {
                                if (listClase.get(i).get("numero").toString().equals(listDetalle.get(4).get("numero_clase").toString()))
                                    position = i;
                            }
                            numero_clase_detalle_5.setSelection(position);
                            cantidad_detalle_5.setText(listDetalle.get(4).get("cantidad").toString());
                            precio_detalle_5.setText(listDetalle.get(4).get("precio").toString());
                        }
                    }
                });
            }
        }).start();
    }

    public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public NotaAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.nota_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.numero_nota_list.setText(list.get(position).get("numero").toString());
            holder.monto_nota_list.setText(list.get(position).get("monto").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView numero_nota_list;
            TextView monto_nota_list;
            Button editar_nota;
            Button eliminar_nota;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                numero_nota_list = itemView.findViewById(R.id.numero_nota_list);
                monto_nota_list = itemView.findViewById(R.id.monto_nota_list);
                editar_nota = itemView.findViewById(R.id.editar_nota);
                eliminar_nota = itemView.findViewById(R.id.eliminar_nota);
                editar_nota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_nota.setVisibility(View.GONE);
                        modificar_nota.setVisibility(View.VISIBLE);

                        numero_nota.setText(param.get("numero").toString());
                        numero_nota.setEnabled(false);
                        monto_nota.setText(param.get("monto").toString());
                        fecha_nota.setText(param.get("fecha").toString());
                        int position = 0;
                        for (int i = 0; i < listCliente.size(); i++) {
                            if (listCliente.get(i).get("ci").toString().equals(param.get("ci_cliente").toString()))
                                position = i;
                        }
                        ci_cliente_nota.setSelection(position);
                        listarDetalle((int) param.get("numero"));
                    }
                });
                eliminar_nota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                notaModel.eliminar((int) param.get("numero"));
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listar();
                                    }
                                });
                            }
                        }).start();
                    }
                });
            }
        }
    }
}
