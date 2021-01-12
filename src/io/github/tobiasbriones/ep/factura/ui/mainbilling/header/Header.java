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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.header;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.bill.BillMutator;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerNameAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerNameMutator;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public final class Header implements SwingComponent<JPanel> {

    @FunctionalInterface
    public interface Output {

        void onAddProduct(ProductModel product);

    }

    interface View extends CustomerNameAccessor, CustomerNameMutator {

        ProductModel getProduct();

        void setProduct(ProductModel value);

        String getRtn();

        void setRtn(String value);

        void setProductList(List<? extends ProductModel> products);

        LocalDate getDate();

        void setDate(LocalDate value);

    }

    public static Header newInstance(ProductDao productDao) {
        final var component = new Header(productDao);

        component.init();
        return component;
    }

    private final HeaderController controller;
    private final HeaderView view;

    private Header(ProductDao productDao) {
        super();
        this.controller = new HeaderController(productDao);
        this.view = new HeaderView(controller);
    }

    @Override
    public JPanel getViewComponent() {
        return view.getViewComponent();
    }

    public void setOutput(Header.Output output) {
        controller.setOutput(output);
    }

    public void onSetBill(BillMutator bill) {
        final LocalDate viewDate = view.getDate();
        final LocalDateTime date = LocalDateTime.of(viewDate, LocalTime.now());
        final var customer = new Customer();

        customer.setName(view.getFirstName());
        customer.setSurname(view.getSurname());

        bill.setDate(date);
        bill.setRtn(view.getRtn());
        bill.setCustomer(customer);
    }

    public void onCustomerUpdated(CustomerNameAccessor accessor) {
        view.update(accessor);
    }

    private void init() {
        view.init();
        initController();
    }

    private void initController() {
        controller.setView(view);
        controller.init();
    }

}
