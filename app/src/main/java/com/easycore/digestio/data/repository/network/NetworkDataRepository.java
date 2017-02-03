package com.easycore.digestio.data.repository.network;


import com.easycore.digestio.data.model.GetItemsResponse;
import com.easycore.digestio.data.repository.DataRepository;

import rx.Observable;

/**
 * Created by Jakub Begera (jakub.begera@gmail.com) on 07.01.17.
 * (C) Copyright 2017
 */
public class NetworkDataRepository implements DataRepository {

    protected Endpoints endpoints;

    public NetworkDataRepository(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public Observable<GetItemsResponse> getItemsObservable(String languages, String keywords) {
        return endpoints.getItems(languages, keywords);
    }
}
