package com.example.gestordedeberes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TareaViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Tarea>> tareasLiveData = new MutableLiveData<>(new ArrayList<>());
    private SQLiteDatabase bdEscribir, bdLectura;

    public LiveData<ArrayList<Tarea>> getTareas() {
        return tareasLiveData;
    }

    public void cargarTareas(Context context) {
        bdLectura = new BaseDatos(context).getReadableDatabase();
        bdEscribir = new BaseDatos(context).getWritableDatabase();

        ArrayList<Tarea> listaTareas = new ArrayList<>();
        Cursor cursor = bdLectura.rawQuery("SELECT * FROM tareas", null);

        if (cursor.moveToFirst()) {
            do {
                String asignaturaRecuperada = cursor.getString(1);
                Tarea.Asignatura asignatura = obtenerAsignatura(asignaturaRecuperada);

                int id = cursor.getInt(0);
                String titulo = cursor.getString(2);
                String descripcion = cursor.getString(3);
                String fecha = cursor.getString(4);
                String hora = cursor.getString(5);

                Tarea nuevaTarea = new Tarea(asignatura, titulo, descripcion, fecha, hora);
                nuevaTarea.setId(id);
                listaTareas.add(nuevaTarea);
            } while (cursor.moveToNext());
        }
        cursor.close();

        Collections.sort(listaTareas, Comparator.comparing(Tarea::getAsignatura));
        tareasLiveData.setValue(listaTareas);
    }

    public void agregarTarea(Tarea tarea) {
        ArrayList<Tarea> listaActual = tareasLiveData.getValue();
        listaActual.add(tarea);
        tareasLiveData.setValue(listaActual);

        // Guardar en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put("Asignatura", tarea.getAsignatura().toString());
        contentValues.put("Titulo", tarea.getTitulo());
        contentValues.put("Descripcion", tarea.getDescipcion());
        contentValues.put("Fecha", tarea.getFecha());
        contentValues.put("Hora", tarea.getHora());
        bdEscribir.insert("tareas", null, contentValues);
    }

    public void eliminarTarea(Tarea tarea) {
        ArrayList<Tarea> listaActual = tareasLiveData.getValue();
        listaActual.remove(tarea);
        tareasLiveData.setValue(listaActual);

        // Eliminar de la base de datos
        bdEscribir.delete("Tareas", "id=?", new String[]{String.valueOf(tarea.getId())});
    }

    private Tarea.Asignatura obtenerAsignatura(String nombre) {
        switch (nombre) {
            case "AD": return Tarea.Asignatura.AD;
            case "PMULT": return Tarea.Asignatura.PMULT;
            case "PSP": return Tarea.Asignatura.PSP;
            case "DI": return Tarea.Asignatura.DI;
            case "SXE": return Tarea.Asignatura.SXE;
            case "EIE": return Tarea.Asignatura.EIE;
            default: return Tarea.Asignatura.AD;
        }
    }
}