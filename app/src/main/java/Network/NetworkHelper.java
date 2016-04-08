package Network;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by qc on 3/29/2016.
 */
public class NetworkHelper {
    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final int    CONNECT_TIMEOUT  = 99999;
    public static final int    READ_TIMEOUT     = 99999;
    public static final String BASE_URL         = "https://www.google.com";
    private static OkClient instance;
    public static RestAdapter makeRequestAdapter(Context context)
    {
        return makeRequestAdapterBuilder(context, new RetrofitErrorHandler(), JSON_DATE_FORMAT)
                .build();
    }
    private static RestAdapter.Builder makeRequestAdapterBuilder(final Context context, ErrorHandler errorHandler, String dateTimeFormat)
    {
        RequestInterceptor requestInterceptor = new RequestInterceptor()
        {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request)
            {
            }
        };

        Gson gson = new GsonBuilder().setDateFormat(dateTimeFormat).create();
        GsonConverter gsonConverter = new GsonConverter(gson);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setRequestInterceptor(requestInterceptor).setConverter(gsonConverter)
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("Boom!"))
                .setEndpoint(BASE_URL);

        if(errorHandler != null)
        {
            builder.setErrorHandler(errorHandler);
        }

        if(instance == null)
        {
            instance = new OkClient(makeTimeoutClient(READ_TIMEOUT, CONNECT_TIMEOUT));
        }
        builder.setClient(instance);

        return builder;
    }

    public static class RetrofitErrorHandler implements ErrorHandler
    {
        @Override
        public Throwable handleError(RetrofitError cause)
        {
            if(cause.getKind() == RetrofitError.Kind.NETWORK)
            {

                return new NetworkErrorException(cause.getCause());

            }

            return cause;
        }

    }

    public static String debugOut(Response response) throws IOException
    {
        return "error";
    }

    private static OkHttpClient makeTimeoutClient(int readTimeout, int connectTimeout)
    {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(readTimeout, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(connectTimeout, TimeUnit.SECONDS);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
        okHttpClient.setCookieHandler(cookieManager);
        //cookieManager.

        return okHttpClient;
    }
}
