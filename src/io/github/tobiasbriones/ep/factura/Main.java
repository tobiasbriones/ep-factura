/*
 * Copyright (c) 2019-2020 Tobias Briones. All rights reserved.
 *
 * SPDX-License-Identifier: MIT
 *
 * This file is part of Example Project: Factura.
 *
 * This source code is licensed under the MIT License found in the
 * LICENSE file in the root directory of this source tree or at
 * https://opensource.org/licenses/MIT.
 */

package io.github.tobiasbriones.ep.factura;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.database.InMemoryProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.Basket;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketList;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.domain.usecase.AddItemToBasketUseCase;
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
        catch (Exception ignore) {
        }
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

    private final ProductDao productDao;
    private final Basket basket;
    private final MainWindow mw;
    private final List<Bill> bills;

    private Main() {
        this.productDao = new InMemoryProductDao();
        this.basket = new BasketList();
        this.mw = new MainWindow(this);
        this.bills = new ArrayList<>();
    }

    @Override
    public ProductDao getProductDao() {
        return productDao;
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
    public Basket getBasket() {
        return basket;
    }

    @Override
    public void pushToBasket(Product product) {
        final var item = new BasketItem(product, 1);
        final var useCase = new AddItemToBasketUseCase(basket);

        useCase.execute(item);
    }

    @Override
    public void save(Bill bill) {
        bills.add(bill);
    }

}
