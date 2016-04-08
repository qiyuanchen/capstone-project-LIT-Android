package Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by qc on 3/29/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
    private static final String DATABASE_FILE_NAME = "LIT";
    private static final int    VERSION            = 1;
    private static DatabaseHelper instance;
    private final Class[] tableClasses = new Class[] {Location.class,user.class};
    private DatabaseHelper(Context context)
    {

        super(context, DATABASE_FILE_NAME, null, VERSION);

    }
    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
    {
        try
        {
            for(Class mTableClass : tableClasses)
            {
                TableUtils.createTable(connectionSource, mTableClass);
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {

    }
    public void destroy(){
        try
        {
            for(Class mTableClass : tableClasses)
            {
                TableUtils.clearTable(connectionSource, mTableClass);
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public Dao<Location,String> getLocationDao() throws SQLException{
        return getDao(Location.class);
    }
    public Dao<user,Integer> getUserDao() throws SQLException{
        return getDao(user.class);
    }




}
