/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import io.github.tobiasbriones.ep.factura.domain.model.product.Product;

public interface BasketItemAccessor {

    Product getProduct();

    int getQuantity();

    double getAmount();

    double getISV();

    double getTotal();

}
