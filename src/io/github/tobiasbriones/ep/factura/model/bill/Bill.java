/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.bill;

import io.github.tobiasbriones.ep.factura.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.model.customer.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Bill implements BillModel {

    private final List<BasketItem> items;
    private Customer customer;
    private String rtn;
    private String date;
    private double subtotal;
    private double isv;
    private double total;
    private int totalItems;

    public Bill(int estimatedNumberOfItems) {
        this.items = new ArrayList<>(estimatedNumberOfItems);

        init();
    }

    public Bill() {
        this(5);
    }

    @Override
    public String toString() {
        return "Bill[" +
               "items=" + items + ", " +
               "customer=" + customer + ", " +
               "rtn=" + rtn + ", " +
               "date=" + date + ", " +
               "subtotal=" + subtotal + ", " +
               "isv=" + isv + ", " +
               "total=" + total + ", " +
               "totalItems=" + totalItems + ", " +
               "]";
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Bill setCustomer(Customer value) {
        this.customer = value;
        return this;
    }

    @Override
    public String getRtn() {
        return rtn;
    }

    @Override
    public Bill setRtn(String value) {
        rtn = value;
        return this;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public Bill setDate(String value) {
        date = value;
        return this;
    }

    @Override
    public void addItem(BasketItem item) {
        items.add(item);
        subtotal += item.getAmount();
        isv += item.getISV();
        total += item.getTotal();
        totalItems += item.getQuantity();
    }

    @Override
    public void clear() {
        init();
    }

    @Override
    public List<BasketItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public double getIsv() {
        return isv;
    }

    @Override
    public double getTotal() {
        return total;
    }

    private void init() {
        items.clear();
        customer = new Customer();
        rtn = "";
        date = "";
        subtotal = 0.0d;
        isv = 0.0d;
        total = 0.0d;
        totalItems = 0;
    }

}
