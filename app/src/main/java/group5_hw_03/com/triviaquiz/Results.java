package group5_hw_03.com.triviaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    ArrayList<Question> questionArrayList = new ArrayList<>();
    int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);



        ImageView imageViewresult = (ImageView) findViewById(R.id.imageView_resultimage);
        TextView textViewresult = (TextView) findViewById(R.id.textView_resulttext);
        TextView textViewresult_display = (TextView) findViewById(R.id.textView_quizresultdisplay);
        Button buttonquit = (Button) findViewById(R.id.button_quit);
        Button buttontryagain = (Button) findViewById(R.id.button_tryagain);

        if(getIntent().getExtras()!=null)
        {
            Bundle b=getIntent().getExtras();
            questionArrayList = b.getParcelableArrayList("questions");
            result = getIntent().getIntExtra("result_key",0);

        }

        if(result >0 && result <= 10){
            textViewresult_display.setText("Non-Geek");
            textViewresult.setText("There isn't a single geeky bone in your\n" +
                    "body. You prefer to party rather than study,\n" +
                    "and have someone else fix your computer, if\n" +
                    "need be. You're just too cool for this. You\n" +
                    "probably don't even wear glasses!");
            imageViewresult.setImageResource(R.drawable.non_geek);
        }
        if(result >10 && result <= 50){
            textViewresult_display.setText("Semi-Geek");
            textViewresult.setText("Maybe you're just influenced by the trend, or\n" +
                    "maybe you just got it all perfectly balanced.\n" +
                    "You have some geeky traits, but they aren't\n" +
                    "as \"hardcore\" and they don't take over your\n" +
                    "life. You like some geeky things, but aren't\n" +
                    "nearly as obsessive about them as the\n" +
                    "uber-geeks. You actually get to enjoy both\n" +
                    "worlds");
            imageViewresult.setImageResource(R.drawable.semi_geek);
        }
        if(result >50 && result <= 72){
            textViewresult_display.setText("Uber-Geek");
            textViewresult.setText("You are the geek supreme! You are likely to\n" +
                    "be interested in technology, science, gaming\n" +
                    "and geeky media such as Sci-Fi and\n" +
                    "fantasy. All the mean kids that used to laugh\n" +
                    "at you in high school are now begging you\n" +
                    "for a job. Be proud of your geeky nature, for\n" +
                    "geeks shall inherit the Earth!");
            imageViewresult.setImageResource(R.drawable.uber_geek);
        }

        buttonquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Results.this, Splash.class);
                startActivity(inte);
            }
        });

        buttontryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Results.this, Welcome.class);
                startActivity(inte);
            }
        });

    }
}
