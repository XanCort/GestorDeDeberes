package com.example.gestordedeberes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Tarea implements Parcelable {
    private Asignatura asignatura;
    private String titulo;
    private String descipcion;
    private String fecha;

    protected Tarea(Parcel in) {
        titulo = in.readString();
        descipcion = in.readString();
        fecha = in.readString();
    }

    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(descipcion);
        parcel.writeString(fecha);
    }

    private enum Asignatura{
        AD,
        PMULT,
        PSP,
        DI,
        SXE,
        EIE
    }

    public Tarea(Asignatura asignatura, String titulo, String descipcion, String fecha) {
        this.asignatura = asignatura;
        this.titulo = titulo;
        this.descipcion = descipcion;
        this.fecha = fecha;
    }

    public Tarea() {
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
