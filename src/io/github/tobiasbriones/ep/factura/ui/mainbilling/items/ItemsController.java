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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.items;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.ui.core.MvcController;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.editor.ItemEditor;

import java.util.Iterator;

final class ItemsController extends MvcController<ItemsView, Void> implements ItemEditor.Output {

    private final BasketModel basket;
    private ItemsView view;

    ItemsController(BasketModel basket) {
        super();
        this.basket = basket;
        this.view = null;
    }

    Iterator<BasketItem> getItems() {
        return basket.iterator();
    }

    @Override
    public ItemsView getView() {
        return view;
    }

    @Override
    public void setView(ItemsView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

    @Override
    public void onDelete(BasketItem item) {
        basket.remove(item);
        view.update();
    }

    @Override
    public void onUpdateQuantity(BasketItem item, int quantity) {
        item.setQuantity(quantity);
        view.update();
    }

}
