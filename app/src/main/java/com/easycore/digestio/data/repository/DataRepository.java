package com.easycore.digestio.data.repository;


import com.easycore.digestio.data.model.GetItemsResponse;

import rx.Observable;

public interface DataRepository {

    Observable<GetItemsResponse> getItemsObservable(String languages, String keywords);

}
