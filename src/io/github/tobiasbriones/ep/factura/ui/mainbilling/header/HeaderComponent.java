/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.mainbilling.header;

import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

// Simple example of creating a component just to exemplify, checkout the order!
public final class HeaderComponent {

    public static SwingComponent<JPanel> newInstance() {
        final var controller = new HeaderController();
        final var view = new HeaderView(controller);

        init(view, controller);
        return new SwingComponent<>(view);
    }

    private static void init(HeaderView view, HeaderController controller) {
        initView(view);
        initController(view, controller);
    }

    private static void initView(HeaderView view) {
        view.init();
    }

    private static void initController(HeaderView view, HeaderController controller) {
        controller.setView(view);
        controller.init();
    }

}
