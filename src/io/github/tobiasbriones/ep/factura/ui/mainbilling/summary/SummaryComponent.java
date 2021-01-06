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

import io.github.tobiasbriones.ep.factura.domain.model.basket.Basket;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;
import io.github.tobiasbriones.ep.factura.ui.core.rx.Observable;

import javax.swing.*;

public final class SummaryComponent {

    public static SwingComponent<JPanel> newInstance(
        Basket basket,
        Observable observable,
        Summary.Output output
    ) {
        final var controller = new SummaryController();
        final var view = new SummaryView(controller, basket);

        controller.setOutput(output);
        observable.subscribe(view);
        init(view, controller);
        return new SwingComponent<>(view);
    }

    private static void init(SummaryView view, SummaryController controller) {
        initView(view);
        initController(view, controller);
    }

    private static void initView(SummaryView view) {
        view.init();
    }

    private static void initController(SummaryView view, SummaryController controller) {
        controller.setView(view);
        controller.init();
    }

    private SummaryComponent() {
    }

}
