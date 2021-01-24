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
import com.example.gym.Models.InstructorModel;
import com.example.gym.Models.SalaModel;
import com.example.gym.R;

import java.util.List;
import java.util.Map;

public class ClaseView {
    private ClaseModel claseModel;
    private SalaModel salaModel;
    private InstructorModel instructorModel;

    private View view;
    private Context context;

    EditText numero_clase;
    EditText nombre_clase;
    EditText descripcion_clase;
    EditText dia_clase;
    EditText hora_clase;
    EditText precio_clase;
    Spinner numero_sala_clase;
    Spinner ci_instructor_clase;
    Button insertar_clase;
    Button modificar_clase;
    RecyclerView clases;
    public List<Map<String, Object>> listSala;
    public List<Map<String, Object>> listInstructor;

    public ClaseView(Context context, View view) {
        this.context = context;
        this.view = view;
        claseModel = new ClaseModel();
        salaModel = new SalaModel();
        instructorModel = new InstructorModel();

        numero_clase = view.findViewById(R.id.numero_clase);
        nombre_clase = view.findViewById(R.id.nombre_clase);
        descripcion_clase = view.findViewById(R.id.descripcion_clase);
        dia_clase = view.findViewById(R.id.dia_clase);
        hora_clase = view.findViewById(R.id.hora_clase);
        precio_clase = view.findViewById(R.id.precio_clase);
        numero_sala_clase = view.findViewById(R.id.numero_sala_clase);
        ci_instructor_clase = view.findViewById(R.id.ci_instructor_clase);
        insertar_clase = view.findViewById(R.id.insertar_clase);
        modificar_clase = view.findViewById(R.id.modificar_clase);
        clases = view.findViewById(R.id.clases);
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = claseModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ClaseAdapter claseAdapter = new ClaseAdapter(view.getContext(), list);
                        clases.setAdapter(claseAdapter);
                        clases.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
                        numero_sala_clase.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public void listarInstructor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listInstructor = instructorModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] numeros = new String[listInstructor.size()];
                        for (int i = 0; i < numeros.length; i++)
                            numeros[i] = listInstructor.get(i).get("nombre").toString() + " " + listInstructor.get(i).get("apellido");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, numeros);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ci_instructor_clase.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    public class ClaseAdapter extends RecyclerView.Adapter<ClaseAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public ClaseAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.clase_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_clase_list.setText(list.get(position).get("nombre").toString());
            holder.descripcion_clase_list.setText(list.get(position).get("descripcion").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_clase_list;
            TextView descripcion_clase_list;
            Button editar_clase;
            Button eliminar_clase;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_clase_list = itemView.findViewById(R.id.nombre_clase_list);
                descripcion_clase_list = itemView.findViewById(R.id.descripcion_clase_list);
                editar_clase = itemView.findViewById(R.id.editar_clase);
                eliminar_clase = itemView.findViewById(R.id.eliminar_clase);
                editar_clase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_clase.setVisibility(View.GONE);
                        modificar_clase.setVisibility(View.VISIBLE);

                        numero_clase.setText(param.get("numero").toString());
                        nombre_clase.setText(param.get("nombre").toString());
                        descripcion_clase.setText(param.get("descripcion").toString());
                        dia_clase.setText(param.get("dia").toString());
                        hora_clase.setText(param.get("hora").toString());
                        precio_clase.setText(param.get("precio").toString());
                        int position = 0;
                        for (int i = 0; i < listSala.size(); i++) {
                            if (listSala.get(i).get("numero").toString().equals(param.get("numero_sala").toString()))
                                position = i;
                        }
                        numero_sala_clase.setSelection(position);
                        for (int i = 0; i < listInstructor.size(); i++) {
                            if (listInstructor.get(i).get("ci").toString().equals(param.get("ci_instructor").toString()))
                                position = i;
                        }
                        ci_instructor_clase.setSelection(position);
                    }
                });
                eliminar_clase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                claseModel.eliminar((int) param.get("numero"));
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
