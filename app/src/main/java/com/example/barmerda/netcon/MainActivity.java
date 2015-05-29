package com.example.barmerda.netcon;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.;

import java.net.URL;
import java.net.HttpURLConnection;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import android.view.View;

public class MainActivity extends Activity {

    EditText Risposta;
    TextView Connessione;
    WebView  RispostaW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Aggiungi Bottone
    public void buttonOnClick(View v) {
        // do something when the button is clicked
        // get reference to the views
        Risposta = (EditText) findViewById(R.id.Risposta);
        Connessione = (TextView) findViewById(R.id.Connessione);
        RispostaW = (WebView) findViewById(R.id.RispostaW);
        Button button = (Button) v;
        button.setText("I'm connecting to Lupus");
        // check if you are connected or not
        if(isConnected()){
            Connessione.setBackgroundColor(0xFF00CC00);
            Connessione.setText("Loading: http://cmsdoc.cern.ch/~spiga/test.html");
        }
        else{
// Metti rosso            Connessione.setBackgroundColor(
            Connessione.setText("You are NOT conncted");
        }

        // show response on the EditText etResponse
        //etResponse.setText(GET("http://hmkcode.com/examples/index.php"));

        // call AsynTask to perform network operation on separate thread
//        http://cmsdoc.cern.ch/~spiga/test.html
//        new HttpAsyncTask().execute("http://hmkcode.com/examples/index.php");
        new HttpAsyncTask().execute("http://cmsdoc.cern.ch/~spiga/test.html");
    }

    public static String GET(String urll){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            //HttpClient httpclient = new DefaultHttpClient();
            //HttpClient httpclient = new HttpClientBuilder.create().build();
            // make GET request to the given URL
            //HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            //inputStream = httpResponse.getEntity().getContent();


            URL url = new URL(urll);
            HttpURLConnection con = (HttpURLConnection) url
                    .openConnection();

            inputStream = con.getInputStream();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            Risposta.setText(result);
        }
    }

}
