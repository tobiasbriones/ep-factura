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

package io.github.tobiasbriones.ep.factura.domain.model.product;

import java.util.Objects;

import static io.github.tobiasbriones.ep.factura.domain.model.product.ProductConstrains.*;

final class ProductRecord implements Product {

    private final Integer code;
    private final String description;
    private final Double price;

    ProductRecord(int code, String description, double price) {
        this.code = code;
        this.description = description != null ? description : "";
        this.price = price;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getIsv() {
        final double isvFactor = ((double) DEF_ISV_PERCENTAGE) / 100.0d;
        return price * isvFactor;
    }

    @Override
    public double getTotal() {
        return price + getIsv();
    }

    @Override
    public String toString() {
        return "Product[" +
               "code=" + code + ", " +
               "description=" + description + ", " +
               "price=" + price +
               "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Product product = (Product) obj;
        return code == product.getCode() &&
               Double.compare(product.getPrice(), price) == 0 &&
               description.equals(product.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, price);
    }

}
