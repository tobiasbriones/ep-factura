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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.summary;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;
import io.github.tobiasbriones.ep.factura.ui.core.rx.Observable;

import javax.swing.*;

public final class Summary implements SwingComponent<JPanel> {

    public static Summary newInstance(BasketModel basket) {
        final var component = new Summary(basket);

        component.init();
        return component;
    }

    private final SummaryController controller;
    private final SummaryView view;

    private Summary(BasketModel basket) {
        this.controller = new SummaryController();
        this.view = new SummaryView(controller, basket);
    }

    @Override
    public JPanel getViewComponent() {
        return view.getViewComponent();
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
