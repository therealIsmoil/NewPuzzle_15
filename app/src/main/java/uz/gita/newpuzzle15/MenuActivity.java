package uz.gita.newpuzzle15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import uz.gita.newpuzzle15.local.MySharedPref;

public class MenuActivity extends AppCompatActivity {

    private AppCompatTextView textViewStart;
    private AppCompatTextView textViewAbout;
    private AppCompatTextView textViewResults;
    private MySharedPref mySharedPref;
    private static final int TIME_INTERVAL = 1000;
    private long backPressed;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (TIME_INTERVAL + backPressed > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(), "Press exit again", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mySharedPref = MySharedPref.getPref(this);

        //Birinchi menudagi Start Game buttonni click bo'lishi
        textViewStart = findViewById(R.id.start);
        textViewStart.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        //Birinchi menudagi Results buttonni click bo'lishi
        textViewResults = findViewById(R.id.results);

        textViewResults.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, ResultsActivity.class);
            startActivity(intent);


        });

        //Birinchi menudagi About buttonni click bo'lishi
        textViewAbout = findViewById(R.id.about);

        textViewAbout.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        //Birinchi menudagi Exit buttonni click bo'lishi
        findViewById(R.id.exit).setOnClickListener(view -> {
            finish();
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