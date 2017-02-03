package com.easycore.digestio.usecases.impl;

import com.easycore.digestio.data.model.GetItemsResponse;
import com.easycore.digestio.data.repository.DataRepository;
import com.easycore.digestio.usecases.Usecase;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 03.02.17.
 */
public class GetItemsUsecase implements Usecase<GetItemsResponse> {

    private DataRepository dataRepository;
    private String languages;
    private String keywords;

    public GetItemsUsecase(DataRepository dataRepository, String languages, String keywords) {
        this.dataRepository = dataRepository;
        this.languages = languages;
        this.keywords = keywords;
    }

    @Override
    public Subscription execute(Observer<GetItemsResponse> observer) {
        return dataRepository.getItemsObservable(languages, keywords)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
