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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.customer;

import io.github.tobiasbriones.ep.factura.data.CityDao;
import io.github.tobiasbriones.ep.factura.data.CommunityDao;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

public final class CustomerCreationDialog implements SwingComponent<JDialog> {

    @FunctionalInterface
    public interface Output {

        void onCreateCustomer(Customer customer);

    }

    public static CustomerCreationDialog newInstance(CityDao cityDao, CommunityDao communityDao) {
        final var component = new CustomerCreationDialog(cityDao, communityDao);

        component.init();
        return component;
    }

    private final CustomerCreationDialogController controller;
    private final CustomerCreationDialogView view;

    private CustomerCreationDialog(CityDao cityDao, CommunityDao communityDao) {
        this.controller = new CustomerCreationDialogController(cityDao, communityDao);
        this.view = new CustomerCreationDialogView(controller);
    }

    @Override
    public JDialog getViewComponent() {
        return view.getViewComponent();
    }

    public void setOutput(CustomerCreationDialog.Output output) {
        controller.setOutput(output);
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
