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

package io.github.tobiasbriones.ep.factura.domain.model.bill;

import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Bill implements BillModel {

    private final List<BasketItem> items;
    private Customer customer;
    private String rtn;
    private LocalDateTime date;
    private double subtotal;
    private double isv;
    private double total;
    private int totalItems;

    public Bill() {
        this(5);
    }

    public Bill(int estimatedNumberOfItems) {
        this.items = new ArrayList<>(estimatedNumberOfItems);

        init();
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer value) {
        this.customer = value;
    }

    @Override
    public String getRtn() {
        return rtn;
    }

    @Override
    public void setRtn(String value) {
        rtn = value;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDateTime value) {
        date = value;
    }

    @Override
    public void addItem(BasketItem item) {
        items.add(item);
        subtotal += item.getAmount();
        isv += item.getIsv();
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
               "totalItems=" + totalItems +
               "]";
    }

    private void init() {
        items.clear();
        customer = new Customer();
        rtn = "";
        date = LocalDateTime.now();
        subtotal = 0.0d;
        isv = 0.0d;
        total = 0.0d;
        totalItems = 0;
    }

}
