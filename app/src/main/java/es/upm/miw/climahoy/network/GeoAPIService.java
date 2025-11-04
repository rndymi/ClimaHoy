package es.upm.miw.climahoy.network;


import es.upm.miw.climahoy.models.CiudadList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoAPIService {

    @GET("v1/search")
    Call<CiudadList> getCityByName(
            @Query("name") String name,
            @Query("count") int count,
            @Query("language") String language
    );

}
