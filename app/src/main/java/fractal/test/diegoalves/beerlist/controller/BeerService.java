package fractal.test.diegoalves.beerlist.controller;

import java.util.List;

import fractal.test.diegoalves.beerlist.model.Beer;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface to access API of beers
 *
 * Created by diegoalves on 21/04/2017.
 */

public interface BeerService {

    @GET("beers")
    Call<List<Beer>> beerList();

}
