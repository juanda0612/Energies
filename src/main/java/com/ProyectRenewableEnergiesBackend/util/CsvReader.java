package com.ProyectRenewableEnergiesBackend.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<String[]> getData(String filePath) {
        List<String[]> energyCountries = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br;

        String linea;

        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            String[] datos;

            while ((linea = br.readLine()) != null) {
                datos = linea.split(",");
                energyCountries.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return energyCountries;
    }
}