package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class FetchDropDownData extends AsyncTask<Void, Void, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    public static ArrayList<String> myCountryList = new ArrayList<String>();
    public static ArrayList<String> myProgramList = new ArrayList<String>();

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.0.25/GlobalJobSearch/api/DropDownFilter");

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            try{
                httpUrlConnection.connect();
                InputStream inputStream = httpUrlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }

                JSONArray JA = new JSONArray(data);

                for(int i =0;i < JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    singleParsed = "Country: " + JO.get("Country") + "\n" +
                            "Program: " + JO.get("Program") + "\n";

                    if(JO.get("Country") != JSONObject.NULL){
                        myCountryList.add("" + JO.get("Country"));
                    }
                    if(JO.get("Program") != JSONObject.NULL){
                        myProgramList.add("" + JO.get("Program"));
                    }
                    dataParsed = dataParsed + singleParsed;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpUrlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }
}
