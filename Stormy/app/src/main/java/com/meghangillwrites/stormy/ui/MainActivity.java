package com.meghangillwrites.stormy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meghangillwrites.stormy.R;
import com.meghangillwrites.stormy.weather.Current;
import com.meghangillwrites.stormy.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Current current; //we'll set this where we have a correct JSON response
    private String IconPhrase;
    private ImageView iconImageView;
    String currentConditionsJsonData;
    String dailyForecastJsonData;
    Current displayWeather;
    //can also have some variables for location to use as parameters for getForecast()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast();

    }

    private void getForecast()//the parameters for get forecast should be some way to access location like GPS, zip code, etc. How do I have that access an API?
     {
        ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,
                R.layout.activity_main);
        /*according to the documentation we need to bind our data to our new data binding variable.
         We don't have that data here, so let's create the variable in onResponse where we make
         a call to getCurrentDetails(). We will be using the binding variable inside an inner
         class so it has to be declared final
        */

        TextView accuweather = findViewById(R.id.accuweatherAttribution);
        accuweather.setMovementMethod(LinkMovementMethod.getInstance()); /*This tells Android to enable
        the links inside this textView object.
        The setMovementMethod takes as a parameter a movement method. the LinkMovementMethod that
        supports clicking on links, so we should get that instance if we click on the link.
        */

        iconImageView = findViewById(R.id.iconImageView);

        String apiKey = "LQ0ElgtewbM1lI6SpD5JLp0QITRkzpc7";

        double latitude = 40.70;

        double longitude = -73.90;

        double locationKey = 329638;

        String dailyForecastURL = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/329638?apikey=LQ0ElgtewbM1lI6SpD5JLp0QITRkzpc7";

        String currentConditionsURL = "http://dataservice.accuweather.com/currentconditions/v1/329638?apikey=LQ0ElgtewbM1lI6SpD5JLp0QITRkzpc7&details=true";

               /* For the time being I have hardcoded the string. I should update the code so that
               a user can enter their location. A location key will have to be generated on Accuweather
               that will later be used for a forecast.

               "http://dataservice.accuweather.com/forecasts/v1/daily/1day/"
                + locationKey + "?apikey=" + apiKey;
                */
        if (isNetworkAvailable()) {
            OkHttpClient dailyForecastClient = new OkHttpClient();


            Request dailyForecastRequest = new Request.Builder()
                    .url(dailyForecastURL)
                    .build();


            Call call = dailyForecastClient.newCall(dailyForecastRequest);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.v(TAG, "Daily Forecast API call did not work");

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response)
                        throws IOException {
                    try {
                        dailyForecastJsonData = response.body().string();

                        Log.v(TAG, dailyForecastJsonData);

                        if (response.isSuccessful()) {

                            current = getDailyDetails(dailyForecastJsonData); /*
                            Next step: try to concatenate currentConditionsJsonData.
                            we are setting the
                            weather details from the JSON data. Also, the responsibility of catching the JSON
                            method is switched to here so we'll catch a second exception*/

                            displayWeather = new Current(
                                    current.getFormattedTime(),
                                    current.getIcon(),
                                    current.getIconPhraseDay(),
                                    current.getIconPhraseNight(),
                                    current.getLocationLabel(),
                                    current.getTemperatureMaximum(),
                                    current.getTemperatureMinimum(),
                                    current.getCurrentTemperature());

                            if (isNetworkAvailable()) {
                                getCurrentTemperature(binding, currentConditionsURL);
                            }

                            binding.setWeather(displayWeather); //this is the binding variable from onCreate
                            //the unOnUiThread method sends data from a background thread to the main thread.
                            // Only the main thread can update UI features

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources().getDrawable(displayWeather.getIconId()); //Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                }

                            });

                        } else {
                            //Provides a Dialog
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception caught: ", e); /*Now our code is more
                        organized because our exceptions are handled in the same place */
                    }

                }
            });
            Log.d(TAG, "Main thread: main UI code is running, hooray!");


        }


     }

    private void getCurrentTemperature(ActivityMainBinding binding, String currentConditionsURL) {
        OkHttpClient currentConditionsClient = new OkHttpClient();


        Request currentForecastRequest = new Request.Builder()
                .url(currentConditionsURL)
                .build();


        Call call = currentConditionsClient.newCall(currentForecastRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.v(TAG, "Current Conditions Forecast API call did not work");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                try {
                    String currentConditionsJsonData = response.body().string();

                    Log.v(TAG, currentConditionsJsonData);

                    if (response.isSuccessful()) {

                        current =getCurrentDetails(currentConditionsJsonData);
                        Log.d(TAG, "Crash issue" + current);

                        displayWeather.setCurrentTemperature(current.getCurrentTemperature());
                        Log.d(TAG, "Crash issue" + displayWeather);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.setWeather(displayWeather);
                            }
                        });


                    } else {
                        //Provides a Dialog
                        alertUserAboutError();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IO Exception caught: ", e);
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception caught: ", e);
                }

            }


        });


        Log.d(TAG, "Main thread: main UI code is running, hooray!");
    }


    /* Here we'll use a JSON object, that's a special class that can hold any object represented
        in JSON format and it has a few different methods to access the properties inside the JSON
        data. The JSON class has a constructor that allows us to pass in a string of JSON data to
        create the new JSON object
         */

    /*we add an exception to the method signature
    with 'throws'. We do that because we want the exception handled where the method is called
    (the 'if' block).
    */

    private Current getDailyDetails(String jsonData) throws JSONException {


        JSONObject forecast = new JSONObject(jsonData);
        JSONArray dailyForecasts = forecast.getJSONArray("DailyForecasts");
        JSONObject dailyForecastArrayIndex0 = dailyForecasts.getJSONObject(0);


        /*JSONArray dailyForecasts only has one index position, [0]. Now, let's try and search the key value pairs
        in the new JSON object I've created from the array.
        */

        JSONObject temperature = dailyForecastArrayIndex0.getJSONObject("Temperature");
        //Access nested objects so you can access their properties
        JSONObject temperatureMinimum = temperature.getJSONObject("Minimum");
        Double minimumTemp = temperatureMinimum.getDouble("Value");

        JSONObject temperatureMaximum = temperature.getJSONObject("Maximum");
        Double maximumTemp = temperatureMaximum.getDouble("Value");
        Log.i(TAG, "Able to parse the minimum temp and maximum temp from dailyForecasts[0] array: "
                + minimumTemp
                + "  "
                + maximumTemp
        );
        JSONObject day = dailyForecastArrayIndex0.getJSONObject("Day");
        JSONObject night = dailyForecastArrayIndex0.getJSONObject("Night");
        Current current = new Current();
        current.setLocationLabel("Ridgewood, NY");
        current.setIconPhraseDay(day.getString("IconPhrase").toLowerCase());
        current.setIconPhraseNight(night.getString("IconPhrase").toLowerCase());
        current.setHasPrecipitation(day.getBoolean("HasPrecipitation"));
        current.setTemperatureMinimum(minimumTemp);
        current.setTemperatureMaximum(maximumTemp);
        current.setTime(dailyForecastArrayIndex0.getLong("EpochDate"));


        Log.d(TAG, current.getFormattedTime());

        return current;
    }

    @NotNull
    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONArray forecast = new JSONArray(jsonData);
        JSONObject headline = forecast.getJSONObject(0);
        Log.i(TAG, "Parsed the current headline from JSON: " + headline);
        JSONObject currentTemperature = headline.getJSONObject("Temperature");
        JSONObject imperialTemperature = currentTemperature.getJSONObject("Imperial");
        Double currentTemp = imperialTemperature.getDouble("Value");
        Log.i(TAG, "Parsed the current imperial temperature object from JSON: " + currentTemp);
        Current current = new Current();
        current.setCurrentTemperature(currentTemp);
        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        /*
        This requires permission in the manifest:
         <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
         Also NetworkInfo class was deprecated in API level 29. ConnectivityManager.NetworkCallback
         should be used instead. The NetworkInfo object is deprecated, as many of its properties
         can't accurately represent modern network characteristics. Please obtain information about
         networks from the NetworkCapabilities or LinkProperties objects instead.

         May have to play with this later:
         ConnectivityManager.NetworkCallback networkInfo = manager.getActiveNetworkInfo();
         */
        boolean isAvailable = false;
        /* best practice is to declare a boolean false. Check if there is a network && if it's
        connected to the internet
         */
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        else {
            alertUserNetworkIsUnavailable();
            Toast.makeText(this, R.string.network_unavailable_message,
                    Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }

    private void alertUserNetworkIsUnavailable() {
        NetworkUnavailableDialogFragment dialog = new NetworkUnavailableDialogFragment();
        dialog.show(getSupportFragmentManager(), "network_error_dialogue");
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager(), "error_dialogue");
    }

    public void refreshOnClick(View view) {
        getForecast();
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_LONG).show();
    }

    //go to the XML file to make the view clickable (aka give it button functionality) using the onClick attribute.
}

