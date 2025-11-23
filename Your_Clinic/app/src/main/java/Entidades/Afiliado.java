package Entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Afiliado {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    public Integer id = null;

    @NonNull
    @ColumnInfo(name = "Nombres")
    public String nombres;

    @NonNull
    @ColumnInfo(name = "Primer apellido")
    public String primerAP;

    @NonNull
    @ColumnInfo(name = "Segundo apellido")
    public String segundoAP;

    @NonNull
    @ColumnInfo(name = "Parentezco")
    public String parentezco;

    @NonNull
    @ColumnInfo(name = "Numero de seguro")
    public String numSeg;

    public Afiliado(@NonNull Integer id, @NonNull String nombres, @NonNull String primerAP, @NonNull String segundoAP, @NonNull String parentezco, @NonNull String numSeg) {
        this.id = id;
        this.nombres = nombres;
        this.primerAP = primerAP;
        this.segundoAP = segundoAP;
        this.parentezco = parentezco;
        this.numSeg = numSeg;
    }

    @Ignore
    public Afiliado(@NonNull String nombres, @NonNull String primerAP, @NonNull String segundoAP, @NonNull String parentezco, @NonNull String numSeg) {
        this.nombres = nombres;
        this.primerAP = primerAP;
        this.segundoAP = segundoAP;
        this.parentezco = parentezco;
        this.numSeg = numSeg;
    }

    @NonNull
    public String getNumSeg() {
        return numSeg;
    }

    public void setNumSeg(@NonNull String numSeg) {
        this.numSeg = numSeg;
    }

    @NonNull
    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(@NonNull String parentezco) {
        this.parentezco = parentezco;
    }

    @NonNull
    public String getSegundoAP() {
        return segundoAP;
    }

    public void setSegundoAP(@NonNull String segundoAP) {
        this.segundoAP = segundoAP;
    }

    @NonNull
    public String getPrimerAP() {
        return primerAP;
    }

    public void setPrimerAP(@NonNull String primerAP) {
        this.primerAP = primerAP;
    }

    @NonNull
    public String getNombres() {
        return nombres;
    }

    public void setNombres(@NonNull String nombres) {
        this.nombres = nombres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Afiliado\n" +
                "Nombres: "+this.nombres+"\n" +
                "Primer apellido: "+this.primerAP+"\n" +
                "Segundo apellido: "+this.segundoAP+"\n" +
                "Parentezco: "+this.parentezco+"\n" +
                "Numero de seguro: "+this.numSeg+"\n\n";
    }
}
