package db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Controlador.AfiliadoDAO;
import Controlador.CitaDAO;
import Entidades.Afiliado;
import Entidades.Cita;
import kotlin.contracts.Returns;

@Database(entities = {Cita.class, Afiliado.class}, version = 1)
public abstract class ClinicaBD extends RoomDatabase {
    private static ClinicaBD Instance;

    public abstract CitaDAO citDAO();
    public abstract AfiliadoDAO afilDAO();

    public static ClinicaBD getAppDatabase(Context conte){
        if(Instance == null){
            Instance = Room.databaseBuilder(conte.getApplicationContext(),
                    ClinicaBD.class, "BD_Clinica").build();
        }
        return Instance;
    }

    public static void desstroyInstance(){Instance = null;}

}
