package uz.gita.newpuzzle15;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Collections;

import uz.gita.newpuzzle15.local.MySharedPref;

public class MainActivity extends AppCompatActivity {
    private TextView[][] cell = new TextView[4][4];
    private ArrayList<Integer> numbers = new ArrayList<>(15);
    private ConstraintLayout parent;
    private AppCompatImageView shuffleButton;
    private int counter = 0;
    private int x = 3;
    private int y = 3;
    private Drawable[] backgrounds = new Drawable[16]; // boshlang'ich xolatda viewlarni rangini(backgroundini) o'zlashtirib olish uchun
    private AppCompatImageView backButtonForFirstMenu;
    private AppCompatTextView steps;
    private MediaPlayer mediaPlayerForButton;
    private AppCompatImageView soundOnOff;
    private boolean flag = true;
    private Chronometer chronometer;
    private long oldTime;
    private MySharedPref mySharedPref;
    private boolean isPlaying;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPlaying = true; // visibility o'chiqmi
        chronometer = findViewById(R.id.time);
        chronometer.start();
        mySharedPref = MySharedPref.getPref(this);
        steps = findViewById(R.id.moves);
        mediaPlayerForButton = MediaPlayer.create(this, R.raw.button_sound);

        soundOnOff = findViewById(R.id.sound);
        soundOnOff.setOnClickListener(view -> {
            if (mySharedPref.getSound()) {
                mySharedPref.saveSound(false);
                soundOnOff.setImageResource(R.drawable.ic_sound_off);
            } else {
                mySharedPref.saveSound(true);
                soundOnOff.setImageResource(R.drawable.ic_sound_on);
            }
        });
        if (mySharedPref.getSound()) {
            soundOnOff.setImageResource(R.drawable.ic_sound_on);
        } else {
            soundOnOff.setImageResource(R.drawable.ic_sound_off);
        }

        backButtonForFirstMenu = findViewById(R.id.back);
        backButtonForFirstMenu.setOnClickListener(view -> {
            if (mySharedPref.getSound()) {
                mediaPlayerForButton.start();
            }
            onBackPressed();
        });

        AppCompatImageView pauseVisibleInvisible = findViewById(R.id.pause);
        AppCompatImageView playButtonView = findViewById(R.id.playButton);
        FrameLayout pauseButtonVisible = findViewById(R.id.pauseVisible);

        pauseVisibleInvisible.setOnClickListener(view -> {
            YoYo.with(Techniques.BounceIn).duration(1500).playOn(playButtonView);
            pauseButtonVisible.setVisibility(View.VISIBLE);

            oldTime = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            isPlaying = false; // visibility o'chiqmi???

        });

        playButtonView.setOnClickListener(view -> {
            YoYo.with(Techniques.BounceIn).duration(1500).playOn(playButtonView);
            findViewById(R.id.pauseVisible).setVisibility(View.INVISIBLE);

            chronometer.start();
            chronometer.setBase(SystemClock.elapsedRealtime() + oldTime);
            isPlaying = true; // visibility o'chiqmi???
        });

        loadData();
        loadViews();
        loadDataToViews();

        //tabledagi viewlarni backgroundini(rangini) hech qanday o'zgarish bo'lmasdan turib backgrounds massiviga yig'ish
        for (int i = 0; i < parent.getChildCount(); i++) {
            backgrounds[i] = parent.getChildAt(i).getBackground();
        }

        shuffleButton = findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(view -> {
            if (mySharedPref.getSound()) {
                mediaPlayerForButton.start();
            }
            shuffle();
            isSolvable();
            loadDataToViews();
            chronometer.setBase(SystemClock.elapsedRealtime());
            //tabledagi viewlarni backgraundini qayta yuklash;
            for (int i = 0; i < parent.getChildCount(); i++) {
                YoYo.with(Techniques.BounceIn).duration(1000).playOn(cell[i / 4][i % 4]);
                cell[i / 4][i % 4].setBackground(backgrounds[i]);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void loadViews() {
        parent = findViewById(R.id.table);
        for (int i = 0; i < parent.getChildCount(); i++) {
            cell[i / 4][i % 4] = (TextView) parent.getChildAt(i);
            cell[i / 4][i % 4].setTag(i);
            cell[i / 4][i % 4].setOnTouchListener((view, motionEvent) -> {
                int pos = (Integer) view.getTag();
                move(pos / 4, pos % 4);
                return true;
            });
        }
    }

    private void loadData() {
        for (int i = 0; i < 15; i++) {
            numbers.add(i + 1);
        }
        shuffle();
        isSolvable();
    }

    private void isSolvable() {
        int sum = 0;
        shuffle();
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) > numbers.get(j)) {
                    sum++;
                }
            }
        }
        Log.d("TTT", "solvable");
        if (sum % 2 == 1) {
            isSolvable();
        }
    }

    @SuppressLint("SetTextI18n")
    private void shuffle() {
        Collections.shuffle(numbers);
        counter = 0;
        steps.setText("Moves 0");

    }

    private void loadDataToViews() {
        cell[x][y].setVisibility(View.VISIBLE);
        x = 3;
        y = 3;
        cell[x][y].setVisibility(View.INVISIBLE);
        for (int i = 0; i < 15; i++) {
            cell[i / 4][i % 4].setText(String.valueOf(numbers.get(i)));
        }
    }

    @SuppressLint("SetTextI18n")
    private void move(int i, int j) {
        if ((Math.abs(x - i) == 1 && Math.abs(y - j) == 0) || (Math.abs(x - i) == 0 && Math.abs(y - j) == 1)) {
            if (mySharedPref.getSound()) {
                mediaPlayerForButton.start();
            }
            cell[x][y].setVisibility(View.VISIBLE);
            cell[x][y].setBackground(cell[i][j].getBackground());
            cell[x][y].setText(cell[i][j].getText());
            cell[i][j].setVisibility(View.INVISIBLE);

            if (i < x) { //Down
                YoYo.with(Techniques.BounceInDown).duration(300).playOn(cell[x][y]);
            } else if (i > x) { // Up
                YoYo.with(Techniques.BounceInUp).duration(300).playOn(cell[x][y]);
            } else if (j < y) { // Left
                YoYo.with(Techniques.BounceInLeft).duration(300).playOn(cell[x][y]);
            } else if (j > y) { //Right
                YoYo.with(Techniques.BounceInRight).duration(300).playOn(cell[x][y]);
            }
            x = i;
            y = j;

            counter++;
            steps.setText(String.valueOf("Moves " + counter));
            // O'yinni terib bo'lganligini tekshirish. Agar tayyor bo'lsa dialog chiqarish metodini chaqirish.
            dialogAlert(cell, counter);
        }
    }

    // O'yinni terib bo'lganligini tekshirish. Agar tayyor bo'lsa dialog chiqarish metodi.
    private void dialogAlert(TextView[][] textView, int counter) {
        if (isCheckReady(textView)) {
            isPlaying = false;
            /*
                 recordlarni sharedpreferencega yozib borish
        * */
            mySharedPref.saveResult(counter);
            chronometer.stop();

            View view = LayoutInflater.from(this).inflate(R.layout.activity_dialog_record, null);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setView(view).create();
            AppCompatTextView setText = view.findViewById(R.id.dialogSetText);
            setText.setText("You have solved puzzle for " + counter);

            view.findViewById(R.id.homeBtn).setOnClickListener(v -> {
                dialog.dismiss();
                super.onBackPressed();
                if (mySharedPref.getSound()) {
                    mediaPlayerForButton.start();
                }
            });
            view.findViewById(R.id.playBtn).setOnClickListener(v -> {
                isPlaying = true;
                dialog.dismiss();
                isSolvable();
                loadDataToViews();
                if (mySharedPref.getSound()) {
                    mediaPlayerForButton.start();
                }
                chronometer.start();
                chronometer.setBase(SystemClock.elapsedRealtime());
            });
            dialog.getWindow().setDimAmount(0f);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        oldTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
        isPlaying = false;
        View view = LayoutInflater.from(this).inflate(R.layout.activitty_dialog_quit, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(view).create();
        view.findViewById(R.id.noBtn).setOnClickListener(v -> {
            if (mySharedPref.getSound()) {
                mediaPlayerForButton.start();
            }
            dialog.dismiss();
            chronometer.setBase(SystemClock.elapsedRealtime() + oldTime);
            chronometer.start();
            isPlaying = true;
        });
        view.findViewById(R.id.yesBtn).setOnClickListener(v -> {
            super.onBackPressed();
            if (mySharedPref.getSound()) {
                mediaPlayerForButton.start();
            }
        });
        dialog.getWindow().setDimAmount(0f);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    private boolean isCheckReady(TextView[][] textView) {
        for (int i = 0; i < 15; i++) {
            if (textView[i / 4][i % 4].getText() != String.valueOf(i + 1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            chronometer.setBase(SystemClock.elapsedRealtime() + oldTime);
            chronometer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlaying) {
            oldTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        }
        chronometer.stop();
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
