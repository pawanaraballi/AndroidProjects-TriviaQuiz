package group5_hw_03.com.triviaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;


public class Splash extends AppCompatActivity {

    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button button_StartQuiz = (Button) findViewById(R.id.button_StartQuiz);

        //image.setImageBitmap(BitmapFactory.decodeFile("geek-inside.png"));

        Intent intent = new Intent();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                if (flag == 0) {
                    Intent intent = new Intent(Splash.this, Welcome.class);
                    startActivity(intent);
                    finishscreen();
                }
            }
        };
        Timer t = new Timer();
        t.schedule(task, 8000);

        button_StartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash.this, Welcome.class);
                finishscreen();
                startActivity(intent);
                flag = 1;
            }
        });


    }
    private void finishscreen() {
        this.finish();
    }
}
