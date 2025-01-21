package com.example.gestordedeberes;

import android.app.AlertDialog;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private TareaAdapter tareaAdapter;
    private ArrayList<Tarea> tareas;
    private SQLiteDatabase bdEscribir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SQLiteDatabase bdLectura = new BaseDatos(this).getReadableDatabase();
        bdEscribir= new BaseDatos(this).getWritableDatabase();

        tareas = new ArrayList<>();
        /*
        tareas.add(new Tarea(Tarea.Asignatura.DI,"Titulo","Descripcion","30/1/2020","10:10"));
        tareas.add(new Tarea(Tarea.Asignatura.DI,"Titulo","Descripcion","30/1/2020","10:10"));
        tareas.add(new Tarea(Tarea.Asignatura.EIE,"Titulo","Descripcion","30/1/2020","10:10"));
        tareas.add(new Tarea(Tarea.Asignatura.PMULT,"Titulo","Descripcion","30/1/2020","10:10"));

         */
        // Crear el adaptador
        tareaAdapter = new TareaAdapter(tareas, (tarea) -> {
            mostrarMenuInferior(tarea);
        });

        getSupportFragmentManager().setFragmentResultListener("tarea", this, (requestKey, result) -> {
            String titulo = result.getString("Titulo");
            String descripcion = result.getString("Descripcion");
            String fecha = result.getString("Fecha");
            String hora = result.getString("Hora");
            Tarea.Asignatura asignatura;
            switch (result.getString("Asignatura")){
                case "AD":
                    asignatura = Tarea.Asignatura.AD;
                break;
                case "PMULT":
                    asignatura = Tarea.Asignatura.PMULT;
                break;
                case "PSP":
                    asignatura = Tarea.Asignatura.PSP;
                break;
                case "DI":
                    asignatura = Tarea.Asignatura.DI;
                break;
                case "SXE":
                    asignatura = Tarea.Asignatura.SXE;
                break;
                case "EIE":
                    asignatura = Tarea.Asignatura.EIE;
                break;
                default:
                    asignatura = Tarea.Asignatura.AD;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("Asignatura",asignatura.toString());
            contentValues.put("Titulo",titulo);
            contentValues.put("Descripcion",descripcion);
            contentValues.put("Fecha",fecha);
            contentValues.put("Hora",hora);
            if(result.getParcelable("tareaEditada")!=null){
                bdEscribir.update("Tareas",contentValues,"id=?",new String[]{result.getInt("Id")+""});
            }else{
                bdEscribir.insert("tareas",null,contentValues);
            }

            Tarea nuevaTarea = new Tarea(asignatura,titulo, descripcion, fecha, hora);
            tareas.add(nuevaTarea);
            tareas.remove(result.getParcelable("tareaEditada"));
            tareaAdapter.notifyDataSetChanged();
        });


        String consulta2 = "SELECT * FROM tareas";
        Cursor cursor = bdLectura.rawQuery(consulta2,
                null);
        if (cursor.moveToFirst()) {
            do {
                String asignaturaRecuperada = cursor.getString(1);
                Tarea.Asignatura asignatura;

                switch (asignaturaRecuperada){
                    case "AD":
                        asignatura = Tarea.Asignatura.AD;
                        break;
                    case "PMULT":
                        asignatura = Tarea.Asignatura.PMULT;
                        break;
                    case "PSP":
                        asignatura = Tarea.Asignatura.PSP;
                        break;
                    case "DI":
                        asignatura = Tarea.Asignatura.DI;
                        break;
                    case "SXE":
                        asignatura = Tarea.Asignatura.SXE;
                        break;
                    case "EIE":
                        asignatura = Tarea.Asignatura.EIE;
                        break;
                    default:
                        asignatura = Tarea.Asignatura.AD;
                }


                int id = cursor.getInt(0);
                String Titulo =cursor.getString(2);
                String Descripcion =cursor.getString(3);
                String Fecha =cursor.getString(4);
                String Hora =cursor.getString(5);
                Tarea nuevaTarea = new Tarea(asignatura,Titulo,Descripcion,Fecha,Hora);
                nuevaTarea.setId(id);
                tareas.add(nuevaTarea);
            } while (cursor.moveToNext());
            cursor.close();
        }




        Collections.sort(tareas, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return t1.getAsignatura().compareTo(t2.getAsignatura());
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Nueva Tarea");
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.fragment_tarea,null));



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton f = findViewById(R.id.botonTarea);
        f.setOnClickListener(e->{
            DialogTarea d = new DialogTarea();
            d.show(getSupportFragmentManager(), "DialogTarea");

        });



        // Instanciar el RecyclerView
        RecyclerView rvTareas = findViewById(R.id.listaTareas);

        rvTareas.setLayoutManager(new LinearLayoutManager(this));

        // Asignar el adaptador al RecyclerView
        rvTareas.setAdapter(tareaAdapter);
    }

    private void mostrarMenuInferior(Tarea tarea){
        BottomSheetDialog menuInferior = new BottomSheetDialog(this);
        View menuInferiorView = getLayoutInflater().inflate(R.layout.menu_inferior, null);

        menuInferiorView.findViewById(R.id.mark_completed_option).setOnClickListener(v->{
            tarea.setEstado(true);
            tareaAdapter.notifyDataSetChanged();
            menuInferior.dismiss();
        });

        menuInferiorView.findViewById(R.id.delete_option).setOnClickListener(v->{
            bdEscribir.delete("Tareas","id=?",new String[]{tarea.getId()+""} );
            tareas.remove(tarea);
            tareaAdapter.notifyDataSetChanged();
            menuInferior.dismiss();
        });

        menuInferiorView.findViewById(R.id.edit_option).setOnClickListener(v->{
            DialogTarea editarTarea = new DialogTarea();
            menuInferior.dismiss();
            Bundle datosTarea = new Bundle();
            datosTarea.putParcelable("tarea", tarea);
            editarTarea.setArguments(datosTarea);
            editarTarea.show(getSupportFragmentManager(),"EditarTarea");
        });


        menuInferior.setContentView(menuInferiorView);
        menuInferior.show();
    }

}