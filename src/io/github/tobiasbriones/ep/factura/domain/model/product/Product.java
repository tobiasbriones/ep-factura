/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.product;

public interface Product extends ProductAccessor {

    static Product of(int code, String description, double price) {
        return new ProductRecord(code, description, price);
    }

}
