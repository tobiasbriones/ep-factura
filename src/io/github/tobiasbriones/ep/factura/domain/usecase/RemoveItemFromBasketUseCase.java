/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.usecase;

import io.github.tobiasbriones.ep.factura.domain.model.basket.Basket;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;

public final class RemoveItemFromBasketUseCase {

    private final Basket basket;

    public RemoveItemFromBasketUseCase(Basket basket) {
        this.basket = basket;
    }

    public void execute(BasketItem item) {
        basket.remove(item);
    }

}
