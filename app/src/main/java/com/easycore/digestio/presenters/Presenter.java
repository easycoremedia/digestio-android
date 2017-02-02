package com.easycore.digestio.presenters;

import android.os.Bundle;

import com.easycore.digestio.view.View;


/**
 * Created by printeastwood on 18.07.16.
 */
public interface Presenter<V extends View> {

    void onEnterScope(Bundle savedState);

    void onAttach(V view);

    void onDetach(V view);

    void onExitScope(Bundle outState);

}
