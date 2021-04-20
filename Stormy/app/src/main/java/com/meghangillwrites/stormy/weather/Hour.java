package com.meghangillwrites.stormy.weather;

import com.meghangillwrites.stormy.R;

public class Hour {

    private long time;
    private String icon;
    private double temperature;
    private double realFeelTemp;
    private int precipitation;
    private String locationLabel;
    int iconID;
    boolean isDaylight;
    private String timeZone;


    public Hour() {

    }

    public Hour(long time,
                         String icon,
                         double temperature,
                         double realFeelTemp,
                         int precipitation,
                         String locationLabel,
                         int iconID,
                         boolean isDaylight,
                         String timeZone
    ) {
        this.time = time;
        this.icon = icon;
        this.temperature = temperature;
        this.realFeelTemp = realFeelTemp;
        this.precipitation = precipitation;
        this.locationLabel = locationLabel;
        this.iconID = iconID;
        this.isDaylight = isDaylight;
        this.timeZone = timeZone;
    }

    public int getIconID() {
        int iconID = R.drawable.snow;
        setIconID(iconID); //this is in progress

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


    public String setIconID(int iconID) {
        this.iconID = iconID;
        return "Still in Progress";
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isDaylight() {
        return isDaylight;
    }

    public void setDaylight(boolean daylight) {
        isDaylight = daylight;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getRealFeelTemp() {
        return realFeelTemp;
    }

    public void setRealFeelTemp(double realFeelTemp) {
        this.realFeelTemp = realFeelTemp;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }
}
