/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.basket;

import io.github.tobiasbriones.ep.factura.model.product.Product;

import java.util.Objects;

public final class BasketItem implements BasketItemModel {

    private final Product product;
    private int quantity;

    public BasketItem(Product product, int quantity) {
        if (product == null) {
            final var msg = "The product can't be null";
            throw new RuntimeException(msg);
        }
        this.product = product;
        setQuantity(quantity);
    }

    public BasketItem(Product product) {
        this(product, 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
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
    public String toString() {
        return "BasketItem[" +
               "product=" + product + ", " +
               "quantity=" + quantity +
               "]";
    }

    @Override
    public Product getProduct() {
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
    public double getISV() {
        return product.getIsv() * ((double) quantity);
    }

    @Override
    public double getTotal() {
        return product.getTotal() * ((double) quantity);
    }

}
