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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.items.editor;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

public final class ItemEditorComponent implements SwingComponent<JDialog> {

    public static ItemEditorComponent newInstance(BasketItem basketItem) {
        final var component = new ItemEditorComponent(basketItem);

        component.init();
        return component;
    }

    private final ItemEditorController controller;
    private final ItemEditorView view;

    private ItemEditorComponent(BasketItem item) {
        this.controller = new ItemEditorController();
        this.view = new ItemEditorView(controller, item);
    }

    @Override
    public JDialog getComponent() {
        return view.getComponent();
    }

    public void setOutput(ItemEditor.Output output) {
        controller.setOutput(output);
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
