package com.piyushbbk.catfetchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView= findViewById(R.id.textView2);
    }
    public void getFact(View view){
        DownloadTask task = new DownloadTask();
        task.execute("https://catfact.ninja/fact");
    }
    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try {
                url= new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current =(char)data;
                    result+=current;
                    data= reader.read();

                }
                return result;
            }catch(Exception e){
                e.printStackTrace();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                String fact= jsonObject.getString("fact");






                    //Log.i("id",jsonPart.getString("id"));
                    //Log.i("main",jsonPart.getString("main"));
                    //Log.i("description",jsonPart.getString("description"));

                if(!fact.equals("")){
                    resultTextView.setText(fact);

                }else{
                    Toast.makeText(getApplicationContext(), "Could not find :(", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find:(", Toast.LENGTH_SHORT).show();
            }


        }
    }
}