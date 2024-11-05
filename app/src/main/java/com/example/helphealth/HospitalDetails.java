package com.example.helphealth;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class HospitalDetails {
    private static final String DELIMITER = "==";
    private static HospitalDetails instance;
    LinkedList<String> HospitalName = new LinkedList<>();
    LinkedList<String> HospitalDescription = new LinkedList<>();
    LinkedList<String> HospitalLocation = new LinkedList<>();

    private HospitalDetails() {}

    public static HospitalDetails getInstance() {
        if (instance == null) {
            instance = new HospitalDetails();
        }
        return instance;
    }

    public boolean loadData(Context context) {
        try {
            FileInputStream fis = context.openFileInput("hospital_data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);


                if (parts != null && parts.length >= 3) {
                    HospitalName.add(parts[0]);
                    HospitalDescription.add(parts[1]);
                    HospitalLocation.add(parts[2]);
                }
            }

            reader.close();
            return true;
        } catch (IOException e) {

            try {
                InputStream is = context.getAssets().open("hospital_data.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(DELIMITER);


                    if (parts != null && parts.length >= 3) {
                        HospitalName.add(parts[0]);
                        HospitalDescription.add(parts[1]);
                        HospitalLocation.add(parts[2]);
                    }
                }

                reader.close();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    public void saveData(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("hospital_data.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            for (int i = 0; i < HospitalName.size(); i++) {

                String line = HospitalName.get(i) + DELIMITER + HospitalDescription.get(i) + DELIMITER + HospitalLocation.get(i);
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inputData() {

    }


    public boolean removeHospital(String name) {
        int index = HospitalName.indexOf(name);
        Log.d("HospitalDetails", "Trying to remove hospital: " + name + " at index: " + index + ", HospitalName size: " + HospitalName.size());

        for (int i = 0; i < HospitalName.size(); i++) {
            Log.d("HospitalDetails", "Hospital at index " + i + ": " + HospitalName.get(i));
        }

        if (index != -1) {
            HospitalName.remove(index);
            HospitalDescription.remove(index);
            HospitalLocation.remove(index);
            Log.d("HospitalDetails", "Hospital removed successfully, new size: " + HospitalName.size());
            return true;
        } else {
            Log.d("HospitalDetails", "Hospital not found");
            return false;
        }
    }

}
