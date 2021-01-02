package fr.efrei.reagency.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("latest?base=EUR")
    Call<JsonObject> getExchangeCurrency(@Path("EUR") String currency);
}
