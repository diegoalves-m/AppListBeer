package fractal.test.diegoalves.beerlist.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fractal.test.diegoalves.beerlist.R;
import fractal.test.diegoalves.beerlist.model.Beer;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Class for test BeerDetailsActivity
 *
 * Created by diegoalves on 22/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BeerDetailsActivityTest {

    private Beer beer;

    /*
     * Get activity and puts a beer in the extras for a correct behavior of the activity
     */
    @Rule
    public ActivityTestRule<BeerDetailsActivity> rule = new ActivityTestRule<BeerDetailsActivity>(BeerDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {

            beer = new Beer();
            beer.setId(100);
            beer.setName("name");
            beer.setTagline("tagline");
            beer.setDescription("description");
            beer.setImageUrl("url");

            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra(InstrumentationRegistry.getTargetContext().getString(R.string.beer_transition_key), beer);

            return intent;
        }
    };

    /**
     * Test image view initialization
     */
    @Test
    public void ensureImageViewIsPresent() {
        BeerDetailsActivity beerDetailsActivity = rule.getActivity();
        View imageView = beerDetailsActivity.findViewById(R.id.imageViewBeerDetails);

        assertThat(imageView, notNullValue());
    }

    /**
     * Test image button to add favorite initialization
     */
    @Test
    public void ensureButtonFavoriteIsPresent() {
        BeerDetailsActivity beerDetailsActivity = rule.getActivity();
        View button = beerDetailsActivity.findViewById(R.id.buttonAddFavorite);

        assertThat(button, notNullValue());
    }

    /**
     * Test all textView initialization with correct texts of the extra object
     *
     */
    @Test
    public void ensureIfExtraObjectIsPresent() {
        BeerDetailsActivity beerDetailsActivity = rule.getActivity();

        TextView nameBeer = (TextView) beerDetailsActivity.findViewById(R.id.textViewBeerDetailsName);
        TextView taglineBeer = (TextView) beerDetailsActivity.findViewById(R.id.textViewBeerDetailsTagline);
        TextView descriptionBeer = (TextView) beerDetailsActivity.findViewById(R.id.textViewBeerDetailsDescription);

        assertEquals(nameBeer.getText().toString(), beer.getName());
        assertEquals(taglineBeer.getText().toString(), beer.getTagline());
        assertEquals(descriptionBeer.getText().toString(), beer.getDescription());

    }

}
