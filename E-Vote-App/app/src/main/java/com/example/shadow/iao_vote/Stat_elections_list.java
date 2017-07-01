package com.example.shadow.iao_vote;

/**
 * Created by SHADOW on 1/22/2017.
 */

import android.app.Activity;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.*;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;


public class Stat_elections_list extends AppCompatActivity {


    ImageView power;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if(Data.statistique==0)
        setContentView(R.layout.activity_main);
        //   else
        // setContentView(R.layout.statis_elections_list_view);


        TextView count = (TextView) findViewById(R.id.count);
        Data.taille = Data.data.size() ;

        count.setText(Integer.toString(Data.data.size()));




            TextView t = (TextView) findViewById(R.id.l) ;
            t.setVisibility(View.VISIBLE);

            lv = (ListView) findViewById(R.id.listview);


            MyListAdaper  myListadaper = new MyListAdaper(this, R.layout.list_item, Data.data);
            lv.setAdapter(myListadaper);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Stat_elections_list.this,Stat_candidats.class);
                    Data.idelections = Data.ids.get(position);

                   Log.e("stat electiobns list",Data.idelections);
                    Stat_elections_list.this.startActivity(intent);
                    finish();
                }
            });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Stat_elections_list.this,Welcome.class);
        Data.destroy();
        Stat_elections_list.this.startActivity(intent);
        finish();

    }



    public class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private ArrayList<String> mObjects;
        private Context context;


        private MyListAdaper(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.list_item, null);
            TextView date = (TextView) view.findViewById(R.id.date);
            TextView title = (TextView) view.findViewById(R.id.list_item_text);

            title.setText(mObjects.get(position).toString());
            date.setText(Data.date.get(position));

            return view;
        }

    }



}
