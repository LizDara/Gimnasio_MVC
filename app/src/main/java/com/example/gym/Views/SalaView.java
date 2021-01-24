package com.example.gym.Views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Models.SalaModel;
import com.example.gym.R;

import java.util.List;
import java.util.Map;

public class SalaView {
    private SalaModel salaModel;

    private View view;
    private Context context;

    EditText numero_sala;
    EditText dimension_sala;
    EditText ubicacion_sala;
    Button insertar_sala;
    Button modificar_sala;
    RecyclerView salas;

    public SalaView(Context context, View view) {
        this.context = context;
        this.view = view;
        salaModel = new SalaModel();

        numero_sala = view.findViewById(R.id.numero_sala);
        dimension_sala = view.findViewById(R.id.dimension_sala);
        ubicacion_sala = view.findViewById(R.id.ubicacion_sala);
        insertar_sala = view.findViewById(R.id.insertar_sala);
        modificar_sala = view.findViewById(R.id.modificar_sala);
        salas = view.findViewById(R.id.salas);
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = salaModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SalaAdapter salaAdapter = new SalaAdapter(view.getContext(), list);
                        salas.setAdapter(salaAdapter);
                        salas.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
                });
            }
        }).start();
    }

    public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public SalaAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View view = inflater.inflate(R.layout.sala_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.dimension_sala_list.setText(list.get(position).get("dimension").toString());
            holder.ubicacion_sala_list.setText(list.get(position).get("ubicacion").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView ubicacion_sala_list;
            TextView dimension_sala_list;
            Button editar_sala;
            Button eliminar_sala;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ubicacion_sala_list = itemView.findViewById(R.id.ubicacion_sala_list);
                dimension_sala_list = itemView.findViewById(R.id.dimension_sala_list);
                editar_sala = itemView.findViewById(R.id.editar_sala);
                eliminar_sala = itemView.findViewById(R.id.eliminar_sala);
                editar_sala.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_sala.setVisibility(View.GONE);
                        modificar_sala.setVisibility(View.VISIBLE);

                        numero_sala.setText(param.get("numero").toString());
                        dimension_sala.setText(param.get("dimension").toString());
                        ubicacion_sala.setText(param.get("ubicacion").toString());
                    }
                });
                eliminar_sala.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                salaModel.eliminar((int) param.get("numero"));
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
