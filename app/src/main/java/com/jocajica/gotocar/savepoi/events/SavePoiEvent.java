package com.jocajica.gotocar.savepoi.events;

import android.location.Location;

/**
 * Created by Joan Carles Jiménez on 09/07/2016.
 */
public class SavePoiEvent {
    private int type;
    private String error;
    private Location location;

    public final static int READ_EVENT = 0;
    public final static int DELETE_EVENT = 1;

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
