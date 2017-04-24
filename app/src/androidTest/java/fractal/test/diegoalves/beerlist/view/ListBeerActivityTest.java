package fractal.test.diegoalves.beerlist.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fractal.test.diegoalves.beerlist.R;

/**
 * Class for test ListBeerActivity
 *
 * Created by diegoalves on 22/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ListBeerActivityTest {

    // get activity
    @Rule
    public ActivityTestRule<ListBeerActivity> rule = new ActivityTestRule<>(ListBeerActivity.class);

    /**
     * Test if recyclerView initialization is correct
     */
    @Test
    public void ensureRecyclerViewIsPresent() {
        ListBeerActivity listBeerActivity = rule.getActivity();
        View viewById = listBeerActivity.findViewById(R.id.recyclerViewBeer);

        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
    }

    /**
     * Test if menuOptions initialization is correct
     */
    @Test
    public void ensureMenuOptionsIsPresent() {
        ListBeerActivity listBeerActivity = rule.getActivity();
        View itemSearch = listBeerActivity.findViewById(R.id.item_search);
        View itemFavorite = listBeerActivity.findViewById(R.id.item_favorite);

        assertThat(itemSearch, notNullValue());
        assertThat(itemFavorite, notNullValue());
    }

}
