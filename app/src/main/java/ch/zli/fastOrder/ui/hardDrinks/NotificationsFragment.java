package ch.zli.fastOrder.ui.hardDrinks;

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
import ch.zli.fastOrder.AddHardDrinkActivity;
import ch.zli.fastOrder.HardDrink;
import ch.zli.fastOrder.HardDrinkAdapter;
import ch.zli.fastOrder.R;

public class NotificationsFragment extends Fragment {

    protected DatabaseReference reference;
    private RecyclerView drinks;
    private ArrayList<HardDrink> list;
    private HardDrinkAdapter adapter;
    private Button btnAddSoftDrink;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hard_drink, container, false);
        btnAddSoftDrink = root.findViewById(R.id.btnAddSoftDrink);
        drinks = root.findViewById(R.id.drinks);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.offsetChildrenVertical(5);
        drinks.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();
        adapter = new HardDrinkAdapter(getActivity(), list, getActivity());

        btnAddSoftDrink.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddHardDrinkActivity.class);
            startActivity(intent);
        });

        onListenDataChange();

        return root;
    }

    public void onListenDataChange() {


        reference = FirebaseDatabase.getInstance().getReference().child("Hard Drinks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HardDrink hardDrink = snapshot.getValue(HardDrink.class);
                    list.add(hardDrink);
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