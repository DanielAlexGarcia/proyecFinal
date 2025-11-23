package Controlador;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import Entidades.Cita;

@Dao
public interface CitaDAO {

    @Insert
    public void addCita(Cita cit);

    @Query("SELECT * FROM Cita")
    public List<Cita> AllCitas();

    @Query("SELECT * FROM Cita WHERE id =:ID")
    public List<Cita> consultCita(int ID);

    @Query("SELECT * FROM Cita WHERE Doctor =:doc")
    public List<Cita> consultCitaDoc(String doc);

    @Query("SELECT * FROM Cita WHERE Estado =:est")
    public List<Cita> consultCitaEstado(String est);

    @Query("UPDATE Cita SET Fecha =:fech, Hora =:hor WHERE id =:ID")
    public void actualizarCita(String fech, String hor, int ID);

}
