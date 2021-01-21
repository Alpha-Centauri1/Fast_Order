package ch.zli.clientapp.ui.softDrink;

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
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ch.zli.clientapp.Order;
import ch.zli.clientapp.OrdersActivity;
import ch.zli.clientapp.R;

public class SoftDrinkAdapter extends RecyclerView.Adapter<SoftDrinkAdapter.SoftDrinkViewHolder> {

    private final Context context;
    private final ArrayList<SoftDrink> drinks;
    private DatabaseReference reference;
    private Activity activity;
    private TextView total, name, price, amount;
    private Button order;
    private SoftDrinkViewHolder softDrinkViewHolder;
    private Integer num = new Random().nextInt();

    public SoftDrinkAdapter(Context c, ArrayList<SoftDrink> d, Activity a) {
        context = c;
        drinks = d;
        activity = a;
    }


    @NonNull
    @Override
    public SoftDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SoftDrinkViewHolder(LayoutInflater.from(context).inflate(R.layout.drink_item, viewGroup, false));
    }

    /**
     * Binds the view to the corresponding holder
     *
     * @param softDrinkViewHolder nested class to hold items
     * @param i                   Integer to get the attributes of the drinkList
     */
    @Override
    public void onBindViewHolder(@NonNull final SoftDrinkViewHolder softDrinkViewHolder, int i) {
        softDrinkViewHolder.name.setText(drinks.get(i).getName());
        softDrinkViewHolder.price.setText(String.valueOf(drinks.get(i).getPrice()));
        this.softDrinkViewHolder = softDrinkViewHolder;

        Float price = drinks.get(i).getPrice();
        ArrayList<Float> accumulatedPrices = new ArrayList<>();
        AtomicInteger clicked = new AtomicInteger();
        total = activity.findViewById(R.id.totalValue);
        reference = FirebaseDatabase.getInstance().getReference().child("Soft Drinks");


        softDrinkViewHolder.itemView.setOnClickListener(v -> {
            clicked.getAndIncrement();

            this.name = v.findViewById(R.id.inputName);
            this.price = v.findViewById(R.id.inputPrice);
            this.amount = v.findViewById(R.id.amount);

            softDrinkViewHolder.amount.setText(String.valueOf(clicked));
            Float totalValue = Float.parseFloat(String.valueOf(this.amount.getText())) * Float.parseFloat(String.valueOf(this.price.getText()));
            total.setText(String.valueOf(totalValue));
        });

        System.out.println(softDrinkViewHolder.amount.getText() + " " + softDrinkViewHolder.price.getText());

        order = activity.findViewById(R.id.order);
        order.setOnClickListener(v -> {
            System.out.println(softDrinkViewHolder.amount.getText() + " " + softDrinkViewHolder.price.getText());
        });

        softDrinkViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clicked.set(0);
                softDrinkViewHolder.amount.setText("");
                total.setText("");
                return true;
            }
        });

        sendOrder();
    }

    public void sendOrder() {
        order.setOnClickListener(v -> {
            reference = FirebaseDatabase.getInstance().getReference().child("Client Orders").
                    child("ClientOrder" + num);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                    dataSnapshot.getRef().child("price").setValue(Float.parseFloat(price.getText().toString()));
                    dataSnapshot.getRef().child("amount").setValue(Float.parseFloat(amount.getText().toString()));

                    Intent intent = new Intent(activity, OrdersActivity.class);
                    activity.startActivity(intent);
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    System.out.println(databaseError);
                }
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

    static class SoftDrinkViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private TextView amount;

        private SoftDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.inputName);
            price = itemView.findViewById(R.id.inputPrice);
            amount = itemView.findViewById(R.id.amount);
        }
    }

}
