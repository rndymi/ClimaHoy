package es.upm.miw.climahoy.network.NominatimAPI;

import es.upm.miw.climahoy.models.Locacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NominatimAPIService {

    @Headers({
            "User-Agent: ClimaHoyApp/1.0 (contacto: climahoy@ejemplo.com)",
            "Accept-Language: es"
    })
    @GET("reverse")
    Call<Locacion> getReverseNominatim(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("format") String format,
            @Query("addressdetails") int addressdetails,
            @Query("zoom") int zoom,
            @Query("accept-language") String language
    );

}
