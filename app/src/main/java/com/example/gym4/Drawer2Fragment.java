package com.example.gym4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Drawer2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;

    public Drawer2Fragment() {
        // Required empty public constructor
    }

    public static Drawer2Fragment newInstance() {
        return new Drawer2Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer2, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewExercises);
        setupRecyclerView();

        Button btnAgregar = view.findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarEjercicio();
            }
        });

        return view;
    }

    private void setupRecyclerView() {
        exerciseList = generateExerciseData();
        adapter = new ExerciseAdapter(exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void mostrarDialogoAgregarEjercicio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.agregar_ejercicio_dialog, null);
        builder.setView(dialogView);
        final EditText etTitulo = dialogView.findViewById(R.id.etTitulo);
        final EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        setupDialogButtons(builder, etTitulo, etDescripcion);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupDialogButtons(AlertDialog.Builder builder, final EditText etTitulo, final EditText etDescripcion) {
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String titulo = etTitulo.getText().toString().trim();
                String descripcion = etDescripcion.getText().toString().trim();
                if (!titulo.isEmpty() && !descripcion.isEmpty()) {
                    agregarNuevoEjercicio(titulo, descripcion);
                } else {
                    Toast.makeText(getContext(), "Por favor, ingresa título y descripción", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void agregarNuevoEjercicio(String titulo, String descripcion) {
        Exercise nuevoEjercicio = new Exercise(R.drawable.ic_launcher_foreground, titulo, descripcion);
        exerciseList.add(nuevoEjercicio);
        adapter.notifyItemInserted(exerciseList.size() - 1);
    }

    private List<Exercise> generateExerciseData() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise(R.drawable.ejercicio1, "Brupies", "Los burpees son un ejercicio compuesto que combina flexiones, saltos y sentadillas en un movimiento fluido. Son altamente eficientes para trabajar varios grupos musculares al mismo tiempo, incluyendo el pecho, los brazos, las piernas y el núcleo. Los burpees también son excelentes para mejorar la resistencia cardiovascular y la fuerza funcional, ayudando a mejorar la coordinación y la agilidad. Son un ejercicio desafiante pero efectivo para trabajar todo el cuerpo y mejorar la condición física general."));
        exercises.add(new Exercise(R.drawable.ejercicio2, "Patadas de glúteo en cuadrupedia", "Las patadas de glúteo en cuadrupedia son un ejercicio enfocado en fortalecer los músculos de los glúteos y las piernas. Se realizan estando en posición de cuadrupedia, levantando una pierna hacia atrás con la rodilla doblada y extendiendo la pierna hacia arriba, manteniendo el glúteo contraído. Este movimiento no solo trabaja los glúteos, sino que también ayuda a mejorar la estabilidad central y fortalece los músculos estabilizadores alrededor de la cadera. Las patadas de glúteo en cuadrupedia son ideales para desarrollar fuerza y tonificar los músculos de la parte posterior del cuerpo, contribuyendo a una mejor postura y estabilidad durante otras actividades físicas."));
        exercises.add(new Exercise(R.drawable.ejercicio3, "Patada de abductores", "\n" + "La \"Patada de Abductores\" es un ejercicio enfocado en trabajar los músculos abductores, específicamente el grupo de músculos que se encuentran en la parte externa de las caderas. Este ejercicio es comúnmente utilizado en rutinas de entrenamiento de piernas y glúteos"));
        return exercises;
    }
}


