package com.example.gym.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.ClienteModel;
import com.example.gym.R;

import java.util.List;
import java.util.Map;

public class ClienteView {
    private ClienteModel clienteModel;

    private View view;
    private Context context;

    EditText ci_cliente;
    EditText nombre_cliente;
    EditText apellido_cliente;
    RadioButton femenino_cliente;
    RadioButton masculino_cliente;
    EditText telefono_cliente;
    EditText correo_cliente;
    Button insertar_cliente;
    Button modificar_cliente;
    RecyclerView clientes;

    public ClienteView(Context context, View view) {
        this.context = context;
        this.view = view;
        clienteModel = new ClienteModel();

        ci_cliente = view.findViewById(R.id.ci_cliente);
        nombre_cliente = view.findViewById(R.id.nombre_cliente);
        apellido_cliente = view.findViewById(R.id.apellido_cliente);
        femenino_cliente = view.findViewById(R.id.femenino_cliente);
        masculino_cliente = view.findViewById(R.id.masculino_cliente);
        telefono_cliente = view.findViewById(R.id.telefono_cliente);
        correo_cliente = view.findViewById(R.id.correo_cliente);
        insertar_cliente = view.findViewById(R.id.insertar_cliente);
        modificar_cliente = view.findViewById(R.id.modificar_cliente);
        clientes = view.findViewById(R.id.clientes);
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = clienteModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ClienteAdapter clienteAdapter = new ClienteAdapter(view.getContext(), list);
                        clientes.setAdapter(clienteAdapter);
                        clientes.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
                });
            }
        }).start();
    }

    public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public ClienteAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.cliente_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_cliente_list.setText(list.get(position).get("nombre").toString());
            holder.telefono_cliente_list.setText(list.get(position).get("telefono").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_cliente_list;
            TextView telefono_cliente_list;
            Button editar_cliente;
            Button eliminar_cliente;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_cliente_list = itemView.findViewById(R.id.nombre_cliente_list);
                telefono_cliente_list = itemView.findViewById(R.id.telefono_cliente_list);
                editar_cliente = itemView.findViewById(R.id.editar_cliente);
                eliminar_cliente = itemView.findViewById(R.id.eliminar_cliente);
                editar_cliente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_cliente.setVisibility(View.GONE);
                        modificar_cliente.setVisibility(View.VISIBLE);

                        ci_cliente.setText(param.get("ci").toString());
                        ci_cliente.setEnabled(false);
                        nombre_cliente.setText(param.get("nombre").toString());
                        apellido_cliente.setText(param.get("apellido").toString());
                        if (param.get("sexo").toString().equals("F"))
                            femenino_cliente.setChecked(true);
                        else
                            masculino_cliente.setChecked(true);
                        telefono_cliente.setText(param.get("telefono").toString());
                        correo_cliente.setText(param.get("correo").toString());
                    }
                });
                eliminar_cliente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                clienteModel.eliminar((int) param.get("ci"));
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
