package com.example.shadow.iao_vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    public String KEY_CIN = "CIN" ;
    public  String KEY_CODE = "CODE";

    private EditText editTextCIN;
    private EditText editTextCode;
    private Button buttonlogin;
    private Button buttoncancel;
    private String cin;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        editTextCIN = (EditText) findViewById(R.id.editTextCIN);
        editTextCode = (EditText) findViewById(R.id.editTextCode);

        buttonlogin = (Button) findViewById(R.id.buttonLogin);
        buttoncancel = (Button) findViewById(R.id.button_cancel);

        buttonlogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                userLogin();
            }
        });



        buttoncancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(LoginActivity.this, Welcome.class);
                LoginActivity.this.startActivity(intent);
                Data.destroy();
                finish();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
    private void userLogin() {

        final MCrypt crypt = new MCrypt();

        String LOGIN_URL= getResources().getString(R.string.ip)+"login.php";

        cin = editTextCIN.getText().toString().trim();
        code = editTextCode.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_LONG ).show();

                            gotoprofile();

                        }else{
                            Toast.makeText(LoginActivity.this,"CIN ou CODE Incorrect Vérifiez vos identifiant",Toast.LENGTH_LONG ).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Un Problème De Connexion Est Survenu ,Veuillez Vérifier Votre Connexion",Toast.LENGTH_LONG ).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                try {
                    Log.e("cin_encrypted",crypt.bytesToHex(crypt.encrypt(cin)));
                    Log.e("code_encrypted",crypt.bytesToHex(crypt.encrypt(code)));
                    map.put(KEY_CIN,crypt.bytesToHex(crypt.encrypt(cin)));
                    map.put(KEY_CODE,crypt.bytesToHex(crypt.encrypt(code)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void gotoprofile(){
        Intent intent = new Intent(LoginActivity.this,Loading.class);
        Data.idelecteurs = code;

        LoginActivity.this.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        userLogin();
    }

}
