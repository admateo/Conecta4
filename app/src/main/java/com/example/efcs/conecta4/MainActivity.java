package com.example.efcs.conecta4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Game juego;
    Boolean musica, maquina;
    int contador = 1;
    private final int ids[][] = {
            {R.id.f0c0Button, R.id.f0c1Button,R.id.f0c2Button,R.id.f0c3Button,R.id.f0c4Button,R.id.f0c5Button, R.id.f0c6Button},
            {R.id.f1c0Button, R.id.f1c1Button,R.id.f1c2Button,R.id.f1c3Button,R.id.f1c4Button,R.id.f1c5Button, R.id.f1c6Button},
            {R.id.f2c0Button, R.id.f2c1Button,R.id.f2c2Button,R.id.f2c3Button,R.id.f2c4Button,R.id.f2c5Button, R.id.f2c6Button},
            {R.id.f3c0Button, R.id.f3c1Button,R.id.f3c2Button,R.id.f3c3Button,R.id.f3c4Button,R.id.f3c5Button, R.id.f3c6Button},
            {R.id.f4c0Button, R.id.f4c1Button,R.id.f4c2Button,R.id.f4c3Button,R.id.f4c4Button,R.id.f4c5Button, R.id.f4c6Button},
            {R.id.f5c0Button, R.id.f5c1Button,R.id.f5c2Button,R.id.f5c3Button,R.id.f5c4Button,R.id.f5c5Button, R.id.f5c6Button}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        juego = new Game(Game.JUGADOR);
        if(getRotation(getApplicationContext()).equals("vertical")){
            setContentView(R.layout.activity_main);
        }else{
            setContentView(R.layout.activity_main);
        }

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        musica = prefs.getBoolean("musica", false);
        maquina = prefs.getBoolean("maquina", false);
    }

    public void onResume(){
        super.onResume();
        if(musica){
            Music.play(this, R.raw.cancion);
        }

    }
    public void onPause(){
        super.onPause();
        Music.stop(this);
    }

    public void onClick(View v){
        int id = v.getId();
        int column = coorJuego(id);

        switch (juego.getEstado()){
            case 'G':
                Toast.makeText(this,"La partida ha terminado",Toast.LENGTH_SHORT);
                break;
            case 'T':
                Toast.makeText(this,"la partida ha quedado en empate",Toast.LENGTH_SHORT);
                break;
            default:
                procesaJugada(column);
        }
    }

    public int coorJuego(int id){
        int columna = 0;
        for (int i = 0; i < Game.NFILAS; i++){
            for (int j = 0; j < Game.NCOLUMNAS; j++){
                if(ids[i][j] == id){
                    columna = j;
                }
            }
        }
        return columna;
    }

    private void procesaJugada(int col){
        if(juego.compruebaColumnaVacia(col)){
            Toast.makeText(this,"Columna completa",Toast.LENGTH_SHORT).show();
        }else {
            juego.colocarFicha(col);
            dibujarFicha(col);
            int fila = juego.filaSelect(col);
            if(juego.jugadaGanadora(fila, col)){
                juego.setEstado('G');
                mensajeGanador("¡Alguien ha ganado!", "Se iniciará una nueva partida");
            }else{
                if(maquina){
                    juego.cambiarTurno();
                    procesaJugadaMaquina((int) (Math.random() * 6));
                } else{
                    juego.cambiarTurno();
                }
            }

            if(juego.tableroCompleto()){
                juego.setEstado('T');
                mensajeGanador("¡Habeis quedado empatados!", "Se iniciará una nueva partida");
                //Toast
            }
        }
    }

    private void procesaJugadaMaquina(int col){

        if(juego.compruebaColumnaVacia(col)){
            Toast.makeText(this,"Columna completa",Toast.LENGTH_SHORT).show();
        }else {
            juego.colocarFicha(col);
            dibujarFicha(col);
            int fila = juego.filaSelect(col);
            if(juego.jugadaGanadora(fila, col)){
                juego.setEstado('G');
                mensajeGanador("¡Alguien ha ganado!", "Se iniciará una nueva partida");
            }else{
                    juego.cambiarTurno();
                }
            }

            if(juego.tableroCompleto()){
                juego.setEstado('T');
                mensajeGanador("¡Habeis quedado empatados!", "Se iniciará una nueva partida");
                //Toast
            }
        }


    private void dibujarFicha(int columna){

        int id = juego.filaSelect(columna);
        ImageButton img = (ImageButton) findViewById(ids[id][columna]);
        if(juego.getTurno() == Game.JUGADOR){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                img.setImageResource(R.drawable.shape_ring_yellow);
            }else{
                img.setImageResource(R.drawable.shape_ring_yellow);
            }

        }else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                img.setImageResource(R.drawable.shape_ring_red);
            }else{
                img.setImageResource(R.drawable.shape_ring_red);
            }
        }
    }


    public void mensajeGanador(String msg1, String msg2){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(msg1);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg2)
                .setCancelable(false) .setPositiveButton("Si",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        AlertDialog alerta = alertDialogBuilder.create();

        alerta.show();

    }

    public String getRotation(Context context){
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation){
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                return "vertical";
            default:
                return "horizontal";
        }
    }


    public void dibujarTablero(){
        for (int i = 0; i < Game.NFILAS; i++) {
            for (int k = 0; k < Game.NCOLUMNAS; k++) {
                ImageButton img = (ImageButton) findViewById(ids[i][k]);
                if(juego.tablero[i][k] == juego.JUGADOR){
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                        img.setImageResource(R.drawable.shape_ring_yellow);
                    }else{
                        img.setImageResource(R.drawable.shape_ring_yellow);
                    }

                }else if(juego.tablero[i][k] == juego.MAQUINA){
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                        img.setImageResource(R.drawable.shape_ring_red);
                    }else{
                        img.setImageResource(R.drawable.shape_ring_red);
                    }
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle outState){
        outState.putString("TABLERO", juego.tableroToString());

        outState.putInt("TURNO",juego.getTurno());
        outState.putChar("ESTADO",juego.getEstado());
        super.onSaveInstanceState(outState);
    }
    public void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        juego.stringToTablero(savedInstance.getString("TABLERO"));
        juego.setEstado(savedInstance.getChar("ESTADO"));
        juego.setTurno(savedInstance.getInt("TURNO"));
        dibujarTablero();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.acercade){
            startActivity(new Intent(this, AcercaDe.class));
        }

        if(id == R.id.configuracion){
            startActivity(new Intent(this, Configuracion.class));

        }

        return true;
    }

}

