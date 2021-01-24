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

import com.example.gym.Models.InstructorModel;
import com.example.gym.R;

import java.util.List;
import java.util.Map;

public class InstructorView {
    private InstructorModel instructorModel;

    private View view;
    private Context context;

    EditText ci_instructor;
    EditText nombre_instructor;
    EditText apellido_instructor;
    RadioButton femenino_instructor;
    RadioButton masculino_instructor;
    EditText telefono_instructor;
    EditText direccion_instructor;
    Button insertar_instructor;
    Button modificar_instructor;
    RecyclerView instructores;

    public InstructorView(Context context, View view) {
        this.context = context;
        this.view = view;
        instructorModel = new InstructorModel();

        ci_instructor = view.findViewById(R.id.ci_instructor);
        nombre_instructor = view.findViewById(R.id.nombre_instructor);
        apellido_instructor = view.findViewById(R.id.apellido_instructor);
        femenino_instructor = view.findViewById(R.id.femenino_instructor);
        masculino_instructor = view.findViewById(R.id.masculino_instructor);
        telefono_instructor = view.findViewById(R.id.telefono_instructor);
        direccion_instructor = view.findViewById(R.id.direccion_instructor);
        insertar_instructor = view.findViewById(R.id.insertar_instructor);
        modificar_instructor = view.findViewById(R.id.modificar_instructor);
        instructores = view.findViewById(R.id.instructores);
    }

    public void listar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> list = instructorModel.listar();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstructorAdapter instructorAdapter = new InstructorAdapter(view.getContext(), list);
                        instructores.setAdapter(instructorAdapter);
                        instructores.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
                });
            }
        }).start();
    }

    public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.MyViewHolder> {

        Context context;
        List<Map<String, Object>> list;

        public InstructorAdapter(Context context, List<Map<String, Object>> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.instructor_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nombre_instructor_list.setText(list.get(position).get("nombre").toString());
            holder.telefono_instructor_list.setText(list.get(position).get("telefono").toString());

            holder.param = list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView nombre_instructor_list;
            TextView telefono_instructor_list;
            Button editar_instructor;
            Button eliminar_instructor;
            Map<String, Object> param;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre_instructor_list = itemView.findViewById(R.id.nombre_instructor_list);
                telefono_instructor_list = itemView.findViewById(R.id.telefono_instructor_list);
                editar_instructor = itemView.findViewById(R.id.editar_instructor);
                eliminar_instructor = itemView.findViewById(R.id.eliminar_instructor);
                editar_instructor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertar_instructor.setVisibility(View.GONE);
                        modificar_instructor.setVisibility(View.VISIBLE);

                        ci_instructor.setText(param.get("ci").toString());
                        ci_instructor.setEnabled(false);
                        nombre_instructor.setText(param.get("nombre").toString());
                        apellido_instructor.setText(param.get("apellido").toString());
                        if (param.get("sexo").toString().equals("F"))
                            femenino_instructor.setChecked(true);
                        else
                            masculino_instructor.setChecked(true);
                        telefono_instructor.setText(param.get("telefono").toString());
                        direccion_instructor.setText(param.get("direccion").toString());
                    }
                });
                eliminar_instructor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                instructorModel.eliminar((int) param.get("ci"));
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
