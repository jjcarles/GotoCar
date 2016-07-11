package com.jocajica.gotocar.savepoi;

import com.jocajica.gotocar.savepoi.events.SavePoiEvent;

/**
 * Created by Joan Carles Jiménez on 09/07/2016.
 */
public interface SavePoiPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void onEventMainThread(SavePoiEvent event);
}
