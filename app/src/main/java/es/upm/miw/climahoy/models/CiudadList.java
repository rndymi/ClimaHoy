package es.upm.miw.climahoy.models;

import java.util.List;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CiudadList {

    @SerializedName("results")
    @Expose
    private List<Ciudad> results;

    /*@SerializedName("generationtime_ms")
    @Expose
    private Double generationtimeMs;*/

    public List<Ciudad> getResults() {
        return results;
    }

    public void setResults(List<Ciudad> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CiudadList.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("results");
        sb.append('=');
        sb.append(((this.results == null)?"<null>":this.results));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
