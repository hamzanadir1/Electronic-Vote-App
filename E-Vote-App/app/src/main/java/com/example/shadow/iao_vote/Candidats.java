package com.example.shadow.iao_vote;

import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.*;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Candidats extends Activity {

ImageView power;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.candidats_list_view);
        power= (ImageView) findViewById(R.id.power_button) ;
        power.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Candidats.this);
                builder1.setMessage("Voulez Vous Vous Déconnecter ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Data.destroy();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

                                startActivity(intent);
                                finish();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });

        Log.e("aaaaaaaaaaa",""+ Data.prop.size());
        lv = (ListView) findViewById(R.id.id_candidats_list_view);

        propertyArrayAdapter properyAdapt = new propertyArrayAdapter(this, R.layout.candidats_list_item , Data.prop );

        lv.setAdapter(properyAdapt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Candidats.this,Vote.class);

                    Data.p = Data.prop.get(position);
                    Data.idcandidats=Data.idcandidatss.get(position);

                    Candidats.this.startActivity(intent);


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
        Intent intent = new Intent(Candidats.this,Loading.class);
       Log.e("gggg","finishevent");
        Candidats.this.startActivity(intent);
        finish();


    }



//custom ArrayAdapter
class propertyArrayAdapter extends ArrayAdapter<Property>{

    private Context context;
    private ArrayList<Property> Properties;

    //constructor, call on creation
    public propertyArrayAdapter(Context context, int resource, ArrayList<Property> objects) {
        super(context, resource, objects);

        this.context = context;
        this.Properties = objects;
    }


    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {


        Property property = Properties.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.candidats_list_item, null);

        TextView description = (TextView) view.findViewById(R.id.description);
        TextView nom = (TextView) view.findViewById(R.id.nom_candidat);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        Picasso.with(context).load( getResources().getString(R.string.ip)+"/logos/candidats/"+Data.prop.get(position).nom+".png").into(image);

        nom.setText(property.nom);

        //display trimmed excerpt for description
        int descriptionLength = property.description.length();
        if(descriptionLength >= 100){
            String descriptionTrim = property.description.substring(0, 70) + "...";
            description.setText(descriptionTrim);
        }else{
            description.setText(property.description);
        }



        return view;
    }
}

}
