package ch.zli.fastOrder;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
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

public class AdminOrdersActivity extends AppCompatActivity {

    private TextView endpage;
    private Button btnAddNew;

    protected DatabaseReference reference;
    private RecyclerView drinks;
    private ArrayList<Order> list;
    private OrderAdapter adapter;

    /**
     * Instantiate Activity through calling onCreate
     * Only called once, when application is being instantiated
     * @param savedInstanceState a Bundle object containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_orders);

        endpage = findViewById(R.id.endpage);
        drinks = findViewById(R.id.drinks);
        btnAddNew = findViewById(R.id.btnCreate);
        drinks.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        reference = FirebaseDatabase.getInstance().getReference().child("Client Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Order order = dataSnapshot1.getValue(Order.class);
                    list.add(order);
                }
                adapter = new OrderAdapter(AdminOrdersActivity.this, list);
                drinks.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (adapter.getItemCount() != 0) {
                    endpage.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        });

    }
}