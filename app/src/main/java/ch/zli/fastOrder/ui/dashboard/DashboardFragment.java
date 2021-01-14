package ch.zli.fastOrder.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import ch.zli.fastOrder.AdminOrdersActivity;
import ch.zli.fastOrder.Order;
import ch.zli.fastOrder.OrderAdapter;
import ch.zli.fastOrder.R;
import ch.zli.fastOrder.SoftDrink;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    protected DatabaseReference reference;
    private RecyclerView drinks;
    private ArrayList<SoftDrink> list;
    private OrderAdapter adapter;
    private Button btnAddSoftDrink;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        btnAddSoftDrink = root.findViewById(R.id.btnAddSoftDrink);

        reference = FirebaseDatabase.getInstance().getReference().child("Soft Drinks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    SoftDrink softDrink = dataSnapshot1.getValue(SoftDrink.class);
                    list.add(softDrink);
                }
                adapter = new OrderAdapter(getActivity(), list);
                drinks.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });

        return root;
    }

}