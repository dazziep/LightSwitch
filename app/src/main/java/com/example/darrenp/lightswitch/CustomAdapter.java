package com.example.darrenp.lightswitch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends ArrayAdapter<Model> {
    List<Model> modelItems = null;
    Context context;

    public CustomAdapter(Context context, List<Model> resource) {
        super(context, R.layout.list_view_info, resource);
        this.context = context;
        this.modelItems = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_view_info, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        Switch sw = (Switch) convertView.findViewById(R.id.switch1);

        name.setText(modelItems.get(position).getName());
        if (modelItems.get(position).getValue().equals("on"))
            sw.setChecked(true);
        else
            sw.setChecked(false);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Firebase ref = new Firebase("https://light-switcher.firebaseio.com/lights");
                Firebase lightRef = ref.child(modelItems.get(position).getName());
                //lightRef.equalTo(modelItems.get(position).getName());

                Map<String, String> lightMap = new HashMap<String, String>();
                lightMap.put("name", modelItems.get(position).getName());
                if (isChecked)
                    lightMap.put("value", "on");
                else
                    lightMap.put("value", "off");

                //Map<String, Map<String, String>> light = new HashMap<String, Map<String, String>>();
                //light.put(modelItems.get(position).getName(), lightMap);
                lightRef.setValue(lightMap);

            }
        });

        return convertView;
    }

}