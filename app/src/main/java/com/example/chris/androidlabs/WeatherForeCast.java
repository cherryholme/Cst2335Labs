package com.example.chris.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class WeatherForeCast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForeCast";

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_fore_cast);

        progressBar = findViewById(R.id.progbar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery run = new ForecastQuery();
        run.execute();

    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String speed = "";
        String min = "";
        String max = "";
        String currentTemp = "";
        String iconName = "";
        Bitmap bitImage;


        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(conn.getInputStream(), null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String name = parser.getName();
                    if (name.equals("temperature")) {

                        min = parser.getAttributeValue(null, "min");
                        publishProgress(25);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(50);
                        currentTemp = parser.getAttributeValue(null, "value");
                        publishProgress(75);

                    } else if (name.equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                    } else if (name.equals("speed")) {
                        speed = parser.getAttributeValue(null, "name");

                    }
                }

                URL url2 = new URL("http://openweathermap.org/img/w/" + iconName + ".png");

                if (fileExistance(iconName + ".png")) {
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(iconName + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap image = BitmapFactory.decodeStream(fis);
                    bitImage = image;
                    publishProgress(100);
                    Log.i(ACTIVITY_NAME, "File: " + "http://openweathermap.org/img/w/" + iconName + ".png" + "was found locally");
                } else {
                    Bitmap image = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                    bitImage = image;
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                    Log.i(ACTIVITY_NAME, "File: " + "http://openweathermap.org/img/w/" + iconName + ".png" + "was downloaded");
                }


            } catch (Exception e) {

            }


            return null;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {


            TextView newMin = findViewById(R.id.minTemp);
            newMin.setText("The minimum tempurate is: " + min);
            TextView newMax = findViewById(R.id.maxTmp);
            newMax.setText("The maximum tempurate is: " + max);
            TextView newCurrent = findViewById(R.id.currentTmp);
            newCurrent.setText("The current tempurate is: " + currentTemp);
            TextView newWind = findViewById(R.id.windSpd);
            newWind.setText("Wind state: " + speed);

            ImageView newImg = findViewById(R.id.weatherImage);
            newImg.setImageBitmap(bitImage);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }


}
