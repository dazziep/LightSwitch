package com.example.darrenp.lightswitch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    List<Model> modelItems = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Get ListView object from xml
            lv = (ListView) findViewById(R.id.listView1);

            final CustomAdapter adapter = new CustomAdapter(this, modelItems);
            lv.setAdapter(adapter);

            // Use Firebase to populate the list.
            Firebase.setAndroidContext(this);

            new Firebase("https://light-switcher.firebaseio.com/lights")
                    .addChildEventListener(new ChildEventListener() {
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            modelItems.add(new Model((String) dataSnapshot.child("name").getValue(),
                                    (String) dataSnapshot.child("value").getValue()));
                            adapter.notifyDataSetChanged();
                        }

                        public void onChildRemoved(DataSnapshot dataSnapshot) {
/*                            adapter.remove(new Model((String) dataSnapshot.child("name").getValue(),
                                  (String) dataSnapshot.child("value").getValue()));*/
                        }

                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            List<Model> tempModelItems = adapter.modelItems;
                            int pos = find(tempModelItems,(String) dataSnapshot.child("name").getValue() );
                            tempModelItems.get(pos).value =  (String)dataSnapshot.child("value").getValue();
                            adapter.notifyDataSetChanged();
                        }

                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        }

                        public void onCancelled(FirebaseError firebaseError) {
                            Log.e("Firebase-Error", firebaseError.getMessage());
                        }
                    });
        }

    private int find(List<Model> models, String value) {
        for (int i = 0; i < models.size(); i++)
            if (models.get(i).getName().equals(value))
                return i;
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}