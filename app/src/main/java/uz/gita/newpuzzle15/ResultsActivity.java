package uz.gita.newpuzzle15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import uz.gita.newpuzzle15.local.MySharedPref;

public class ResultsActivity extends AppCompatActivity {

    private MySharedPref mySharedPref;
    private AppCompatTextView first;
    private AppCompatTextView second;
    private AppCompatTextView third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        mySharedPref = MySharedPref.getPref(this);

        MediaPlayer mediaPlayerForButton = MediaPlayer.create(this, R.raw.button_sound);

        findViewById(R.id.backResults).setOnClickListener(view -> {
            if (mySharedPref.getSound()){
                mediaPlayerForButton.start();
            }
            onBackPressed();
        });

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);

        if (mySharedPref.getFirst() != Integer.MAX_VALUE){
            first.setText("1. Moves " + mySharedPref.getFirst());
        }
        if (mySharedPref.getSecond() != Integer.MAX_VALUE){
            second.setText("2. Moves " + mySharedPref.getSecond());
        }
        if (mySharedPref.getThird() != Integer.MAX_VALUE){
            third.setText("3. Moves " + mySharedPref.getThird());
        }

        findViewById(R.id.clear).setOnClickListener(view -> {
            first.setText("1. Moves ~");
            second.setText("2. Moves ~");
            third.setText("3. Moves ~");

            mySharedPref.remove();
        });
    }

    /**
     * bnsdf
     * aljfnajskd
     * aojnbdfkjsaf
     * lkdnfkjalsd
     * dfgsd
     *
    * */
}