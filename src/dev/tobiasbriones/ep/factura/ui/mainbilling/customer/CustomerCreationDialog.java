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

package dev.tobiasbriones.ep.factura.ui.mainbilling.customer;

import dev.tobiasbriones.ep.factura.data.CityDao;
import dev.tobiasbriones.ep.factura.data.CommunityDao;
import dev.tobiasbriones.ep.factura.ui.core.SwingComponent;
import dev.tobiasbriones.ep.factura.domain.model.customer.Customer;
import dev.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;

import javax.swing.*;

public final class CustomerCreationDialog implements SwingComponent<JDialog> {

    @FunctionalInterface
    public interface Output {

        void onCustomerCreated(Customer customer);

    }

    public static CustomerCreationDialog newInstance(
        CustomerModel customer,
        CityDao cityDao,
        CommunityDao communityDao
    ) {
        final var component = new CustomerCreationDialog(customer, cityDao, communityDao);

        component.init();
        return component;
    }

    private final CustomerCreationController controller;
    private final CustomerCreationView view;

    private CustomerCreationDialog(CustomerModel customer, CityDao cityDao, CommunityDao communityDao) {
        this.controller = new CustomerCreationController(customer, cityDao, communityDao);
        this.view = new CustomerCreationView(controller);
    }

    @Override
    public JDialog getViewComponent() {
        return view.getViewComponent();
    }

    public void setOutput(CustomerCreationDialog.Output output) {
        controller.setOutput(output);
    }

    public void show() {
        view.show();
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
