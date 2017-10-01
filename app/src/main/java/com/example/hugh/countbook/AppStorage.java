/*
 * AppStorage.java
 *
 * Version: 1.0
 *
 * Date: 2017-10-01
 *
 * Author: Hugh Craig
 *
 * Copyright (c) 2017. CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify
 * this code under terms and conditions of the Code of Student Behavior at the University of Alberta. You may find a
 * copy of the license in th project. Otherwise please contact hdc@ualberta.ca
 */
package com.example.hugh.countbook;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class AppStorage {
    private AppStorage() {} // Simulate a static class for storage
    private static final String STORAGE_FILE = "file.sav";

    public static ArrayList<Counter> loadFromFile(Context context){
        ArrayList<Counter> counterList;
        try {
            FileInputStream fis = context.openFileInput(STORAGE_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);
            if(counterList == null){
                return counterList = new ArrayList<Counter>();
            }
        } catch (FileNotFoundException e) {
             counterList = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return counterList;
    }

    public static void saveInFile(ArrayList<Counter> counterList, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(STORAGE_FILE,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter (new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
