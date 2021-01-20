package ch.zli.fastOrder.ui.softDrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.zli.fastOrder.AddSoftDrinkActivity;
import ch.zli.fastOrder.R;
import ch.zli.fastOrder.SoftDrink;
import ch.zli.fastOrder.SoftDrinkAdapter;

public class DashboardFragment extends Fragment {

    protected DatabaseReference reference;
    private RecyclerView drinks;
    private ArrayList<SoftDrink> list;
    private SoftDrinkAdapter adapter;
    private Button btnAddSoftDrink;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_soft_drink, container, false);
        btnAddSoftDrink = root.findViewById(R.id.btnAddSoftDrink);
        drinks = root.findViewById(R.id.drinks);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.offsetChildrenVertical(5);
        drinks.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();
        adapter = new SoftDrinkAdapter(getActivity(), list, getActivity());

        btnAddSoftDrink.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSoftDrinkActivity.class);
            startActivity(intent);
        });

        onListenDataChange();

        return root;
    }

    public void onListenDataChange() {


        reference = FirebaseDatabase.getInstance().getReference().child("Soft Drinks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SoftDrink softDrink = snapshot.getValue(SoftDrink.class);
                    list.add(softDrink);
                }
                drinks.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });

    }

}