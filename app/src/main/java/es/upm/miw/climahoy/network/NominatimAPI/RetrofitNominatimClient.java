package es.upm.miw.climahoy.network.NominatimAPI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNominatimClient {

    private static Retrofit retrofitNominatim;

    private static final String BASE_URL_NOMINATIM = "https://nominatim.openstreetmap.org/";

    public static Retrofit getInstance() {
        if (retrofitNominatim == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("User-Agent", "ClimaHoyApp/1.0 (contacto: climahoy@ejemplo.com)")
                                .header("Accept-Language", "es")
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    })
                    .build();
            retrofitNominatim = new Retrofit.Builder()
                    .baseUrl(BASE_URL_NOMINATIM)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitNominatim;
    }

}
