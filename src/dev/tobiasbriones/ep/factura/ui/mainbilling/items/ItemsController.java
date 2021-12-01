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

package dev.tobiasbriones.ep.factura.ui.mainbilling.items;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketItemModel;
import dev.tobiasbriones.ep.factura.ui.core.MvcController;
import dev.tobiasbriones.ep.factura.ui.mainbilling.items.editor.ItemEditor;

final class ItemsController extends MvcController<ItemsView, Items.Output> implements ItemEditor.Output {
    private final BasketModel basket;
    private ItemsView view;

    ItemsController(BasketModel basket) {
        super();
        this.basket = basket;
        this.view = null;
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
        onItemUpdated(item);
    }

    @Override
    public void onUpdateQuantity(BasketItem item, int quantity) {
        item.setQuantity(quantity);
        onItemUpdated(item);
    }

    private void onItemUpdated(BasketItemModel item) {
        getOutput().ifPresent(output -> output.onItemUpdated(item));
    }
}
