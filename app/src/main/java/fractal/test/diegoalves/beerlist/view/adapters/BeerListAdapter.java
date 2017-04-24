package fractal.test.diegoalves.beerlist.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fractal.test.diegoalves.beerlist.R;
import fractal.test.diegoalves.beerlist.controller.BeerOnClickListener;
import fractal.test.diegoalves.beerlist.model.Beer;

/**
 * Adapter class to control list items
 *
 * Created by diegoalves on 21/04/2017.
 */

public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.BeerViewHolder> {

    private List<Beer> beerList;
    private Context mContext;
    private BeerOnClickListener mBeerOnClickListener;

    public BeerListAdapter(List<Beer> beerList, Context context, BeerOnClickListener beerOnClickListener) {
        this.beerList = beerList;
        this.mContext = context;
        this.mBeerOnClickListener = beerOnClickListener;
    }

    /**
     * Method responsible for inflating the beer item
     * @param parent father view that can contain other views
     * @param viewType The view type of this ViewHolder
     * @return view for each item in the list
     */
    @Override
    public BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.beer_item, parent, false);
        return new BeerViewHolder(view);
    }

    /**
     * Method responsible for popular data of each item of beer
     * @param holder view references
     * @param position position list items
     */
    @Override
    public void onBindViewHolder(final BeerViewHolder holder, int position) {
        Beer beer = beerList.get(position);

        holder.textViewNameBeer.setText(beer.getName());
        holder.textViewTaglineBeer.setText(beer.getTagline());

        if(mBeerOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBeerOnClickListener.onClickBeer(holder.getAdapterPosition());
                }
            });
        }

    }

    /**
     * Controls how many items to display based on the item list
     * @return size of the list
     */
    @Override
    public int getItemCount() {
        return beerList.size();
    }

    /**
     * Class to instantiate view items
     */
    class BeerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNameBeer;
        TextView textViewTaglineBeer;

        public BeerViewHolder(View itemView) {
            super(itemView);

            textViewNameBeer = (TextView) itemView.findViewById(R.id.beerName);
            textViewTaglineBeer = (TextView) itemView.findViewById(R.id.beerTagline);

        }
    }
}
