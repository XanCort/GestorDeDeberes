package com.example.gestordedeberes;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimePickerDialog timerPickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) ->{

                },
                12,0,true
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("PRUEBA","FLIPA LOCO");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("PRUEBA","PERO MEU");
            }
        });
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

            //AlertDialog dialog = builder.create();
            //dialog.show();
        });





    }
}