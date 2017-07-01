package com.example.shadow.iao_vote;


import java.util.ArrayList;


/**
 * Created by Hamza on 1/27/2017.
 */

public  class Data {

    public static String idelecteurs;
    public static String idelections;
    public static String idcandidats;

    public static ArrayList<String> data = new ArrayList<String>();
    public static ArrayList<String> ids = new ArrayList<String>();
    public static ArrayList<String> date = new ArrayList<String>();

    public static Property p ;



    public static ArrayList<Property> prop = new ArrayList<Property>();
    public static ArrayList<String> idcandidatss = new ArrayList<String>();
    public static int taille = 0;


    public static void destroy()
    {
        data.clear();
        date.clear();
        ids.clear();
        prop.clear();
        idcandidatss.clear();

    }

}
