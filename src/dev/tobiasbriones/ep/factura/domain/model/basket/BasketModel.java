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

import dev.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import java.util.Optional;

public interface BasketModel extends StreamableBasketItems {
    int size();

    void push(BasketItem item);

    boolean remove(BasketItem item);

    default BasketSummaryModel computeSummary() {
        return summary(this);
    }

    default void pushProduct(ProductModel product) {
        findAnyProduct(this, product).ifPresentOrElse(
            BasketItem::incrementQuantity,
            () -> push(new BasketItem(product))
        );
    }

    static BasketSummaryModel summary(StreamableBasketItems items) {
        // This Holder example is in Java 11. Use Java Record in Java 17+.
        final class Holder {
            private final double isv;
            private final double total;

            private Holder() { this(0.0, 0.0); }

            private Holder(Holder h1, Holder h2) { this(h1.isv() + h2.isv(), h1.total() + h2.total()); }

            private Holder(double isv, double total) {
                this.isv = isv;
                this.total = total;
            }

            private double isv() { return isv; }

            private double total() { return total; }

            private BasketSummaryModel toBasketSummary() {
                return new BasketSummary(isv, total);
            }
        }
        final var identity = new Holder();
        return items.stream()
                    .map(item -> new Holder(item.getIsv(), item.getTotal()))
                    .reduce(identity, Holder::new)
                    .toBasketSummary();
    }

    static Optional<BasketItem> findAnyProduct(StreamableBasketItems items, ProductModel product) {
        return items.stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findAny();
    }
}
