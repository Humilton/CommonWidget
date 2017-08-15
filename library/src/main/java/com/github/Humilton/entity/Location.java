package com.github.Humilton.entity;

import java.io.Serializable;

/**
 * Created by Hamilton on 2017/8/2.
 */

public class Location implements Serializable {
    double lat;
    double lon;
    String addr;

    public Location(double lat, double lon, String addr) {
        this.lat = lat;
        this.lon = lon;
        this.addr = addr;
    }

    public String toString() {
        return addr + "(" + lat + "," + lon + ")";
    }
}
