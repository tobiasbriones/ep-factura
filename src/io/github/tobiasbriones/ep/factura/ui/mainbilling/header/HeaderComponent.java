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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.header;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

// Simple example of creating a component just to exemplify, checkout the order!
public final class HeaderComponent {

    public static SwingComponent<JPanel> newInstance(ProductDao productDao, Header.Output output) {
        final var controller = new HeaderController(productDao);
        final var view = new HeaderView(controller);

        controller.setOutput(output);
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

    private HeaderComponent() {}

}
