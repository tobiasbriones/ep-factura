/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import java.util.Iterator;

public interface Basket {

    int size();

    void push(BasketItem basketItem);

    void remove(BasketItem basketItem);

    Iterator<BasketItem> iterator();

}
