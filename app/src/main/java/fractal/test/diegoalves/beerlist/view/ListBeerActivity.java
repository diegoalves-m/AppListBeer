package fractal.test.diegoalves.beerlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fractal.test.diegoalves.beerlist.R;
import fractal.test.diegoalves.beerlist.controller.BeerOnClickListener;
import fractal.test.diegoalves.beerlist.controller.BeerService;
import fractal.test.diegoalves.beerlist.model.Beer;
import fractal.test.diegoalves.beerlist.utils.Connected;
import fractal.test.diegoalves.beerlist.view.adapters.BeerListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class list beers
 */
public class ListBeerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, BeerOnClickListener {

    private static final String TAG = ListBeerActivity.class.getName();
    @BindView(R.id.recyclerViewBeer) protected RecyclerView mRecyclerViewBeer;
    @BindView(R.id.progressBarBeers) protected ProgressBar mProgressBar;
    @BindView(R.id.swipeRefreshLayout) protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.imageViewDynamics) protected ImageView imageViewDynamics;
    private BeerListAdapter beerListAdapter;
    private List<Beer> beerList, searchList, aux;
    Connected connected;

    /**
     * Method responsible for creation of activity
     *
     * @param savedInstanceState So the activity can save status information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_beer);

        configViews();
        connected = new Connected(this);

        // check internet connection
        if (connected.hasConnectionInternet()) {
            listBeers();
        } else {
            showSnackBar();
            mProgressBar.setVisibility(View.INVISIBLE);
            imageViewDynamics.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.no_clould));
            imageViewDynamics.setVisibility(View.VISIBLE);
        }

        // swipe down to refresh list of beers
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(connected.hasConnectionInternet()) {
                    listBeers();
                } else {
                    showSnackBar();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    /**
     * Method to retrieve list of beers from api endpoint, using lib Retrofit
     */
    private void listBeers() {

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        BeerService beerService = mRetrofit.create(BeerService.class);

        Call<List<Beer>> callRequest = beerService.beerList();

        callRequest.enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                if (response.isSuccessful()) {
                    beerList = response.body();
                    mSwipeRefreshLayout.setRefreshing(false);
                    fillView();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(ListBeerActivity.this, "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {
                Log.e(TAG, "ERROR " + t.getMessage());
            }
        });
    }

    /**
     * Method to control the filling of the list and control other items of the view
     */
    private void fillView() {
        beerListAdapter = new BeerListAdapter(beerList, ListBeerActivity.this, ListBeerActivity.this);
        mRecyclerViewBeer.setAdapter(beerListAdapter);
        mProgressBar.setVisibility(View.INVISIBLE);
        imageViewDynamics.setVisibility(View.INVISIBLE);
    }

    /**
     * Config views for the activity
     */
    private void configViews() {

        // Using lip ButterKnife for linking views
        ButterKnife.bind(this);

        mRecyclerViewBeer.setHasFixedSize(true);
        mRecyclerViewBeer.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary, R.color.colorSecondaryDark, R.color.colorPrimaryDark);

    }


    /**
     * Method responsible for the user search filter
     *
     * @param beers list of beers
     * @param query user input text
     * @return List of beers found
     */
    private List<Beer> filter(List<Beer> beers, String query) {
        aux = new ArrayList<>();
        if(connected.hasConnectionInternet()) {
            for (int i = 0; i < beerList.size(); i++) {
                if (beers.get(i).toString().toLowerCase().contains(query.toLowerCase())) {
                    aux.add(beerList.get(i));
                }
            }
        }
        return aux;
    }

    /**
     * Method to create and show SnackBar
     */
    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.contentList), getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.setAction(getString(R.string.try_again), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected.hasConnectionInternet()) {
                    listBeers();
                } else {
                    showSnackBar();
                }
            }
        });
        snackbar.show();
    }

    /**
     * Method responsible for creating the search menu
     *
     * @param menu responsible to inflate menu items
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_beer, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.hint_search_beer));
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Method responsible for selecting menu items
     *
     * @param item receive item clicked by user
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_favorite) {
            startActivity(new Intent(this, FavoritesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method responsible for the submission of query
     *
     * @param query user input text
     * @return boolean
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Method responsible for filtering user search in real time
     *
     * @param newText all text changed by user during search
     * @return boolean
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        searchList = filter(beerList, newText);
        beerListAdapter = new BeerListAdapter(searchList, this, this);
        mRecyclerViewBeer.setAdapter(beerListAdapter);
        mRecyclerViewBeer.scrollToPosition(0);

        if (searchList.isEmpty()) {
            imageViewDynamics.setVisibility(View.VISIBLE);
        } else {
            imageViewDynamics.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    /**
     * Method responsible for controlling beer clicks to show more details
     *
     * @param position inform item position clicked by user in list
     */
    @Override
    public void onClickBeer(int position) {
        if (searchList != null) {
            aux = searchList;
        } else {
            aux = beerList;
        }
        Beer beer = aux.get(position);
        Intent intent = new Intent(this, BeerDetailsActivity.class);
        intent.putExtra(getString(R.string.beer_transition_key), beer);
        startActivity(intent);
    }
}
