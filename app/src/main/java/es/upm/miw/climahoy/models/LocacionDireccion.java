package es.upm.miw.climahoy.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LocacionDireccion {


    @SerializedName("house_number")
    @Expose
    private String houseNumber;
    @SerializedName("road")
    @Expose
    private String road;
    @SerializedName("neighbourhood")
    @Expose
    private String neighbourhood;
    @SerializedName("quarter")
    @Expose
    private String quarter;
    @SerializedName("suburb")
    @Expose
    private String suburb;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("ISO3166-2-lvl4")
    @Expose
    private String iSO31662Lvl4;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_code")
    @Expose
    private String countryCode;


    public LocacionDireccion() {
    }

    public LocacionDireccion(String houseNumber, String road, String neighbourhood, String quarter, String suburb, String city, String state, String iSO31662Lvl4, String postcode, String country, String countryCode) {
        super();
        this.houseNumber = houseNumber;
        this.road = road;
        this.neighbourhood = neighbourhood;
        this.quarter = quarter;
        this.suburb = suburb;
        this.city = city;
        this.state = state;
        this.iSO31662Lvl4 = iSO31662Lvl4;
        this.postcode = postcode;
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getISO31662Lvl4() {
        return iSO31662Lvl4;
    }

    public void setISO31662Lvl4(String iSO31662Lvl4) {
        this.iSO31662Lvl4 = iSO31662Lvl4;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LocacionDireccion.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("houseNumber");
        sb.append('=');
        sb.append(((this.houseNumber == null)?"<null>":this.houseNumber));
        sb.append(',');
        sb.append("road");
        sb.append('=');
        sb.append(((this.road == null)?"<null>":this.road));
        sb.append(',');
        sb.append("neighbourhood");
        sb.append('=');
        sb.append(((this.neighbourhood == null)?"<null>":this.neighbourhood));
        sb.append(',');
        sb.append("quarter");
        sb.append('=');
        sb.append(((this.quarter == null)?"<null>":this.quarter));
        sb.append(',');
        sb.append("suburb");
        sb.append('=');
        sb.append(((this.suburb == null)?"<null>":this.suburb));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(',');
        sb.append("iSO31662Lvl4");
        sb.append('=');
        sb.append(((this.iSO31662Lvl4 == null)?"<null>":this.iSO31662Lvl4));
        sb.append(',');
        sb.append("postcode");
        sb.append('=');
        sb.append(((this.postcode == null)?"<null>":this.postcode));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null)?"<null>":this.countryCode));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
