package ch.zli.fastOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SoftDrinkAdapter extends RecyclerView.Adapter<SoftDrinkAdapter.SoftDrinkViewHolder>{

    private final Context context;
    private final ArrayList<SoftDrink> drinks;

    public SoftDrinkAdapter(Context c, ArrayList<SoftDrink> d) {
        context = c;
        drinks = d;
    }


    @NonNull
    @Override
    public SoftDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SoftDrinkViewHolder(LayoutInflater.from(context).inflate(R.layout.drink_item, viewGroup, false));
    }

    /**
     * Binds the view to the corresponding holder
     * @param softDrinkViewHolder nested class to hold items
     * @param i Integer to get the attributes of the drinkList
     */
    @Override
    public void onBindViewHolder(@NonNull final SoftDrinkViewHolder softDrinkViewHolder, int i) {
        softDrinkViewHolder.name.setText(drinks.get(i).getName());
        softDrinkViewHolder.price.setText(String.valueOf(drinks.get(i).getPrice()));
    }

    /**
     * To get Items
     * @return number of items in recyclerView
     */
    @Override
    public int getItemCount() {
        return drinks.size();
    }

    static class SoftDrinkViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        private SoftDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.inputName);
            price = itemView.findViewById(R.id.inputPrice);
        }
    }

}
