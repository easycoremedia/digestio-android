package com.easycore.digestio.data.repository.network;

import com.easycore.digestio.data.model.GetItemsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jakub Begera (jakub.begera@gmail.com) on 07.01.17.
 * (C) Copyright 2017
 */
public interface Endpoints {

    @GET("search")
    Observable<GetItemsResponse> getItems(@Query("languages") String languages, @Query("phrases") String keywords);

}
