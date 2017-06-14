package henry.code.retrofitrealmsample.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import henry.code.retrofitrealmsample.model.Response;
import henry.code.retrofitrealmsample.net.service.OpenAirQualityService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by henry.thetswe on 12/6/17.
 */

public class OpenAirQuality {

    private static final String HOST_URL = "https://api.openaq.org/";

    public static Observable<Response> fetchData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        Retrofit tmpRetrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        OpenAirQualityService service = tmpRetrofit.create(OpenAirQualityService.class);
        return service.fetchData();
    }
}
