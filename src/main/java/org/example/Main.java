package org.example;

import javax.swing.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter file path: ");
        String sourcePath = sc.nextLine();
        String[] products = {"TV LED,1290.99,1", "VIDEO GAME CHAIR,350.50,3", "IPHONE X,900.00,2", "SAMSUNG GALAXY 9,850.00,2"};
        List<Product> list = new ArrayList<>();

        // Creating a  products file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(sourcePath))) {
            for (String product : products) {
                bw.write(product);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading file content and splitting to a product list
        try (BufferedReader br = new BufferedReader(new FileReader(sourcePath))) {

            String itemCSV = br.readLine();
            while (itemCSV != null) {

                String[] fields = itemCSV.split(",");
                String name = fields[0];
                Double price = Double.valueOf(fields[1]);
                Integer qtd = Integer.valueOf(fields[2]);

                list.add(new Product(name, price, qtd));

                itemCSV = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creating new folder to output total price product
        File sourceFolder = new File(sourcePath);
        String sourceFolderStr = sourceFolder.getParent();
        boolean success = new File(sourceFolderStr + "\\out").mkdir();

        // Creating output file for total price
        String targetFileStr = sourceFolderStr + "\\out\\Summary.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
            for (Product product : list ) {
                bw.write(String.format("%s, %.2f",product.getName(),product.total()));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        sc.close();
    }

}