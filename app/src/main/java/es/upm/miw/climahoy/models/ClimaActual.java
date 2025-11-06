package es.upm.miw.climahoy.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ClimaActual {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("interval")
    @Expose
    private Integer interval;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("windspeed")
    @Expose
    private Double windspeed;
    @SerializedName("winddirection")
    @Expose
    private Integer winddirection;
    @SerializedName("is_day")
    @Expose
    private Integer isDay;
    @SerializedName("weathercode")
    @Expose
    private Integer weathercode;
    @SerializedName("cloudcover")
    @Expose
    private Integer cloudcover;

    public ClimaActual() {
    }

    public ClimaActual(String time, Integer interval, Double temperature, Double windspeed, Integer winddirection, Integer isDay, Integer weathercode, Integer cloudcover) {
        super();
        this.time = time;
        this.interval = interval;
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.winddirection = winddirection;
        this.isDay = isDay;
        this.weathercode = weathercode;
        this.cloudcover = cloudcover;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Double windspeed) {
        this.windspeed = windspeed;
    }

    public Integer getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(Integer winddirection) {
        this.winddirection = winddirection;
    }

    public Integer getIsDay() {
        return isDay;
    }

    public void setIsDay(Integer isDay) {
        this.isDay = isDay;
    }

    public Integer getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(Integer weathercode) {
        this.weathercode = weathercode;
    }

    public Integer getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(Integer cloudcover) {
        this.cloudcover = cloudcover;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ClimaActual.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("interval");
        sb.append('=');
        sb.append(((this.interval == null)?"<null>":this.interval));
        sb.append(',');
        sb.append("temperature");
        sb.append('=');
        sb.append(((this.temperature == null)?"<null>":this.temperature));
        sb.append(',');
        sb.append("windspeed");
        sb.append('=');
        sb.append(((this.windspeed == null)?"<null>":this.windspeed));
        sb.append(',');
        sb.append("winddirection");
        sb.append('=');
        sb.append(((this.winddirection == null)?"<null>":this.winddirection));
        sb.append(',');
        sb.append("isDay");
        sb.append('=');
        sb.append(((this.isDay == null)?"<null>":this.isDay));
        sb.append(',');
        sb.append("weathercode");
        sb.append('=');
        sb.append(((this.weathercode == null)?"<null>":this.weathercode));
        sb.append(',');
        sb.append("cloudcover");
        sb.append('=');
        sb.append(((this.cloudcover == null)?"<null>":this.cloudcover));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}