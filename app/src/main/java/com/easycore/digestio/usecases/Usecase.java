package com.easycore.digestio.usecases;

import rx.Observer;
import rx.Subscription;

public interface Usecase<T> {

    Subscription execute(Observer<T> observer);
}
