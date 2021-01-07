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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.items;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;
import io.github.tobiasbriones.ep.factura.ui.core.rx.Observable;

import javax.swing.*;

public final class ItemsComponent implements SwingComponent<JPanel> {

    public static ItemsComponent newInstance(BasketModel basket) {
        final var component = new ItemsComponent(basket);

        component.init();
        return component;
    }

    private final ItemsController controller;
    private final ItemsView view;

    private ItemsComponent(BasketModel basket) {
        this.controller = new ItemsController(basket);
        this.view = new ItemsView(controller, basket);
    }

    @Override
    public JPanel getComponent() {
        return view.getComponent();
    }

    public void setOutput(Items.Output output) {
        controller.setOutput(output);
    }

    public void subscribe(Observable observable) {
        observable.subscribe(view);
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
