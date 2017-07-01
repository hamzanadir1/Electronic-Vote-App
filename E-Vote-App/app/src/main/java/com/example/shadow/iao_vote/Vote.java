package com.example.shadow.iao_vote;

/**
 * Created by Hamza on 1/27/2017.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;



import static android.app.PendingIntent.getActivity;

public class Vote extends AppCompatActivity implements View.OnClickListener{

    Button buttonvote;
    ImageView power;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.vote_layout);
        TextView nom = (TextView) findViewById(R.id.nom) ;
        nom.setText(Data.p.nom);

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(Data.p.description);
        TextView txtdate = (TextView) findViewById(R.id.date);
        txtdate.setText(Data.date.get(Data.ids.indexOf(Data.idelections)));
        ImageView img = (ImageView) findViewById(R.id.logo) ;

        power= (ImageView) findViewById(R.id.power_button) ;
        power.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Vote.this);
                builder1.setMessage("Voulez Vous Se Déconnecter ?");
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
        Picasso.with(Vote.this).load( getResources().getString(R.string.ip)+"/logos/candidats/"+Data.p.nom+".png").into(img);
       buttonvote = (Button) findViewById(R.id.buttonVote);

        buttonvote.setOnClickListener(this);

    }

    private void voter() {

        String LOGIN_URL= getResources().getString(R.string.ip)+"vote.php";

        final MCrypt crypt = new MCrypt();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.trim().equals("success")){

                                 Toast.makeText(Vote.this,"Merci Pour Participation Vous Serez Rediriger à La Liste Des Elections ",Toast.LENGTH_LONG ).show();
                                goback();

                            }else{
                                Toast.makeText(Vote.this,"Une Erreur s'est produit",Toast.LENGTH_LONG ).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Vote.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();


                    Log.e("a",Data.idelections.toString());
                Log.e("a",Data.idcandidats.toString());
                Log.e("a",Data.idelecteurs.toString());
                try {
                    map.put("idelection", crypt.bytesToHex(crypt.encrypt(Data.idelections)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    map.put("idcandidat", crypt.bytesToHex(crypt.encrypt(Data.idcandidats)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    map.put("idelecteur",crypt.bytesToHex(crypt.encrypt(Data.idelecteurs)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void goback()
    {
        Intent intent = new Intent(getApplicationContext(),Loading.class);

        startActivity(intent);
        finish();
        Data.destroy();
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Vous etes sur que vous voulez voter pour ce candidat ?.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        voter();

                        buttonvote.setVisibility(View.INVISIBLE);
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

}
