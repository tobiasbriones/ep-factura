/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura;

import io.github.tobiasbriones.ep.factura.model.Bill;
import io.github.tobiasbriones.ep.factura.model.Product;
import io.github.tobiasbriones.ep.factura.ui.MainWindow;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Main implements MainWindow.Controller {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ignore) {}
        SwingUtilities.invokeLater(Main::new);
    }

    private static List<String> read(String file) throws IOException {
        final List<String> list = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
    private final MainWindow mw;
    private final List<Bill> bills;

    private Main() {
        final List<Product> products = loadProducts();
        this.mw = new MainWindow(this, products);
        this.bills = new ArrayList<>();
    }

    @Override
    public List<String> getCities() {
        try {
            return read("cities");
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(mw, e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCommunities() {
        try {
            return read("communities");
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(mw, e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(Bill bill) {
        bills.add(bill);
    }

    private List<Product> loadProducts() {
        final List<Product> products = new ArrayList<>();

        products.add(new Product(120, "Product 1", 45));
        products.add(new Product(140, "Product 2", 446.99));
        products.add(new Product(487, "Product 3", 0.25));
        products.add(new Product(521, "Product 4", 42));
        products.add(new Product(785, "Product 5", 7));
        products.add(new Product(122, "Product 6", 10.33));
        products.add(new Product(479, "Product 7", 785.50));
        products.add(new Product(642, "Product 8", 12.99));
        return products;
    }

}
