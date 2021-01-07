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
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketList;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.MainBillingWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class Main implements MainBillingWindow.Controller {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ignore) {
        }
        SwingUtilities.invokeLater(Main::new);
    }

    private final ProductDao productDao;
    private final BasketModel basket;
    private final MainBillingWindow mw;
    private final List<Bill> bills;

    private Main() {
        this.productDao = new InMemoryProductDao();
        this.basket = new BasketList();
        this.mw = new MainBillingWindow(this);
        this.bills = new ArrayList<>();
    }

    @Override
    public ProductDao getProductDao() {
        return productDao;
    }

    @Override
    public BasketModel getBasket() {
        return basket;
    }

    @Override
    public void pushToBasket(ProductModel product) {
        basket.pushProduct(product);
    }

    @Override
    public void save(Bill bill) {
        bills.add(bill);
    }

}
