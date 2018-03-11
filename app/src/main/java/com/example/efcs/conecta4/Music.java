package com.example.efcs.conecta4;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    private static MediaPlayer player;

    public static void play(Context context, int id) {
        player = player.create(context, id);
        player.setLooping(true);
        player.start();
    }


    public static void stop(Context context){
        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
