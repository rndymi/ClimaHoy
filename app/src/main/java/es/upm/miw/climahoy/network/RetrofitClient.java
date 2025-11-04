package es.upm.miw.climahoy.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitGeo;

    private static final String BASE_URL_GEO = "https://geocoding-api.open-meteo.com/";

    public static Retrofit getInstance() {
        if (retrofitGeo == null) {
            retrofitGeo = new Retrofit.Builder()
                    .baseUrl(BASE_URL_GEO)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitGeo;
    }

}
