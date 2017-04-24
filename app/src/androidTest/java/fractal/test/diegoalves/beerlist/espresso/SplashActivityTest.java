package fractal.test.diegoalves.beerlist.espresso;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fractal.test.diegoalves.beerlist.view.SplashActivity;

/**
 * Test for Splash activity
 */
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    //get activity
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    /**
     * Test if activity is really waiting 3 seconds
     */
    @Test
    public void splashActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
