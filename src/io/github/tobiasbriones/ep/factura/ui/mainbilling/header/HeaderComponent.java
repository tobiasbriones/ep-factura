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

public final class HeaderComponent implements SwingComponent<JPanel> {

    public static HeaderComponent newInstance(ProductDao productDao) {
        final var component = new HeaderComponent(productDao);

        component.init();
        return component;
    }

    private final HeaderController controller;
    private final HeaderView view;

    private HeaderComponent(ProductDao productDao) {
        super();
        this.controller = new HeaderController(productDao);
        this.view = new HeaderView(controller);
    }

    @Override
    public JPanel getViewComponent() {
        return view.getViewComponent();
    }

    public void setOutput(Header.Output output) {
        controller.setOutput(output);
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
