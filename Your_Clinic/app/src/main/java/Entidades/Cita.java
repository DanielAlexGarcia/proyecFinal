package Entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Cita {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer id = null;

    @NonNull
    @ColumnInfo(name = "Doctor")
    public String doctor;

    @NonNull
    @ColumnInfo(name = "Paciente")
    public String paciente;
    @NonNull
    @ColumnInfo(name = "fecha")
    public String fecha;
    @NonNull
    @ColumnInfo(name = "hora")
    public String hora;
    @NonNull
    @ColumnInfo(name = "motivo")
    public String motivo;
    @NonNull
    @ColumnInfo(name = "estado")
    public String estado;

    public Cita(@NonNull Integer id, @NonNull String doctor, @NonNull String paciente, @NonNull String fecha, @NonNull String hora, @NonNull String motivo, @NonNull String estado){
        this.id = id;
        this.doctor = doctor;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
    }

    @Ignore
    public Cita( @NonNull String doctor, @NonNull String paciente, @NonNull String fecha, @NonNull String hora, @NonNull String motivo, @NonNull String estado){
        this.doctor = doctor;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
    }

    @NonNull
    public int getId() {return id;}

    public void setId(@NonNull int id) { this.id = id;}

    @NonNull
    public String getDoctor() {return doctor;}

    public void setDoctor(@NonNull String doctor) {this.doctor = doctor;}

    @NonNull
    public String getPaciente() {return paciente;}

    public void setPaciente(@NonNull String paciente) {this.paciente = paciente;}

    @NonNull
    public String getFecha() {return fecha;}

    public void setFecha(@NonNull String fecha) {this.fecha = fecha;}

    @NonNull
    public String getHora() {return hora;}

    public void setHora(@NonNull String hora) {
        this.hora = hora;
    }

    @NonNull
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(@NonNull String motivo) {
        this.motivo = motivo;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return "Doctor: "+ this.doctor+"\n" +
                "Paciente: "+this.paciente+"\n" +
                "Fecha: "+this.fecha+"\n" +
                "Hora: "+this.hora+"\n" +
                "Motivo: "+this.motivo+"\n" +
                "Estado: "+this.estado+"\n\n";
    }

    public String seeall(int op){
        if (op == 1){
            return "Doctor: "+ this.doctor+"\n" +
                    "Paciente: "+this.paciente+"\n" +
                    "Fecha: "+this.fecha+"\n" +
                    "Hora: "+this.hora+"\n" +
                    "Motivo: "+this.motivo+"\n" +
                    "Estado: "+this.estado+"\n\n";
        }else{
            return "ID cita: "+this.id+"\n"+
                    "Doctor: "+ this.doctor+"\n" +
                    "Paciente: "+this.paciente+"\n" +
                    "Fecha: "+this.fecha+"\n" +
                    "Hora: "+this.hora+"\n" +
                    "Motivo: "+this.motivo+"\n" +
                    "Estado: "+this.estado+"\n\n";
        }

    }


}
