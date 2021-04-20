package com.meghangillwrites.stormy.weather;

import com.meghangillwrites.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Current {
    /*This class (data model) holds the weather data we are getting from JSON, specifically the API for
    Accuweather's 1 Day of Daily Forecasts. In another class I  can also use the API 5 Days of
    Daily Forecasts and 12 Hours of Hourly Forecasts, Text Search returns cities with zip codes,
    lat and longitude
    */
    private double currentTemperature;
    private String locationLabel;
    private String iconPhraseDay;
    private String iconPhraseNight;
    private String icon;
    private long time;
    private double temperatureMinimum;
    private double temperatureMaximum;
    private boolean hasPrecipitation;
    private String currentTime;

    public Current() {
        //used in getCurrentDetails() method
    }

    public Current(String currentTime,
                   String icon,
                   String iconPhraseDay,
                   String iconPhraseNight,
                   String locationLabel,
                   double temperatureMaximum,
                   double temperatureMinimum) {

        this.currentTime = currentTime;
        this.icon = icon;
        this.iconPhraseDay = iconPhraseDay;
        this.iconPhraseNight = iconPhraseNight;
        this.locationLabel = locationLabel;
        this.temperatureMaximum = temperatureMaximum;
        this.temperatureMinimum = temperatureMinimum;

    }

    public Current(String formattedTime, String icon, String iconPhraseDay, String iconPhraseNight, String locationLabel, double temperatureMaximum, double temperatureMinimum, double currentTemperature) {
        this.currentTemperature = currentTemperature;
        this.locationLabel = locationLabel;
        this.iconPhraseDay = iconPhraseDay;
        this.iconPhraseNight = iconPhraseNight;
        this.icon = icon;
        this.time = time;
        this.temperatureMinimum = temperatureMinimum;
        this.temperatureMaximum = temperatureMaximum;
        this.hasPrecipitation = hasPrecipitation;
    }

    public int getIconId(){

        int iconID = R.drawable.snow;
        setIconId();

        switch(icon) {
            case "sunny":
            case "mostly sunny":
            case "hazy sunshine":
                iconID = R.drawable.sunny;
                break;
            case "partly sunny":
            case "intermittent clouds":
                iconID = R.drawable.partly_cloudy;
                break;
            case "mostly cloudy":
            case "cloudy":
            case "dreary":
            case "overcast":
                iconID = R.drawable.cloudy;
                break;
            case "mostly cloudy w/ showers":
            case "partly sunny w/ showers":
            case "mostly cloudy w/ t-storms":
            case "partly sunny w/ t-storms":
            case "showers":
            case "t-storms":
            case "rain":
                iconID = R.drawable.rain;
                break;
            case "snow":
            case "flurries":
            case "mostly cloudy w/ flurries":
            case "partly sunny w/ flurries":
            case "mostly cloudy w/ snow":
            case "ice":
                iconID = R.drawable.snow;
                break;
            case "sleet":
            case "freezing rain":
            case "rain and snow":
                iconID = R.drawable.sleet;
                break;
            case "windy":
                iconID = R.drawable.wind;
                break;
            case "fog":
                iconID = R.drawable.fog;
                break;
            case "partly cloudy":
            case "partly cloudy w/ showers":
            case "hazy moonlight":
                iconID = R.drawable.cloudy_night;
                break;
            case "mostly clear":
            case "clear":
                iconID = R.drawable.clear_night;
                break;
        }
        return iconID;
    }

    private String setIconId() {
        String daytimeOrNightimeIcon = getFormattedTime();
        if (daytimeOrNightimeIcon.contains("AM") && daytimeOrNightimeIcon.charAt(0) >= Integer.parseInt("5")
                && daytimeOrNightimeIcon.charAt(0) >= Integer.parseInt("11"))
        {
            icon = iconPhraseDay;

        } else if (daytimeOrNightimeIcon.contains("PM") && daytimeOrNightimeIcon.charAt(0) >= Integer.parseInt("1")
                && daytimeOrNightimeIcon.charAt(0) >= Integer.parseInt("7")){
            icon = iconPhraseDay;

        } else
            icon = iconPhraseNight;
        return icon;
    }

    public String getIcon() {
        //setDaytimeOrNighttimeIcon();
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getIconPhraseNight() {
        return iconPhraseNight;
    }

    public void setIconPhraseNight(String iconPhraseNight) {
        this.iconPhraseNight = iconPhraseNight;
    }

    public String getIconPhraseDay() {
        return iconPhraseDay;
    }

    public void setIconPhraseDay(String iconPhraseDay) {
        this.iconPhraseDay = iconPhraseDay;
    }

    public long getTime() {
        return time;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat();
        Calendar cal = Calendar.getInstance();
        String formatForTime = "h:mm a";
        formatter.applyPattern(formatForTime);
        String currentTime = formatter.format(cal.getTime());
        /*Timezone EDT (Eastern Daylight Time) to access timezone you have to add the API
         Postal Code Search. See the video "Cleaning up date and time" minute 5:30

         Date dateTime = new Date(time * 1000); Sometimes you have to put the time into milliseconds
        */
        return currentTime;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getTemperatureMinimum() {
        return temperatureMinimum;
    }

    public void setTemperatureMinimum(double temperatureMinimum) {
        this.temperatureMinimum = temperatureMinimum;
    }

    public double getTemperatureMaximum() {
        return temperatureMaximum;
    }

    public void setTemperatureMaximum(double temperatureMaximum) {
        this.temperatureMaximum = temperatureMaximum;
    }



    public boolean isHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setHasPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }


}
