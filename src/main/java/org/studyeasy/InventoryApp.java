package org.studyeasy;

import java.io.*;
import java.util.*;

class Product {
    int id;
    String name;
    int quantity;
    double price;

    Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + quantity + "," + price;
    }
}

public class InventoryApp {

    static final String FILE_PATH =
            "C:\\Users\\Shreyash\\OneDrive\\Documents\\Inventory.txt";

    static HashMap<Integer, Product> inventory = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadFromFile();

        while (true) {
            System.out.println("\n--- Inventory Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> viewInventory();
                case 5 -> {
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ADD
    static void addProduct() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        System.out.print("Enter price: ");
        double price = sc.nextDouble();

        inventory.put(id, new Product(id, name, qty, price));
        saveToFile();
        System.out.println("Product added ✅");
    }

    // UPDATE
    static void updateProduct() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();

        Product p = inventory.get(id);
        if (p == null) {
            System.out.println("Product not found");
            return;
        }

        sc.nextLine();
        System.out.print("New name: ");
        p.name = sc.nextLine();

        System.out.print("New quantity: ");
        p.quantity = sc.nextInt();

        System.out.print("New price: ");
        p.price = sc.nextDouble();

        saveToFile();
        System.out.println("Product updated ✅");
    }

    // DELETE
    static void deleteProduct() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        if (inventory.remove(id) != null) {
            saveToFile();
            System.out.println("Product deleted ✅");
        } else {
            System.out.println("Product not found");
        }
    }

    // VIEW
    static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory empty");
            return;
        }

        for (Product p : inventory.values()) {
            System.out.println(
                    "ID: " + p.id +
                            ", Name: " + p.name +
                            ", Qty: " + p.quantity +
                            ", Price: " + p.price
            );
        }
    }

    // SAVE TO FILE
    static void saveToFile() {
        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Product p : inventory.values()) {
                bw.write(p.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }

    // LOAD FROM FILE
    static void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br =
                     new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int qty = Integer.parseInt(data[2]);
                double price = Double.parseDouble(data[3]);

                inventory.put(id, new Product(id, name, qty, price));
            }

        } catch (IOException e) {
            System.out.println("Error loading file");
        }
    }
}
