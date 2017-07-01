package com.example.shadow.iao_vote;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.*;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.*;

import java.util.HashMap;
import java.util.Map;


public class Loading_Candidats extends AppCompatActivity {
    int mStatusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("ffffffffffff",Data.idelecteurs);
        new load().execute();

        Intent intent = new Intent(Loading_Candidats.this,Candidats.class);

        startActivity(intent);

    }


    JSONArray users = null;
    JSONObject jsonObject=null;



    public class load extends  AsyncTask<String, String, Boolean>{
        String f;

        @Override
        protected Boolean doInBackground(String... params) {

            String VOTE_URL;

            final MCrypt crypt = new MCrypt();
          //  if(Data.statistique==0)
            VOTE_URL = getResources().getString(R.string.ip)+"/get_candidats.php";
             //  else
           //   VOTE_URL = getResources().getString(R.string.ip)+"/get_candidats_finish.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, VOTE_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                jsonObject = new JSONObject( response);
                                users = jsonObject.getJSONArray("result");
                                Data.prop.clear();
                                Data.idcandidatss.clear();
                                for(int i=0;i<users.length();i++){
                                    Log.e("candidats",response);
                                    JSONObject jo = users.getJSONObject(i);

                                    //verifie si on est dans mode statistique
                                 //   if(Data.statistique==0)
                                    Data.prop.add(new Property(jo.getString("nom_candidat"),jo.getString("slogon"),""));
                                   //   else
                                  //  {
                                   //   Data.prop.add(new Property(jo.getString("nom_candidat"),jo.getString("slogon"),jo.getString("vote")));
                                   //}
                                  //  Log.e("voote",jo.getString("vote"));
                                    Data.idcandidatss.add(jo.getString("id_candidat"));

                                }
                            }catch (JSONException e) {
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
                        Log.e("ooooooooooooooooooo",""+mStatusCode);
                    }
                    return super.parseNetworkResponse(response);
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                   // Log.e("asmaa", Data.idelections);
                    try {
                        map.put("id_election", Data.idelections);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return map;
                }

            };


                MySingleton.getInstance(Loading_Candidats.this).addToRequestQueue(stringRequest);

            return  true;
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            while (true)
            {
                if(mStatusCode==200)
                {
                    Intent intent = new Intent(Loading_Candidats.this,Candidats.class);
                    Log.e("HAMZA","IN LOOOP");
                    Loading_Candidats.this.startActivity(intent);
                    break;
                }
            }
        }
    }
}
