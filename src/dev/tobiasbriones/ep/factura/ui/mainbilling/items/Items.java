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

package dev.tobiasbriones.ep.factura.ui.mainbilling.items;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import dev.tobiasbriones.ep.factura.ui.core.SwingComponent;
import dev.tobiasbriones.ep.factura.ui.core.rx.Observable;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketItemModel;

import javax.swing.*;

public final class Items implements SwingComponent<JScrollPane> {

    @FunctionalInterface
    public interface Output {

        void onItemUpdated(BasketItemModel item);

    }

    public static Items newInstance(BasketModel basket) {
        final var component = new Items(basket);

        component.init();
        return component;
    }

    private final ItemsController controller;
    private final ItemsView view;

    private Items(BasketModel basket) {
        this.controller = new ItemsController(basket);
        this.view = new ItemsView(controller, basket);
    }

    @Override
    public JScrollPane getViewComponent() {
        return view.getViewComponent();
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
