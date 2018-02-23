package models;

/**
 * Defines a GPS point used for Google maps implementation
 */

public class GpsPoint {
    private double _latitude;
    private double _longitude;

    public GpsPoint(){}

    public GpsPoint(double latitude, double longitude){
        this._latitude = latitude;
        this._longitude = longitude;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(double latitude) {
        this._latitude = latitude;
    }


    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(double longitude) {
        this._longitude = longitude;
    }

    public boolean isGpsPointValid(){

        return this._longitude > 0 && this._latitude > 0;

    }
}
