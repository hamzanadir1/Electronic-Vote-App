package com.example.shadow.iao_vote;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 2/2/17.
 */
public class AddingListener extends Service {
    boolean isRunning = true;

    public static int length = Data.data.size() ;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        new getnews().execute(4, 700);
    }//onStart


    @Override
    public void onDestroy() {

        isRunning = false;
    }
    public class getnews extends AsyncTask<
            Integer, Integer, Integer > {

      final   JSONArray users = null;
      final   JSONObject jsonObject = null;
        @Override
        protected Integer doInBackground(Integer... params) {
            final JSONArray users = null;
            JSONObject jsonObject = null;

           while (true)
           {
               String VOTE_URL;

               // if(Data.statistique==0)
               //  {
               Log.e("ismail","ismail");
               VOTE_URL = getResources().getString(R.string.ip) + "/get_membership.php";
               // }
               //  else
               //  VOTE_URL = getResources().getString(R.string.ip) + "/get_membership_finish.php";
               StringRequest stringRequest = new StringRequest(Request.Method.POST, VOTE_URL,
                       new Response.Listener<String>() {

                           @Override
                           public void onResponse(String response) {



                           }
                       },
                       new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               }
                       })

               {

                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String, String> map = new HashMap<String, String>();
                       map.put("code", Data.idelecteurs);

                       return map;
                   }

               };


               MySingleton.getInstance(AddingListener.this).addToRequestQueue(stringRequest);

               return 0;
           }

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Intent intentFilter5 = new Intent("maris");
            String data = "dataItem-5-fibonacci-AsyncTask"
                    + values[0] + ": " + values[1];
            intentFilter5.putExtra("MyService5DataItem", data+" hamza");
            sendBroadcast(intentFilter5);
// (next id not really needed!!! - we did the broadcasting already)

        }
    }// ComputeFibonacciRecursivelyTask
}//MyService5