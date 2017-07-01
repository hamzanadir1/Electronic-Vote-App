package com.example.shadow.iao_vote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Stat_elections extends AppCompatActivity {

    public  int mStatusCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("aazzaz","before destroy");
        Data.destroy();
        Log.e("aazzaz","after destroy");
        new load().execute();
        Log.e("aazzaz","after execute");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        finish();

    }

    JSONArray users = null;
    JSONObject jsonObject = null;


    public class load extends AsyncTask<String, String, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {

            String VOTE_URL;


            Log.e("ismail","ismail");
            VOTE_URL = getResources().getString(R.string.ip) + "/get_membership_finish.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, VOTE_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.e("sss",response);
                                jsonObject = new JSONObject(response);
                                users = jsonObject.getJSONArray("result");


                                for (int i = 0; i < users.length(); i++) {
                                    JSONObject jo = users.getJSONObject(i);
                                    Data.data.add(jo.getString("nom_election"));
                                    Data.ids.add(jo.getString("id_election"));
                                    Data.date.add(jo.getString("date_fin_election"));
                                    Log.e("aaaaa", Data.ids.get(i).toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })

            {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    if (response != null) {
                        mStatusCode = response.statusCode;

                    }
                    return super.parseNetworkResponse(response);
                }


            };


            MySingleton.getInstance(Stat_elections.this).addToRequestQueue(stringRequest);

            return true;
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            while (true)
            {
                if(mStatusCode==200)
                {
                    Intent intent = new Intent(Stat_elections.this,Stat_elections_list.class);
                    Log.e("HAMZA","IN STAT ELECTIONS");
                    Stat_elections.this.startActivity(intent);
                    break;
                }
            }
        }
    }

}

