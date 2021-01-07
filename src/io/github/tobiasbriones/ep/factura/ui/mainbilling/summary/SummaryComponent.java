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

public final class SummaryComponent implements SwingComponent<JPanel> {

    public static SummaryComponent newInstance(BasketModel basket) {
        final var component = new SummaryComponent(basket);

        component.init();
        return component;
    }

    private final SummaryController controller;
    private final SummaryView view;

    private SummaryComponent(BasketModel basket) {
        this.controller = new SummaryController();
        this.view = new SummaryView(controller, basket);
    }

    @Override
    public JPanel getComponent() {
        return view.getComponent();
    }

    public void setOutput(Summary.Output output) {
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
