package fractal.test.diegoalves.beerlist.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by diegoalves on 21/04/2017.
 *
 * Class to check if has internet connection
 */
public class Connected {

    private Context context;

    public Connected(Context context) {
        this.context = context;
    }

    public boolean hasConnectionInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }

}
