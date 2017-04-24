package fractal.test.diegoalves.beerlist.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fractal.test.diegoalves.beerlist.R;
import fractal.test.diegoalves.beerlist.view.adapters.BeerListAdapter;
import fractal.test.diegoalves.beerlist.controller.BeerOnClickListener;
import fractal.test.diegoalves.beerlist.model.Beer;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Class list favorites beers
 */
public class FavoritesActivity extends AppCompatActivity implements BeerOnClickListener {

    @BindView(R.id.recyclerViewBeerFavorites) protected RecyclerView mRecyclerViewBeerFavorites;
    @BindView(R.id.imageViewNoFavoriteBeer) protected ImageView imageViewNoFavoriteBeer;
    private List<Beer> beerList;
    private Realm mRealm;

    /**
     * Method responsible for creation of activity
     *
     * @param savedInstanceState So the activity can save status information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_beer_favorites);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        configViews();

        // init and getInstance of Realm to database access
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

    }

    /**
     * Config views for the activity
     */
    private void configViews() {

        // Using lip ButterKnife for linking views
        ButterKnife.bind(this);

        mRecyclerViewBeerFavorites.setHasFixedSize(true);
        mRecyclerViewBeerFavorites.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Method responsible for control beers list and items to show in view
     */
    private void updateItems() {
        BeerListAdapter beerListAdapter = new BeerListAdapter(retrieveBeersFromDatabase(), this, this);
        mRecyclerViewBeerFavorites.setAdapter(beerListAdapter);
        if(!retrieveBeersFromDatabase().isEmpty()) {
            imageViewNoFavoriteBeer.setVisibility(View.INVISIBLE);
        }
        else {
            imageViewNoFavoriteBeer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to keep the view always up to date
     */
    @Override
    protected void onStart() {
        super.onStart();
        updateItems();
    }

    /**
     *  Method to check database to find favorites beers
     * @return list of beers
     */
    private List<Beer> retrieveBeersFromDatabase() {
        beerList = new ArrayList<>();

        RealmResults<Beer> realmResults = mRealm.where(Beer.class).findAll();

        for (Beer b : realmResults) {
            beerList.add(b);
        }

        return beerList;
    }

    /**
     * Method responsible for controlling beer clicks to show more details
     * @param position list beer position clicked by user
     */
    @Override
    public void onClickBeer(int position) {
        Beer beer = beerList.get(position);
        Intent intent = new Intent(this, BeerDetailsActivity.class);
        intent.putExtra(getString(R.string.beer_transition_key), beer);
        startActivity(intent);
    }

    /**
     * Method responsible for selecting menu items
     * @param item receive item clicked by user
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }
}
