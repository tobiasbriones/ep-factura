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

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import java.util.Iterator;

public interface Basket {

    static BasketSummaryModel summary(Basket basket) {
        final Iterator<BasketItem> iterator = basket.iterator();
        double isv = 0.0d;
        double total = 0.0d;

        while (iterator.hasNext()) {
            final BasketItem current = iterator.next();

            isv += current.getIsv();
            total += current.getTotal();
        }
        return new BasketSummary(isv, total);
    }

    int size();

    void push(BasketItem item);

    boolean remove(BasketItem item);

    Iterator<BasketItem> iterator();

    default BasketSummaryModel computeSummary() {
        return summary(this);
    }

}
