package group5_hw_03.com.triviaquiz;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by pawan on 2/20/2016.
 */
public class Async extends AsyncTask<String, Void, ArrayList<Question>>{

    IData activity;
    public Async(IData acticity)
    {
        this.activity=acticity;
    }

    BufferedReader bufferedReader;
    InputStream in = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected ArrayList<Question> doInBackground(String... params)
    {

        ArrayList<Question> questionArrayList =new ArrayList<>();
        URL url = null;
        try {

            for(int i=0;i<7;i++) {
                url = new URL(params[0] + i);

                HttpURLConnection con= (HttpURLConnection) url.openConnection();

                con.setRequestMethod("GET");
                in=con.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb =new StringBuilder();
                String line="";
                while((line= bufferedReader.readLine())!=null)
                {
                    sb.append(line);
                }
                StringTokenizer stringTokenizer=new StringTokenizer(sb.toString(),";");
                int count=0,position=0;
                Question question=new Question();
                String link=null;
                while(stringTokenizer.hasMoreElements())
                {
                    String token = stringTokenizer.nextToken();
                    int len=token.length();
                    if(len>5)
                    {
                        link = token.substring(len-4,len);
                    }
                    if(count == 0)
                    {
                        question.setId(Integer.parseInt(token));
                        count++;
                    }
                    else if(count ==1)
                    {
                        question.setQuestion(token);
                        count++;
                    }
                    else if(position%2==0)
                    {
                        if(link.equals(".jpg"))
                        {
                            question.setLink(token);
                        }
                        else
                        {
                            question.setOptions(token);
                            position++;
                        }
                    }
                    else if(position%2!=0)
                    {
                        question.setPoints(token);
                        position++;
                    }
                }
                questionArrayList.add(question);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return(questionArrayList);
    }



    protected void onPostExecute(ArrayList<Question> QList)
    {
        Welcome.progressDialog.dismiss();
        activity.getQuestions(QList);
        super.onPostExecute(QList);
    }

    static public interface IData
    {
        public void getQuestions(ArrayList<Question> QList);
    }


}

