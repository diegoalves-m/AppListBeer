package fractal.test.diegoalves.beerlist.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fractal.test.diegoalves.beerlist.R;

/**
 * Class initial before show list of beers
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Method responsible for creation of activity
     * @param savedInstanceState So the activity can save status information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // wait 3 seconds before show list of beers
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, ListBeerActivity.class));
                finish();
            }
        }, 3000);

    }
}
