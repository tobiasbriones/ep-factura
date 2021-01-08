/*
 * Copyright (c) 2020 Tobias Briones. All rights reserved.
 *
 * SPDX-License-Identifier: MIT
 *
 * This file is part of Example Project: Factura.
 *
 * This source code is licensed under the MIT License found in the
 * LICENSE file in the root directory of this source tree or at
 * https://opensource.org/licenses/MIT.
 */

package io.github.tobiasbriones.ep.factura.ui.mainbilling;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

public final class MainBillingWindow implements SwingComponent<JFrame> {

    public static MainBillingWindow newInstance(DependencyRepository dependency) {
        final var component = new MainBillingWindow(dependency);

        component.init();
        return component;
    }

    // Again, this would be a record class in Java 17+
    public static final class DependencyRepository {
        private final BasketModel basket;
        private final ProductDao productDao;

        public DependencyRepository(BasketModel basket, ProductDao productDao) {
            this.basket = basket;
            this.productDao = productDao;
        }

        BasketModel basket() { return basket; }

        ProductDao productDao() { return productDao; }
    }

    private final MainBillingController controller;
    private final MainBillingView view;

    private MainBillingWindow(DependencyRepository dependency) {
        this.controller = new MainBillingController(dependency);
        this.view = new MainBillingView(controller);
    }

    @Override
    public JFrame getViewComponent() {
        return view.getViewComponent();
    }

    public void show() {
        view.show();
    }

    private void init() {
        view.init();
        initController();
    }

    private void initController() {
        controller.setView(view);
        controller.init();
    }

}
