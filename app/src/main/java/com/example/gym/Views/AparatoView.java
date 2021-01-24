package com.example.gym.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.AparatoModel;
import com.example.gym.Models.SalaModel;
import com.example.gym.R;

import java.util.List;
import java.util.Map;

public class AparatoView {
    private AparatoModel aparatoModel;
    private SalaModel salaModel;

    private View view;
    private Context context;

    EditText codigo_aparato;
    EditText nombre_aparato;
    EditText descripcion_aparato;
    EditText estado_aparato;
    Spinner numero_sala_aparato;
    Button insertar_aparato;
    Button modificar_aparato;
    RecyclerView aparatos;
    public List<Map<String, Object>> listSala;

    public AparatoView(Context context, View view) {
        this.context = context;
        this.view = view;
        aparatoModel = new AparatoModel();
        salaModel = new SalaModel();

        codigo_aparato = view.findViewById(R.id.codigo_aparato);
        nombre_aparato = view.findViewById(R.id.nombre_aparato);
        descripcion_aparato = view.findViewById(R.id.descripcion_aparato);
        estado_aparato = view.findViewById(R.id.estado_aparato);
        numero_sala_aparato = view.findViewById(R.id.numero_sala_aparato);
        insertar_aparato = view.findViewById(R.id.insertar_aparato);
        modificar_aparato = view.findViewById(R.id.modificar_aparato);
        aparatos = view.findViewById(R.id.aparatos);
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = aparatoModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AparatoAdapter clienteAdapter = new AparatoAdapter(view.getContext(), list);
                        aparatos.setAdapter(clienteAdapter);
                        aparatos.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
                });
            }
        }).start();
    }

    public void listarSala() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listSala = salaModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listSala.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listSala.get(i).get("numero").toString() + " " + listSala.get(i).get("ubicacion");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        numero_sala_aparato.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public class AparatoAdapter extends RecyclerView.Adapter<AparatoAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public AparatoAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.aparato_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_aparato_list.setText(list.get(position).get("nombre").toString());
            holder.descripcion_aparato_list.setText(list.get(position).get("descripcion").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_aparato_list;
            TextView descripcion_aparato_list;
            Button editar_aparato;
            Button eliminar_aparato;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_aparato_list = itemView.findViewById(R.id.nombre_aparato_list);
                descripcion_aparato_list = itemView.findViewById(R.id.descripcion_aparato_list);
                editar_aparato = itemView.findViewById(R.id.editar_aparato);
                eliminar_aparato = itemView.findViewById(R.id.eliminar_aparato);
                editar_aparato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_aparato.setVisibility(View.GONE);
                        modificar_aparato.setVisibility(View.VISIBLE);

                        codigo_aparato.setText(param.get("codigo").toString());
                        codigo_aparato.setEnabled(false);
                        nombre_aparato.setText(param.get("nombre").toString());
                        descripcion_aparato.setText(param.get("descripcion").toString());
                        estado_aparato.setText(param.get("estado").toString());
                        int position = 0;
                        for (int i = 0; i < listSala.size(); i++) {
                            if (listSala.get(i).get("numero").toString().equals(param.get("numero_sala").toString()))
                                position = i;
                        }
                        numero_sala_aparato.setSelection(position);
                    }
                });
                eliminar_aparato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                aparatoModel.eliminar((int) param.get("codigo"));
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
