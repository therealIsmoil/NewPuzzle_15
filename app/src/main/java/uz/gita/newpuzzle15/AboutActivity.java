package uz.gita.newpuzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import uz.gita.newpuzzle15.local.MySharedPref;

public class AboutActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerForButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        MySharedPref mySharedPref = MySharedPref.getPref(this);

        mediaPlayerForButton = MediaPlayer.create(this, R.raw.button_sound);

        findViewById(R.id.backAbout).setOnClickListener(view -> {
            if (mySharedPref.getSound()){
                mediaPlayerForButton.start();
            }
            onBackPressed();
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