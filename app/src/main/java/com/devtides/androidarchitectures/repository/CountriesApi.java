package com.devtides.androidarchitectures.repository;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {
    //Single
    //Maybe
    //Flowable
    //Observable
    @GET("all")
    Single<List<Country>> getCountries();
}
