package com.example.shadow.iao_vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Welcome extends AppCompatActivity {


    private Button buttonlogin;
    private Button buttoncancel;
    private Button buttonstatistique;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            MCrypt crypt = new MCrypt();
        setContentView(R.layout.welcome);

        String m;

        String f ="c6d7fd2e9dd14954b9199dcc25365bc22ecdf572bdd3a467f562f66f114fb96897dbf8d69e722614162b06d0cca1b395309aee8bfdca9eb55cd2b1c81995a6457db6efb848dd3cbb54ec3efcf76069bddc1fb7af07f9773815c5a52975b9b1b2fc69d15865a062a94d182f844defc0e0627e7f10b11273333c58ed85217c4a06236e5d3e0599483d84d741360d005972";
                 try {

            m = new String(crypt.decrypt(f.trim()));
            Log.e("decrypt",m);
        } catch (Exception e) {
            e.printStackTrace();
        }




        buttonstatistique = (Button) findViewById(R.id.statis);
        buttonlogin = (Button) findViewById(R.id.buttonLogin);
        buttoncancel = (Button) findViewById(R.id.button_cancel);
        buttonlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, LoginActivity.class);
                Welcome.this.startActivity(intent);
            }
        });

        buttoncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Data.destroy();
                finishAffinity();

            }
        });


        buttonstatistique.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

           
                Intent intent = new Intent(Welcome.this, Stat_elections.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}



