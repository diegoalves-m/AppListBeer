package fractal.test.diegoalves.beerlist.apiTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import fractal.test.diegoalves.beerlist.R;
import fractal.test.diegoalves.beerlist.controller.BeerService;
import fractal.test.diegoalves.beerlist.model.Beer;
import fractal.test.diegoalves.beerlist.view.ListBeerActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class for test api access
 *
 * Created by diegoalves on 23/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ListBeerActivityApiTest {

    // get activity
    @Rule
    public ActivityTestRule<ListBeerActivity> rule = new ActivityTestRule<>(ListBeerActivity.class);

    // mock web server
    private MockWebServer webServer;

    private Context context;

    @Before
    public void setUp() throws Exception {
        webServer = new MockWebServer();
        context = InstrumentationRegistry.getTargetContext();
    }

    /**
     * Method for test if api is working correctly
     *
     * @throws IOException throws in out exception
     */
    @Test
    public void testRequest() throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServer.url(context.getString(R.string.base_url)).toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        BeerService beerService = retrofit.create(BeerService.class);

        Call<List<Beer>> callRequest = beerService.beerList();

        Assert.assertTrue(callRequest.execute() != null);

        webServer.shutdown();
    }

    /**
     * Method for test response with incorrect url
     *
     * @throws IOException throws in out exception
     */
    @Test
    public void testRequestWrongUrl() throws IOException {

        String url = context.getString(R.string.base_url).concat("b/");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServer.url(url).toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        BeerService beerService = retrofit.create(BeerService.class);

        Call<List<Beer>> callRequest = beerService.beerList();

        Assert.assertNotEquals(200, callRequest.execute().code());

        webServer.shutdown();
    }

}
