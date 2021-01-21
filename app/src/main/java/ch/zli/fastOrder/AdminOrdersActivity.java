package ch.zli.fastOrder;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class AdminOrdersActivity extends AppCompatActivity {

    private TextView endpage;
    protected DatabaseReference reference;
    private RecyclerView drinks;
    private ArrayList<Order> list;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_orders);

        endpage = findViewById(R.id.endpage);
        drinks = findViewById(R.id.orders);
        drinks.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Client Orders");
        adapter = new OrderAdapter(AdminOrdersActivity.this, list);
        drinks.setAdapter(adapter);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Order order = snapshot.getValue(Order.class);
                    System.out.println(order.getName());
                    list.add(order);
                }
                adapter.notifyDataSetChanged();

                for (Order order : list) {
                    System.out.println(order.getName());
                }

                if (adapter.getItemCount() != 0) {
                    endpage.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        };
        reference.addValueEventListener(listener);

    }
}