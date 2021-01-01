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

// Simple example of how to create a component
public final class ItemEditorComponent {

    public static SwingComponent<JDialog> newInstance(ItemEditor.Output output, BasketItem item) {
        final var controller = new ItemEditorController();
        final var view = new ItemEditorView(controller, item);

        init(view, controller, output);
        return new SwingComponent<>(view);
    }

    private static void init(
        ItemEditorView view,
        ItemEditorController controller,
        ItemEditor.Output output
    ) {
        initView(view);
        initController(view, controller, output);
    }

    private static void initView(ItemEditorView view) {
        view.init();
    }

    private static void initController(
        ItemEditorView view,
        ItemEditorController controller,
        ItemEditor.Output output
    ) {
        controller.setView(view);
        controller.setOutput(output);
        controller.init();
    }

    private ItemEditorComponent() {}

}
