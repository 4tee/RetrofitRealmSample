package henry.code.retrofitrealmsample.net.service;


import henry.code.retrofitrealmsample.model.Response;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface OpenAirQualityService {

    @GET("v1/countries")
    Observable<Response> fetchData();
}
