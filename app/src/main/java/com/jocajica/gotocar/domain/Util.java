package com.jocajica.gotocar.domain;

import android.location.Address;
import android.location.Geocoder;
import android.provider.Telephony;

import java.io.IOException;
import java.util.List;

/**
 * Created by jcarles on 12/07/2016.
 */
public class Util {
    private Geocoder geocoder;

    public Util(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public String getFromLocation(double lat, double lng) {
        String result = "";
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            Address address = addresses.get(0);

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                result += address.getAddressLine(i) + ", ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
