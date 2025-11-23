package com.example.your_clinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText txtUser, txtPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPasword);

    }
    private boolean validacion(){
        int status = 0;
        boolean si =false;
        String User = txtUser.getText().toString();
        String pasword = txtPassword.getText().toString();
        Log.i("imprimir", "si: "+User+" :si");
        Log.i("imprimir", "si: "+pasword+" :si");
        if(User.equals("") && pasword.equals("")){
            status = 8;
        } else if (User.equals("")) {
            status = 7;
        } else if (pasword.equals("")) {
            status = 6;
        }else {
            status = 1;
        }

        if (status == 8){
            MensajeTOAST("Ingresa primero con tu usuario y contraseña");
        } else if (status ==7) {
            MensajeTOAST("No puedes dejar el usuario vacio");
        } else if (status==6) {
            MensajeTOAST("Noo puedes entrar sin contraseña");
        } else if (status == 1) {
            if (User.equals("Jose") && pasword.equals("2308")){
                return true;
            }else{
                return false;
            }
        }



        return si;
    }

    public void loadPag(View v){

        boolean paso = validacion();

        if(v.getId() == R.id.btnIngresar && paso){
            Intent i = new Intent(this, classActivityInicio.class);
            startActivity(i);
            finish();
        } else if (!paso) {

        } else{
            Log.i("MSJ=>", "Error al intentar acceder, Reinicia la aplicasion");
            Toast.makeText(this, "No se pudo cargar, Reinicia la aplicasion", Toast.LENGTH_SHORT).show();
        }
            
    }
    
    private void MensajeTOAST(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }


}