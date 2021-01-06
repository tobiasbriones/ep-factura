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

package io.github.tobiasbriones.ep.factura.ui;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.core.rx.AnyObservable;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.HeaderComponent;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.ItemsComponent;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.Summary;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.SummaryComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class MainWindow extends JFrame implements Header.Output, Summary.Output {

    //                                                                                            //
    //                                                                                            //
    //                                           CLASS                                            //
    //                                                                                            //
    //                                                                                            //

    public interface Controller {

        ProductDao getProductDao();

        BasketModel getBasket();

        void pushToBasket(ProductModel product);

        void save(Bill bill);

    }

    //                                                                                            //
    //                                                                                            //
    //                                          INSTANCE                                          //
    //                                                                                            //
    //                                                                                            //

    private final Controller controller;
    private final AnyObservable basketObservable;

    public MainWindow(Controller controller) {
        super("Factura");
        this.controller = controller;
        this.basketObservable = new AnyObservable();

        init();
    }

    @Override
    public void onAddProduct(ProductModel product) {
        controller.pushToBasket(product);
        basketObservable.notifyObservers();
    }

    @Override
    public void onPrint() {

    }

    @Override
    public void onPrintWithNewCustomer() {

    }

    private void init() {
        final var productDao = controller.getProductDao();
        final var basket = controller.getBasket();
        final var panel = new JPanel();
        final var inputPanel = HeaderComponent.newInstance(productDao, this).getComponent();
        final var scroll = ItemsComponent.newInstance(basket, basketObservable).getComponent();
        final var summaryPanel = SummaryComponent.newInstance(basket, basketObservable, this).getComponent();

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(inputPanel, BorderLayout.PAGE_START);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(summaryPanel, BorderLayout.PAGE_END);
        getContentPane().add(panel);

        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

}
