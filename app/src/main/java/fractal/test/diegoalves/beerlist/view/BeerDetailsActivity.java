package fractal.test.diegoalves.beerlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import fractal.test.diegoalves.beerlist.R;
import fractal.test.diegoalves.beerlist.model.Beer;
import fractal.test.diegoalves.beerlist.utils.Connected;
import io.realm.Realm;

/**
 * Class to show beer details
 */
public class BeerDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Beer beer;
    private Realm mRealm;
    @BindView(R.id.buttonAddFavorite) protected Button buttonAddFavorite;
    @BindView(R.id.imageViewBeerDetails) protected ImageView imageViewBeer;
    @BindViews({R.id.textViewBeerDetailsName, R.id.textViewBeerDetailsTagline, R.id.textViewBeerDetailsDescription}) protected List<TextView> textViews;

    /**
     * Method responsible for creation of activity
     * @param savedInstanceState So the activity can save status information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_beer_details);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        // object intent to get extras of the previous activity
        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            beer = intent.getParcelableExtra(getString(R.string.beer_transition_key));
        } else {
            finish();
            Toast.makeText(this, R.string.internal_error, Toast.LENGTH_SHORT).show();
        }

        // init and getInstance of Realm to database access
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        configViews();

        // check internet connection
        Connected connected = new Connected(this);
        if(!connected.hasConnectionInternet()) {
            imageViewBeer.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.icon));
        }
    }

    /**
     * Config views for the activity
     */
    private void configViews() {

        // Using lip ButterKnife for linking views
        ButterKnife.bind(this);

        textViews.get(0).setText(beer.getName());
        textViews.get(1).setText(beer.getTagline());
        textViews.get(2).setText(beer.getDescription());

        buttonAddFavorite.setOnClickListener(this);
        changeTextButton();

        Picasso.with(this).load(beer.getImageUrl()).into(imageViewBeer);

    }

    /**
     * Method to control text in button
     */
    private void changeTextButton() {
        if(isFavorite()) {
            buttonAddFavorite.setText(R.string.remove_from_favorites);
        } else {
            buttonAddFavorite.setText(R.string.add_to_favorites);
        }
    }

    /**
     * Method to return if beer is favorite or no
     *
     * @return boolean true or false
     */
    private boolean isFavorite() {
        Beer favoriteBer = mRealm.where(Beer.class).equalTo("id", beer.getId()).findFirst();

        return favoriteBer != null;
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

    /**
     * Method to control click on button the add or remove favorite
     *
     * @param v item clicked on view
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.buttonAddFavorite) {

            mRealm.beginTransaction();

            if(!isFavorite()) {
                Beer beerDatabasePersist = mRealm.createObject(Beer.class);
                beerDatabasePersist.setId(beer.getId());
                beerDatabasePersist.setName(beer.getName());
                beerDatabasePersist.setTagline(beer.getTagline());
                beerDatabasePersist.setImageUrl(beer.getImageUrl());
                beerDatabasePersist.setDescription(beer.getDescription());

                Toast.makeText(this, R.string.added_favorites, Toast.LENGTH_SHORT).show();
                changeTextButton();
            } else {
                Beer deleteBeer = mRealm.where(Beer.class).equalTo("id", beer.getId()).findFirst();
                deleteBeer.deleteFromRealm();
                Toast.makeText(this, R.string.removed_favorites, Toast.LENGTH_SHORT).show();
                changeTextButton();
            }

            mRealm.commitTransaction();
        }

    }

}
