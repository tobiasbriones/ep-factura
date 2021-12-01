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

package dev.tobiasbriones.ep.factura.domain.model.basket;

public abstract class AbstractBasket implements BasketModel {
    AbstractBasket() {}

    @Override
    public final void push(BasketItem item) {
        if (!contains(item)) {
            pushItem(item);
        }
    }

    /**
     * Returns {@code true} if and only if the given basket item is already
     * added to this basket.
     *
     * @param item basket item to check
     *
     * @return {@code true} if and only if the given basket item is already
     * added to this basket
     */
    protected abstract boolean contains(BasketItem item);

    /**
     * Pushes the given basket item into this basket.
     *
     * @param item basket item to push
     */
    protected abstract void pushItem(BasketItem item);
}
