/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.product;

import java.util.Objects;

public final class Product implements ProductModel {

    public static final int ISV_PERCENTAGE = 15;
    private final Integer code;
    private final String description;
    private final Double price;

    public Product(int code, String description, double price) {
        this.code = code;
        this.description = description != null ? description : "";
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProductModel product = (ProductModel) obj;
        return code == product.getCode() &&
               Double.compare(product.getPrice(), price) == 0 &&
               description.equals(product.getDescription());
    }

    @Override
    public String toString() {
        return "Product[" +
               "code=" + code + ", " +
               "description=" + description + ", " +
               "price=" + price + ", " +
               "]";
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
        final double isvFactor = ((double) ISV_PERCENTAGE) / 100.0d;
        return price * isvFactor;
    }

    @Override
    public double getTotal() {
        return price + getIsv();
    }

}
