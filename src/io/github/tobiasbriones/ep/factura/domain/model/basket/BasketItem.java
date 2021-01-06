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

import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import java.util.Objects;

public final class BasketItem implements BasketItemModel {

    public static final int DEF_QUANTITY = 1;
    private final ProductModel product;
    private int quantity;

    public BasketItem(ProductModel product) {
        this(product, DEF_QUANTITY);
    }

    public BasketItem(ProductModel product, int quantity) {
        if (product == null) {
            final var msg = "The product can't be null";
            throw new RuntimeException(msg);
        }
        this.product = product;
        setQuantity(quantity);
    }

    @Override
    public ProductModel getProduct() {
        return product;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int value) {
        if (value < 0) {
            throw new RuntimeException("Quantity can't be negative, quantity: " + value);
        }
        this.quantity = value;
    }

    @Override
    public void incrementQuantity() {
        quantity++;
    }

    @Override
    public double getAmount() {
        return product.getPrice() * ((double) quantity);
    }

    @Override
    public double getIsv() {
        return product.getIsv() * ((double) quantity);
    }

    @Override
    public double getTotal() {
        return product.getTotal() * ((double) quantity);
    }

    @Override
    public String toString() {
        return "BasketItem[" +
               "product=" + product + ", " +
               "quantity=" + quantity +
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
        final BasketItemAccessor basketItem = (BasketItemAccessor) obj;
        return product.equals(basketItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

}
