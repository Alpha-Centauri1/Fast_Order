package ch.zli.fastOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import ch.zli.fastOrder.ui.dashboard.DashboardFragment;

public class AddSoftDrinkActivity extends AppCompatActivity {

    private EditText name, price;
    private Button btnAdd, btnCancel;
    private DatabaseReference reference;
    private Integer num = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_soft_drink);

        name = findViewById(R.id.inputName);
        price = findViewById(R.id.inputPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(v -> {

            reference = FirebaseDatabase.getInstance().getReference().child("Soft Drinks").
                    child("SoftDrink" + num);
            reference.addValueEventListener(new ValueEventListener() {

                /**
                 *Call this method when database is ready
                 * @param dataSnapshot is an efficiently generated copy of the data at a Database location
                 */
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                    dataSnapshot.getRef().child("price").setValue(Float.parseFloat(price.getText().toString()));
                    //dataSnapshot.getRef().child("key").setValue(key);

                    Intent intent = new Intent(AddSoftDrinkActivity.this, DashboardFragment.class);
                    startActivity(intent);
                }

                /**
                 * Method is only called, when something went wrong
                 * @param databaseError contains the data to what went wrong, like message and status code
                 */
                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    System.out.println(databaseError);
                }
            });
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(AddSoftDrinkActivity.this, AdminOrdersActivity.class);
            startActivity(intent);
        });
    }

}