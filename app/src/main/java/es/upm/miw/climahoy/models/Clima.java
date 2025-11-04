package es.upm.miw.climahoy.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Clima {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("generationtime_ms")
    @Expose
    private Double generationtimeMs;
    @SerializedName("utc_offset_seconds")
    @Expose
    private Integer utcOffsetSeconds;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("timezone_abbreviation")
    @Expose
    private String timezoneAbbreviation;
    @SerializedName("elevation")
    @Expose
    private Integer elevation;
    @SerializedName("current_weather")
    @Expose
    private ClimaActual climaActual;

    public Clima() {
    }

    public Clima(Double latitude, Double longitude, Double generationtimeMs, Integer utcOffsetSeconds, String timezone, String timezoneAbbreviation, Integer elevation, ClimaActual climaActual) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.generationtimeMs = generationtimeMs;
        this.utcOffsetSeconds = utcOffsetSeconds;
        this.timezone = timezone;
        this.timezoneAbbreviation = timezoneAbbreviation;
        this.elevation = elevation;
        this.climaActual = climaActual;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(Double generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }

    public Integer getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public void setUtcOffsetSeconds(Integer utcOffsetSeconds) {
        this.utcOffsetSeconds = utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public void setTimezoneAbbreviation(String timezoneAbbreviation) {
        this.timezoneAbbreviation = timezoneAbbreviation;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public ClimaActual getClimaActual() {
        return climaActual;
    }

    public void setClimaActual(ClimaActual climaActual) {
        this.climaActual = climaActual;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Clima.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append("generationtimeMs");
        sb.append('=');
        sb.append(((this.generationtimeMs == null)?"<null>":this.generationtimeMs));
        sb.append(',');
        sb.append("utcOffsetSeconds");
        sb.append('=');
        sb.append(((this.utcOffsetSeconds == null)?"<null>":this.utcOffsetSeconds));
        sb.append(',');
        sb.append("timezone");
        sb.append('=');
        sb.append(((this.timezone == null)?"<null>":this.timezone));
        sb.append(',');
        sb.append("timezoneAbbreviation");
        sb.append('=');
        sb.append(((this.timezoneAbbreviation == null)?"<null>":this.timezoneAbbreviation));
        sb.append(',');
        sb.append("elevation");
        sb.append('=');
        sb.append(((this.elevation == null)?"<null>":this.elevation));
        sb.append(',');
        sb.append("currentWeather");
        sb.append('=');
        sb.append(((this.climaActual == null)?"<null>":this.climaActual));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}