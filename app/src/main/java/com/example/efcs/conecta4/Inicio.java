package com.example.efcs.conecta4;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class Inicio extends AppCompatActivity {

    public static int MILISEGUNDOS_ESPERA = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().hide();
        pasarActividad(MILISEGUNDOS_ESPERA);
    }

    public void pasarActividad(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getApplicationContext(), ElegirJuego.class));
                finish();
            }
        }, milisegundos);
    }
}
