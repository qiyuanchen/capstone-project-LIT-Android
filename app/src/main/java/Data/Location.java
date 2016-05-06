package Data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by qc on 3/29/2016.
 */
public class Location {

    @DatabaseField(id=true)
    private String locationID;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;
    public Location(String id, double lat, double longi){
        this.locationID=id;
        this.latitude=lat;
        this.longitude=longi;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public  Location(){

    }


}
