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

package dev.tobiasbriones.ep.factura.domain.usecase;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketModel;

public final class RemoveItemFromBasketUseCase {

    private final BasketModel basket;

    public RemoveItemFromBasketUseCase(BasketModel basket) {
        this.basket = basket;
    }

    public void execute(BasketItem item) {
        basket.remove(item);
    }

}
