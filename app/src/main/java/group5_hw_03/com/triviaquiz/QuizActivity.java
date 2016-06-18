package group5_hw_03.com.triviaquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizActivity extends AppCompatActivity
{

    int i=0;
    ArrayList<Integer> shuffleOptions;
    RadioGroup radioGroup;
    LinearLayout.LayoutParams lb;
    Question question;
    TextView questionNumber,questiontext;
    ImageView imageView;
    ProgressDialog progressDialog;
    LinearLayout ll;
    Button buttonNext,buttonQuit;
    ArrayList<Question> questionArrayList = new ArrayList<>();

    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionNumber = (TextView)findViewById(R.id.textView_questionNumber);
        questiontext= (TextView) findViewById(R.id.textView_question);
        imageView= (ImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        ll=(LinearLayout) findViewById(R.id.layout);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonQuit = (Button) findViewById(R.id.button_quit);
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        lb = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);


        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            Bundle bundle = intent.getExtras();
            questionArrayList = bundle.getParcelableArrayList(Welcome.QUESTIONS);

            question = questionArrayList.get(i);
            if(question.getLink()!=null)
            {
                new ImageSync().execute(question.getLink());
            }
            else
            {
                imageView.setImageBitmap(null);
            }
            questiontext.setText(question.getQuestion());
            addoptions(question);
        }

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(QuizActivity.this, Welcome.class);
                finish();
                startActivity(inte);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexvalue = radioGroup.getCheckedRadioButtonId();
                if(indexvalue==-1)
                {
                    Toast.makeText(QuizActivity.this, "No Options Selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    score = score + question.getPoints(shuffleOptions.get(indexvalue));
                    radioGroup.clearCheck();
                    radioGroup.removeAllViews();
                    i++;
                    if (i > questionArrayList.size() - 1) {

                        Intent i = new Intent(QuizActivity.this,Results.class);

                        Bundle b =new Bundle();
                        b.putParcelableArrayList("questions", questionArrayList);
                        i.putExtra("result_key",score);
                        i.putExtras(b);
                        finish();
                        startActivity(i);
                    }
                    else {
                        questionNumber.setText("Q" + (i + 1));

                        question = questionArrayList.get(i);

                        questiontext.setText(question.getQuestion());
                        addoptions(question);
                        if (question.getLink() != null) {

                            new ImageSync().execute(question.getLink());


                        } else {
                            imageView.setImageBitmap(null);
                        }
                    }
                }
            }
        });

    }

    private void addoptions(Question q)
    {

        List<String> op=q.getOptions();
        int cnt = op.size();
        shuffleOptions = new ArrayList<>();
        for(int i=0;i<cnt;i++)
        {
            shuffleOptions.add(i);
        }
        Collections.shuffle(shuffleOptions);
        for(int i=0;i<cnt;i++)
        {
            RadioButton rb =new RadioButton(this);
            rb.setId(i);
            rb.setText(q.options.get(shuffleOptions.get(i)));
            rb.setLayoutParams(lb);
            radioGroup.addView(rb);
        }
    }

    private class ImageSync extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params)
        {
            InputStream in;
            Bitmap image =null;
            try {
                URL url =new URL(params[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                in=con.getInputStream();
                image= BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            if(bitmap!=null) {
                try {
                    imageView.setImageBitmap(bitmap);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }
    }

}
