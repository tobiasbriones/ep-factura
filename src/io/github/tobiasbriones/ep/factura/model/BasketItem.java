/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model;

public final class BasketItem {

    private final Product product;
    private int quantity;

    public BasketItem(Product product, int quantity) {
        this.product = product;
        setQuantity(quantity);
    }

    public BasketItem(Product product) {
        this(product, 0);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int value) {
        if (value < 0) {
            throw new RuntimeException("Quantity can't be negative, quantity: " + value);
        }
        this.quantity = value;
    }

    public double getAmount() {
        return product.price * quantity;
    }

    public double getISV() {
        return product.isv() * quantity;
    }

    public double getTotal() {
        return product.total() * quantity;
    }

    public void addNew() {
        quantity++;
    }

}
