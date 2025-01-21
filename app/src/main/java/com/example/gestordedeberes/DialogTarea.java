package com.example.gestordedeberes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DialogTarea extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance(); // Obtén la fecha y hora actual
        int año = calendar.get(Calendar.YEAR);     // Año actual
        int mes = calendar.get(Calendar.MONTH);   // Mes actual (0 = enero, 1 = febrero, etc.)
        int dia = calendar.get(Calendar.DAY_OF_MONTH); // Día actual
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY); // Hora actual (formato 24h)
        int minutosActuales = calendar.get(Calendar.MINUTE);    // Minutos actuales


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View miVista = layoutInflater.inflate(R.layout.fragment_tarea, null);

        Button buttoncancelar = miVista.findViewById(R.id.buttonCancelar);
        Button buttonguardar = miVista.findViewById(R.id.buttonGuardar);
        builder.setView(miVista);
        EditText campoFecha = miVista.findViewById(R.id.editTextDate);
        EditText campoHora = miVista.findViewById(R.id.editTextHora);
        campoFecha.setShowSoftInputOnFocus(false);

        Spinner spinner = miVista.findViewById(R.id.spinner);
        Tarea.Asignatura[] asignaturas = Tarea.Asignatura.values();

        List<String> listaAsignaturas = new ArrayList<>();
        for (Tarea.Asignatura asignatura : asignaturas) {
            listaAsignaturas.add(asignatura.name()); // Usar name() para obtener la representación en texto
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_spinner_item,
                listaAsignaturas

        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        if(getArguments()!=null){
            Tarea tareaRecibida = getArguments().getParcelable("tarea");
            campoHora.setText(tareaRecibida.getHora());
            campoFecha.setText(tareaRecibida.getFecha());
            ((EditText)miVista.findViewById(R.id.editTextTitulo)).setText(tareaRecibida.getTitulo());
            ((EditText)miVista.findViewById(R.id.editTextDescripcion)).setText(tareaRecibida.getDescipcion());
            for (int i=0;i<asignaturas.length;i++) {
                if(tareaRecibida.getAsignatura() == asignaturas[i]){
                    spinner.setSelection(i);
                    break;
                }
            }

            año = Integer.parseInt(campoFecha.getText().toString().split("/")[2]);
            mes = (Integer.parseInt(campoFecha.getText().toString().split("/")[1]))-1;
            dia = Integer.parseInt(campoFecha.getText().toString().split("/")[0]);

            horaActual = Integer.parseInt(campoHora.getText().toString().split(":")[0]);
            minutosActuales = Integer.parseInt(campoHora.getText().toString().split(":")[1]);

        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year,month,dayOfMonth)->{
                    String fechaSeleccionada = dayOfMonth+"/"+(month+1)+"/"+year;
                    campoFecha.setText(fechaSeleccionada);
                },
                año,mes,dia
        );

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hora, minutos) -> {
                    campoHora.setText((hora<10?"0"+hora:hora)+":"+(minutos<10?"0"+minutos:minutos));
                },
                horaActual,minutosActuales,true
        );

        campoFecha.setOnClickListener(e->{
            datePickerDialog.show();
        });

        campoHora.setOnClickListener(e->{
            timePickerDialog.show();
        });

        buttoncancelar.setOnClickListener(e->{
            this.dismiss();
        });

        buttonguardar.setOnClickListener(e->{
            Bundle bundleTarea = new Bundle();
            int id=0;
            if(getArguments()!=null){
                Tarea tareaEditada = getArguments().getParcelable("tarea");
                id = tareaEditada.getId();
                bundleTarea.putParcelable("tareaEditada",tareaEditada);
            }
            String titulo, descripcion, fecha, hora, asignatura;
            asignatura = ((Spinner)miVista.findViewById(R.id.spinner)).getSelectedItem().toString();
            titulo = ((EditText)miVista.findViewById(R.id.editTextTitulo)).getText().toString();
            descripcion = ((EditText)miVista.findViewById(R.id.editTextDescripcion)).getText().toString();
            fecha = ((EditText)miVista.findViewById(R.id.editTextDate)).getText().toString();
            hora = ((EditText)miVista.findViewById(R.id.editTextHora)).getText().toString();
            bundleTarea.putString("Asignatura",asignatura);
            bundleTarea.putString("Titulo",titulo);
            bundleTarea.putString("Descripcion",descripcion);
            bundleTarea.putString("Fecha",fecha);
            bundleTarea.putString("Hora",hora);
            bundleTarea.putInt("Id",id);
            getParentFragmentManager().setFragmentResult("tarea",bundleTarea);
            this.dismiss();
        });


        return builder.create();
    }
}
