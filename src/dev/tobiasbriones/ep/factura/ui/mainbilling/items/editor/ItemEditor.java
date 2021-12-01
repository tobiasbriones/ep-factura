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

package dev.tobiasbriones.ep.factura.ui.mainbilling.items.editor;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import dev.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

public final class ItemEditor implements SwingComponent<JDialog> {

    public interface Output {

        void onDelete(BasketItem item);

        void onUpdateQuantity(BasketItem item, int quantity);

    }

    public static ItemEditor newInstance(BasketItem basketItem) {
        final var component = new ItemEditor(basketItem);

        component.init();
        return component;
    }

    private final ItemEditorController controller;
    private final ItemEditorView view;

    private ItemEditor(BasketItem item) {
        this.controller = new ItemEditorController();
        this.view = new ItemEditorView(controller, item);
    }

    @Override
    public JDialog getViewComponent() {
        return view.getViewComponent();
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
