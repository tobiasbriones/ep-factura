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

    public static MainBillingWindow newInstance(BasketModel basket, ProductDao productDao) {
        final var component = new MainBillingWindow(basket, productDao);

        component.init();
        return component;
    }

    private final MainBillingController controller;
    private final MainBillingView view;

    private MainBillingWindow(BasketModel basket, ProductDao productDao) {
        this.controller = new MainBillingController(basket, productDao);
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
