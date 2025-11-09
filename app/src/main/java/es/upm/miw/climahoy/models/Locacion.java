package es.upm.miw.climahoy.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Locacion {

    @SerializedName("place_id")
    @Expose
    private Integer placeId;
    @SerializedName("licence")
    @Expose
    private String licence;
    @SerializedName("osm_type")
    @Expose
    private String osmType;
    @SerializedName("osm_id")
    @Expose
    private Long osmId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("place_rank")
    @Expose
    private Integer placeRank;
    @SerializedName("importance")
    @Expose
    private Double importance;
    @SerializedName("addresstype")
    @Expose
    private String addresstype;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("address")
    @Expose
    private LocacionDireccion address;
    @SerializedName("boundingbox")
    @Expose
    private List<String> boundingbox;


    public Locacion() {
    }

    public Locacion(Integer placeId, String licence, String osmType, Long osmId, String lat, String lon, String _class, String type, Integer placeRank, Double importance, String addresstype, String name, String displayName, LocacionDireccion address, List<String> boundingbox) {
        super();
        this.placeId = placeId;
        this.licence = licence;
        this.osmType = osmType;
        this.osmId = osmId;
        this.lat = lat;
        this.lon = lon;
        this._class = _class;
        this.type = type;
        this.placeRank = placeRank;
        this.importance = importance;
        this.addresstype = addresstype;
        this.name = name;
        this.displayName = displayName;
        this.address = address;
        this.boundingbox = boundingbox;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public Long getOsmId() {
        return osmId;
    }

    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlaceRank() {
        return placeRank;
    }

    public void setPlaceRank(Integer placeRank) {
        this.placeRank = placeRank;
    }

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocacionDireccion getAddress() {
        return address;
    }

    public void setAddress(LocacionDireccion address) {
        this.address = address;
    }

    public List<String> getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(List<String> boundingbox) {
        this.boundingbox = boundingbox;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Locacion.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("placeId");
        sb.append('=');
        sb.append(((this.placeId == null)?"<null>":this.placeId));
        sb.append(',');
        sb.append("licence");
        sb.append('=');
        sb.append(((this.licence == null)?"<null>":this.licence));
        sb.append(',');
        sb.append("osmType");
        sb.append('=');
        sb.append(((this.osmType == null)?"<null>":this.osmType));
        sb.append(',');
        sb.append("osmId");
        sb.append('=');
        sb.append(((this.osmId == null)?"<null>":this.osmId));
        sb.append(',');
        sb.append("lat");
        sb.append('=');
        sb.append(((this.lat == null)?"<null>":this.lat));
        sb.append(',');
        sb.append("lon");
        sb.append('=');
        sb.append(((this.lon == null)?"<null>":this.lon));
        sb.append(',');
        sb.append("_class");
        sb.append('=');
        sb.append(((this._class == null)?"<null>":this._class));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("placeRank");
        sb.append('=');
        sb.append(((this.placeRank == null)?"<null>":this.placeRank));
        sb.append(',');
        sb.append("importance");
        sb.append('=');
        sb.append(((this.importance == null)?"<null>":this.importance));
        sb.append(',');
        sb.append("addresstype");
        sb.append('=');
        sb.append(((this.addresstype == null)?"<null>":this.addresstype));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("displayName");
        sb.append('=');
        sb.append(((this.displayName == null)?"<null>":this.displayName));
        sb.append(',');
        sb.append("address");
        sb.append('=');
        sb.append(((this.address == null)?"<null>":this.address));
        sb.append(',');
        sb.append("boundingbox");
        sb.append('=');
        sb.append(((this.boundingbox == null)?"<null>":this.boundingbox));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
