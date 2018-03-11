package com.example.efcs.conecta4;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class Configuracion extends AppCompatActivity {

    EditText nombre;
    String nombreUsuario, nombreRecogido;
    Switch switchMusica, switchMaquina;
    Boolean musica, musicaRecogida, maquina, maquinaRecogida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        nombreRecogido = prefs.getString("nombre", "Nombre");
        musicaRecogida = prefs.getBoolean("musica", false);
        maquinaRecogida = prefs.getBoolean("maquina", false);

        nombre = findViewById(R.id.editText_Nombre);
        switchMusica = findViewById(R.id.musica);
        switchMaquina = findViewById(R.id.maquina);

        nombre.setText(nombreRecogido);
        switchMusica.setChecked(musicaRecogida);
        switchMaquina.setChecked(maquinaRecogida);
    }

    @Override
    public void onPause() {
        super.onPause();

        nombre = findViewById(R.id.editText_Nombre);
        nombreUsuario = nombre.getText().toString();

        switchMusica = findViewById(R.id.musica);
        musica = switchMusica.isChecked();

        switchMaquina = findViewById(R.id.maquina);
        maquina = switchMaquina.isChecked();

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", nombreUsuario);
        editor.putBoolean("musica", musica);
        editor.putBoolean("maquina", maquina);
        editor.apply();
    }
}
