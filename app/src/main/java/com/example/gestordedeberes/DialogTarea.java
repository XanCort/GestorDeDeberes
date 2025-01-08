package com.example.gestordedeberes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogTarea extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View miVista = layoutInflater.inflate(R.layout.fragment_tarea, null);

        builder.setView(miVista);
        EditText campoFecha = miVista.findViewById(R.id.editTextDate);

        campoFecha.setShowSoftInputOnFocus(false);


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year,month,dayOfMonth)->{
                },
                2024,12,18
        );


        campoFecha.setOnClickListener(e->{
            datePickerDialog.show();
        });

        return builder.create();
    }
}
