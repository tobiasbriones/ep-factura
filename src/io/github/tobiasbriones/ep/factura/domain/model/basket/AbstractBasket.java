/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.basket;

public abstract class AbstractBasket implements Basket {

    protected AbstractBasket() {}

    @Override
    public final void push(BasketItem basketItem) {
        if (!contains(basketItem)) {
            pushItem(basketItem);
        }
    }

    /**
     * Returns {@code true} if and only if the given basket item is already
     * added to this basket.
     *
     * @param basketItem basket item to check
     *
     * @return {@code true} if and only if the given basket item is already
     * added to this basket
     */
    protected abstract boolean contains(BasketItem basketItem);

    /**
     * Pushes the given basket item into this basket.
     *
     * @param basketItem basket item to push
     */
    protected abstract void pushItem(BasketItem basketItem);

}
