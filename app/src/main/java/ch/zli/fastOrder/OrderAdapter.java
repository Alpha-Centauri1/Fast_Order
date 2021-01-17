package ch.zli.fastOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private final Context context;
    private final ArrayList<Order> orders;

    public OrderAdapter(Context c, ArrayList<Order> d) {
        context = c;
        orders = d;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item, viewGroup, false));
    }

    /**
     * Binds the view to the corresponding holder
     * @param orderViewHolder nested class to hold items
     * @param i Integer to get the attributes of the drinkList
     */
    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, int i) {
        orderViewHolder.name.setText(orders.get(i).getName());
        orderViewHolder.amount.setText(String.valueOf(orders.get(i).getAmount()));
        orderViewHolder.price.setText(String.valueOf(orders.get(i).getPrice()));
    }

    /**
     * To get Items
     * @return number of items in recyclerView
     */
    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView amount;
        private TextView price;

        private OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.inputName);
            amount = itemView.findViewById(R.id.amount);
            price = itemView.findViewById(R.id.inputPrice);
        }
    }

}
