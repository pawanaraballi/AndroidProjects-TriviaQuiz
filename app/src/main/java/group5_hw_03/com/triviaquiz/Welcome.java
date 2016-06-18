package group5_hw_03.com.triviaquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity implements Async.IData {


        Button buttonexit, buttonstart;
        ImageView imageView;
        static ProgressDialog progressDialog;
        ArrayList<Question> questionArrayList =new ArrayList<Question>();
        public static String QUESTIONS="questions";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle("Welcome");


            imageView=(ImageView)findViewById(R.id.imageView);
            buttonexit =(Button)findViewById(R.id.button_Exit);
            buttonstart = (Button)findViewById(R.id.button_StartQuiz);

            if(isConnected()) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading Questions");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Async(this).execute("http://dev.theappsdr.com/apis/spring_2016/hw3/index.php?qid=");
                buttonstart.setEnabled(false);
            }
            else
            {
                Toast.makeText(this,"Enable Internet",Toast.LENGTH_SHORT).show();
            }

            buttonexit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Taken from source StackOverFlow
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                }
            });
        }


        public void getQuestions(ArrayList<Question> questionArrayList) {
            this.questionArrayList = questionArrayList;
            buttonstart.setEnabled(true);
            buttonstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent inte = new Intent(Welcome.this, QuizActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(QUESTIONS, Welcome.this.questionArrayList);
                    inte.putExtras(bundle);
                    finish();
                    startActivity(inte);
                }
            });
        }

        public boolean isConnected() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }
    }
