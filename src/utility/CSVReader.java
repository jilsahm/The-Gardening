package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args){

        String csvFile = "csv/csv-test.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] test = line.split(cvsSplitBy);

                for (String i : test){
                	System.out.printf("%s ", i);
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Jacobo hats kaputt gemacht.");
        	//e.printStackTrace();
        }

    }

}
