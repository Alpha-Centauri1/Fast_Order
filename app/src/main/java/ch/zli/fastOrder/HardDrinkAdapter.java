package ch.zli.fastOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HardDrinkAdapter extends RecyclerView.Adapter<HardDrinkAdapter.HardDrinkViewHolder> {

    private final Context context;
    private final ArrayList<HardDrink> drinks;
    private DatabaseReference reference;
    private Activity activity;
    public Button btnDelete;

    public HardDrinkAdapter(Context c, ArrayList<HardDrink> d, Activity a) {
        context = c;
        drinks = d;
        activity = a;
    }


    @NonNull
    @Override
    public HardDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HardDrinkViewHolder(LayoutInflater.from(context).inflate(R.layout.drink_item, viewGroup, false));
    }

    /**
     * Binds the view to the corresponding holder
     *
     * @param hardDrinkViewHolder nested class to hold items
     * @param i                   Integer to get the attributes of the drinkList
     */
    @Override
    public void onBindViewHolder(@NonNull final HardDrinkViewHolder hardDrinkViewHolder, int i) {
        hardDrinkViewHolder.name.setText(drinks.get(i).getName());
        hardDrinkViewHolder.price.setText(String.valueOf(drinks.get(i).getPrice()));

        final String name = drinks.get(i).getName();
        btnDelete = activity.findViewById(R.id.btnDelete);
        btnDelete.setVisibility(View.GONE);

        reference = FirebaseDatabase.getInstance().getReference().child("Hard Drinks");

        hardDrinkViewHolder.itemView.setOnClickListener(v -> {
            btnDelete.setVisibility(View.VISIBLE);
            System.out.println("Click Item");

            btnDelete.setOnClickListener(s -> {
                System.out.println("Click Delete");

                Query query = reference.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                            Intent intent = new Intent(activity, AdminOrdersActivity.class);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError);
                    }
                });

            });
        });
    }

    /**
     * To get Items
     *
     * @return number of items in recyclerView
     */
    @Override
    public int getItemCount() {
        return drinks.size();
    }

    static class HardDrinkViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        private HardDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.inputName);
            price = itemView.findViewById(R.id.inputPrice);
        }
    }

}
