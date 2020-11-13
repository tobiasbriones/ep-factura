/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.product;

public interface ProductModel {

    int getCode();

    String getDescription();

    double getPrice();

    double getIsv();

    double getTotal();

}