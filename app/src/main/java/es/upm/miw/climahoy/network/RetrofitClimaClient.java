package es.upm.miw.climahoy.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClimaClient {

    private static Retrofit retrofitWeather;

    private static final String BASE_URL = "https://api.open-meteo.com/";

    public static Retrofit getInstance() {
        if (retrofitWeather == null) {
            retrofitWeather = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWeather;
    }

}
