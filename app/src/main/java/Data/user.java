package Data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by qc on 3/29/2016.
 */
public class user {

    @DatabaseField(id=true)
    public int userID;
    public user(int id){
        this.userID=id;
    }
}
