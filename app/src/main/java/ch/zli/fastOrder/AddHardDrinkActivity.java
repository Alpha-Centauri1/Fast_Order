package ch.zli.fastOrder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AddHardDrinkActivity extends AppCompatActivity {

    private EditText name, price;
    private Button btnAdd, btnCancel;
    private DatabaseReference reference;
    private Integer num = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_hard_drink);

        name = findViewById(R.id.inputName);
        price = findViewById(R.id.inputPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "14141")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Drink was created!")
                .setContentText("A new drink was added to the library")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        btnAdd.setOnClickListener(v -> {
            reference = FirebaseDatabase.getInstance().getReference().child("Hard Drinks").
                    child("HardDrink" + num);
            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                    dataSnapshot.getRef().child("price").setValue(Float.parseFloat(price.getText().toString()));

                    notificationManager.notify(10, builder.build());
                    System.out.println(notificationManager.areNotificationsEnabled());

                    Intent intent = new Intent(AddHardDrinkActivity.this, AdminOrdersActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    System.out.println(databaseError);
                }
            });
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(AddHardDrinkActivity.this, AdminOrdersActivity.class);
            startActivity(intent);
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "drinkChannel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("14141", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}