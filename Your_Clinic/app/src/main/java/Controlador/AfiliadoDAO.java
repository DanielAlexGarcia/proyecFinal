package Controlador;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Entidades.Afiliado;

@Dao
public interface AfiliadoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addAfiliado(Afiliado afiliado);

    @Delete
    public void DeletAfiliado(Afiliado afiliado);

    @Query("Select * from afiliado")
    public List<Afiliado> allAfiliados();

}
