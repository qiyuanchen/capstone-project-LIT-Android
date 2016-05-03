package Network;

/**
 * Created by Qiyuan on 5/2/2016.
 */
public class results_request {

    public String id;
    public double lat;
    public double lng;
    public results_request(String id, double lat, double lng){
        this.id=id;
        this.lat=lat;
        this.lng=lng;
    }
}
