package es.upm.miw.climahoy.network;

import es.upm.miw.climahoy.models.Clima;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIService {

    @GET("v1/forecast")
    Call<Clima> getCurrentWeather(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current_weather") boolean currentWeather
    );

}
