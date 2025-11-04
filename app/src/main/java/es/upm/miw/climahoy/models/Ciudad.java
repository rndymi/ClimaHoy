package es.upm.miw.climahoy.models;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Ciudad {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("elevation")
    @Expose
    private Integer elevation;
    @SerializedName("feature_code")
    @Expose
    private String featureCode;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("admin1_id")
    @Expose
    private Integer admin1Id;
    @SerializedName("admin2_id")
    @Expose
    private Integer admin2Id;
    @SerializedName("admin3_id")
    @Expose
    private Integer admin3Id;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("postcodes")
    @Expose
    private List<String> postcodes;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("admin1")
    @Expose
    private String admin1;
    @SerializedName("admin2")
    @Expose
    private String admin2;
    @SerializedName("admin3")
    @Expose
    private String admin3;

    public Ciudad() {
    }

    public Ciudad(String name, Double latitude, Double longitude, Integer elevation, String featureCode, String countryCode, Integer admin1Id, Integer admin2Id, Integer admin3Id, String timezone, Integer population, List<String> postcodes, Integer countryId, String country, String admin1, String admin2, String admin3) {
        super();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.featureCode = featureCode;
        this.countryCode = countryCode;
        this.admin1Id = admin1Id;
        this.admin2Id = admin2Id;
        this.admin3Id = admin3Id;
        this.timezone = timezone;
        this.population = population;
        this.postcodes = postcodes;
        this.countryId = countryId;
        this.country = country;
        this.admin1 = admin1;
        this.admin2 = admin2;
        this.admin3 = admin3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getAdmin1Id() {
        return admin1Id;
    }

    public void setAdmin1Id(Integer admin1Id) {
        this.admin1Id = admin1Id;
    }

    public Integer getAdmin2Id() {
        return admin2Id;
    }

    public void setAdmin2Id(Integer admin2Id) {
        this.admin2Id = admin2Id;
    }

    public Integer getAdmin3Id() {
        return admin3Id;
    }

    public void setAdmin3Id(Integer admin3Id) {
        this.admin3Id = admin3Id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public List<String> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(List<String> postcodes) {
        this.postcodes = postcodes;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdmin1() {
        return admin1;
    }

    public void setAdmin1(String admin1) {
        this.admin1 = admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public void setAdmin3(String admin3) {
        this.admin3 = admin3;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Ciudad.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append("elevation");
        sb.append('=');
        sb.append(((this.elevation == null)?"<null>":this.elevation));
        sb.append(',');
        sb.append("featureCode");
        sb.append('=');
        sb.append(((this.featureCode == null)?"<null>":this.featureCode));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null)?"<null>":this.countryCode));
        sb.append(',');
        sb.append("admin1Id");
        sb.append('=');
        sb.append(((this.admin1Id == null)?"<null>":this.admin1Id));
        sb.append(',');
        sb.append("admin2Id");
        sb.append('=');
        sb.append(((this.admin2Id == null)?"<null>":this.admin2Id));
        sb.append(',');
        sb.append("admin3Id");
        sb.append('=');
        sb.append(((this.admin3Id == null)?"<null>":this.admin3Id));
        sb.append(',');
        sb.append("timezone");
        sb.append('=');
        sb.append(((this.timezone == null)?"<null>":this.timezone));
        sb.append(',');
        sb.append("population");
        sb.append('=');
        sb.append(((this.population == null)?"<null>":this.population));
        sb.append(',');
        sb.append("postcodes");
        sb.append('=');
        sb.append(((this.postcodes == null)?"<null>":this.postcodes));
        sb.append(',');
        sb.append("countryId");
        sb.append('=');
        sb.append(((this.countryId == null)?"<null>":this.countryId));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("admin1");
        sb.append('=');
        sb.append(((this.admin1 == null)?"<null>":this.admin1));
        sb.append(',');
        sb.append("admin2");
        sb.append('=');
        sb.append(((this.admin2 == null)?"<null>":this.admin2));
        sb.append(',');
        sb.append("admin3");
        sb.append('=');
        sb.append(((this.admin3 == null)?"<null>":this.admin3));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}