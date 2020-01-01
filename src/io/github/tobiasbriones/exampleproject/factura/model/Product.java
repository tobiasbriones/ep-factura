/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.exampleproject.factura.model;

public final class Product {
    
    public static final int ISV_PERCENTAGE = 15;
    public final int code;
    public final String description;
    public final double price;
    
    public Product(int code, String description, double price) {
        this.code = code;
        this.description = description;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return code + " - " + description + " - $" + price;
    }
    
    public double isv() {
        return price * ((float) ISV_PERCENTAGE / 100.0F);
    }
    
    public double total() {
        return price + isv();
    }
    
}
