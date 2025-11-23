package Controlador;

import androidx.room.Dao;
import androidx.room.Insert;

import Entidades.Afiliado;

@Dao
public interface AfiliadoDAO {

    @Insert
    public void addAfiliado(Afiliado afiliado);


}
