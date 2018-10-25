package com.nomad.network;

import com.nomad.domain.MovieBean;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("top250")
    Observable<MovieBean> listTop250(@Query("start") int start,
                                     @Query("count") int count);
}
