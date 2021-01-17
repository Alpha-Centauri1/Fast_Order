package ch.zli.fastOrder.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.zli.fastOrder.AddSoftDrinkActivity;
import ch.zli.fastOrder.AdminOrdersActivity;
import ch.zli.fastOrder.R;
import ch.zli.fastOrder.SoftDrink;
import ch.zli.fastOrder.SoftDrinkAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    protected DatabaseReference reference;
    private RecyclerView drinks;
    private ArrayList<SoftDrink> list;
    private SoftDrinkAdapter adapter;
    private Button btnAddSoftDrink;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        btnAddSoftDrink = root.findViewById(R.id.btnAddSoftDrink);
        drinks = root.findViewById(R.id.drinks);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.offsetChildrenVertical(5);
        drinks.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();

        btnAddSoftDrink.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSoftDrinkActivity.class);
            startActivity(intent);
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Soft Drinks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    SoftDrink softDrink = snapshot.getValue(SoftDrink.class);
                    list.add(softDrink);
                }
                adapter = new SoftDrinkAdapter(getActivity(), list);
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