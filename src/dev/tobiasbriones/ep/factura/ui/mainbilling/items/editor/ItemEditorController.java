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
import dev.tobiasbriones.ep.factura.ui.core.MvcController;

import static dev.tobiasbriones.ep.factura.ui.mainbilling.items.editor.ItemEditor.Output;

final class ItemEditorController extends MvcController<ItemEditorView, Output> implements Output {

    private ItemEditorView view;

    ItemEditorController() {
        super();
        this.view = null;
    }

    @Override
    public ItemEditorView getView() {
        return view;
    }

    @Override
    public void setView(ItemEditorView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

    @Override
    public void onDelete(BasketItem item) {
        getOutput().ifPresent(output -> output.onDelete(item));
        view.onDestroy();
    }

    @Override
    public void onUpdateQuantity(BasketItem item, int quantity) {
        getOutput().ifPresent(output -> output.onUpdateQuantity(item, quantity));
        view.onDestroy();
    }

}
