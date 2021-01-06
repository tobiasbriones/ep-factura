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

// Simple example of creating a component just to exemplify, checkout the order!
public final class ItemsComponent {

    public static SwingComponent<JPanel> newInstance(BasketModel basket, Observable observable) {
        final var controller = new ItemsController(basket);
        final var view = new ItemsView(controller);

        observable.subscribe(view);
        init(view, controller);
        return new SwingComponent<>(view);
    }

    private static void init(ItemsView view, ItemsController controller) {
        initView(view);
        initController(view, controller);
    }

    private static void initView(ItemsView view) {
        view.init();
    }

    private static void initController(ItemsView view, ItemsController controller) {
        controller.setView(view);
        controller.init();
    }

}
