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

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketList;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketSummaryModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;

import java.time.LocalDateTime;

public final class Bill implements BillModel {

    private BasketModel basket;
    private BasketSummaryModel basketSummary;
    private CustomerModel customer;
    private String rtn;
    private LocalDateTime date;

    public Bill() {
        init();
    }

    @Override
    public BasketModel getBasket() {
        return basket;
    }

    @Override
    public void setBasket(BasketModel value) {
        basket = value;
        basketSummary = basket.computeSummary();
    }

    @Override
    public CustomerModel getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(CustomerModel value) {
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
    public double getSubtotal() {
        return basketSummary.getSubtotal();
    }

    @Override
    public double getIsv() {
        return basketSummary.getIsv();
    }

    @Override
    public double getTotal() {
        return basketSummary.getTotal();
    }

    @Override
    public void clear() {
        init();
    }

    @Override
    public String toString() {
        return "Bill[" +
               "basket=" + basket + ", " +
               "basketSummary=" + basketSummary + ", " +
               "customer=" + customer + ", " +
               "rtn=" + rtn + ", " +
               "date=" + date +
               "]";
    }

    private void init() {
        basket = new BasketList();
        basketSummary = basket.computeSummary();
        customer = new Customer();
        rtn = "";
        date = LocalDateTime.now();
    }

}
