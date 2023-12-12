package com.example.gym4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.List;

public class Drawer3Fragment extends Fragment {

    private EditText editTextName, editTextLastName, editTextDNI, editTextDNI2;
    private Button buttonAdd, buttonRead, buttonDelete;
    private TextView textViewPersons;

    private AppDatabase appDatabase;
    private PersonDao personDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer3, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextDNI = view.findViewById(R.id.editTextDNI);
        editTextDNI2 = view.findViewById(R.id.editTextDNI2);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonRead = view.findViewById(R.id.buttonRead);
        buttonDelete = view.findViewById(R.id.buttonEliminar);
        textViewPersons = view.findViewById(R.id.textViewPersons);

        appDatabase = AppDatabase.getDatabase(requireContext());
        personDao = appDatabase.personDao();

        buttonAdd.setOnClickListener(v -> addPerson());
        buttonRead.setOnClickListener(v -> readPersons());
        buttonDelete.setOnClickListener(v -> deletePersonByDNI());

        return view;
    }

    private void addPerson() {
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String dni = editTextDNI.getText().toString();

        if (!name.isEmpty() && !lastName.isEmpty() && !dni.isEmpty()) {
            Person person = new Person();
            person.setFirstName(name);
            person.setLastName(lastName);
            person.setDni(dni);

            new Thread(() -> {
                personDao.insertPerson(person);
                requireActivity().runOnUiThread(() -> {
                    editTextName.setText("");
                    editTextLastName.setText("");
                    editTextDNI.setText("");
                });
            }).start();
        } else {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void readPersons() {
        new Thread(() -> {
            List<Person> persons = personDao.getAllPersons();
            StringBuilder stringBuilder = new StringBuilder();
            for (Person person : persons) {
                stringBuilder.append("Nombre: ").append(person.getFirstName()).append("\n")
                        .append("Apellidos: ").append(person.getLastName()).append("\n")
                        .append("DNI: ").append(person.getDni()).append("\n\n");
            }
            requireActivity().runOnUiThread(() -> textViewPersons.setText(stringBuilder.toString()));
        }).start();
    }

    private void deletePersonByDNI() {
        String dni = editTextDNI2.getText().toString();

        if (!dni.isEmpty()) {
            new Thread(() -> {
                Person person = personDao.getPersonByDNI(dni);
                if (person != null) {
                    personDao.deletePerson(person);
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "Persona eliminada", Toast.LENGTH_SHORT).show();
                        editTextDNI2.setText("");
                    });
                } else {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "Persona no encontrada", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        } else {
            Toast.makeText(requireContext(), "Ingrese un DNI válido", Toast.LENGTH_SHORT).show();
        }
    }
}
