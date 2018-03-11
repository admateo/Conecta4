package com.example.efcs.conecta4;

public class Game {
    static final int MAQUINA = 1;
    static final int JUGADOR = 2;
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int VACIO = 0;
    static final String MAQGANADOR = "1111";
    static final String JUGGANADOR = "2222";
    private int turno = JUGADOR;
    public int tablero[][] = new int[NFILAS][NCOLUMNAS];
    private char estado; //J Jugado, G Ganado, T tablas.

    public Game(int jugador) {
        turno = jugador;
        for (int i = 0; i < NFILAS; i++) {
            for (int k = 0; k < NCOLUMNAS; k++) {
                tablero[i][k] = VACIO;
                this.estado = 'J';
            }
        }
    }

    public boolean compruebaColumnaVacia(int colm) {
        if (tablero[0][colm] != VACIO) return true;
        return false;
    }

    public int filaSelect(int columna) {

        int i = NFILAS-1;
        int fila = -1;

        boolean filaSel = false;
        while(i >= 0 && !filaSel) {
            if (tablero[i][columna] == VACIO) {
                fila = i;
                filaSel = true;
            }else {
                i--;
            }
        }
        return fila+1;
    }

    public boolean tableroCompleto() {
        for (int i=0; i<NFILAS;  i++){
            for (int j=0; j<NCOLUMNAS;  j++){
                if (tablero[i][j] == VACIO){
                    return false;
                }
            }
        }
        return true;
    }

    public void cambiarTurno() {
        if(this.getTurno() == MAQUINA){
            this.setTurno(JUGADOR);
        }else{
            this.setTurno(MAQUINA);
        }
    }

    public boolean colocarFicha(int colm) {
        for (int i = 5; i >= 0; i--) {
            if (tablero[i][colm] == VACIO) {
                tablero[i][colm] = this.getTurno();
                return true;
            }
        }
        return false;
    }

    public boolean jugadaGanadora(int fila, int colm){
       return compruebaColm(colm) || compruebaFila(fila) || compruebaDiagonalDer(fila, colm) || compruebaDiagonalIzq(fila, colm);
    }

    public boolean compruebaColm(int colm){
        String match = "";
        for(int i= 0;  i<NFILAS; i++){
            if(tablero[i][colm] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match = "";
            }
        }
        return false;
    }

    public boolean compruebaDiagonalIzq(int fila, int colm){
        String match = "";
        for (int i = fila, j= colm; i < NFILAS && j < NCOLUMNAS; i++, j++){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }

        for (int i = fila-1, j= colm - 1; j >= 0 && i >=0; i--, j--){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }

        return false;
    }

    public boolean compruebaDiagonalDer(int fila, int colm){
        String match = "";
        for (int i = fila, j= colm; i < NFILAS && j >= 0; i++, j--){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }

        for (int i = fila-1, j= colm + 1; j < NCOLUMNAS && i >=0; i--, j++){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }

        return false;
    }

    public boolean compruebaFila(int fila){
        String match = "";
        for(int i= 0;  i<NCOLUMNAS; i++){
            if(tablero[fila][i] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match = "";
            }
        }
        return false;
    }

    public String tableroToString(){
        String str = "";
        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                str += tablero[i][j];
        return str;
    }

    public void stringToTablero(String str){
        for (int i = 0,count = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                tablero[i][j] = str.charAt(count++) - '0';
            }
        }
    }

    public void setTurno(int jugador) {
        this.turno = jugador;
    }

    public int getTurno() {
        return this.turno;
    }

    public char getEstado() {
        return this.estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

}