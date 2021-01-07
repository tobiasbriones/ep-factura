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
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

public final class CustomerCreationDialogComponent implements SwingComponent<JDialog> {

    public static CustomerCreationDialogComponent newInstance(CityDao cityDao, CommunityDao communityDao) {
        final var component = new CustomerCreationDialogComponent(cityDao, communityDao);

        component.init();
        return component;
    }

    private final CustomerCreationDialogController controller;
    private final CustomerCreationDialogView view;

    private CustomerCreationDialogComponent(CityDao cityDao, CommunityDao communityDao) {
        this.controller = new CustomerCreationDialogController(cityDao, communityDao);
        this.view = new CustomerCreationDialogView(controller);
    }

    @Override
    public JDialog getComponent() {
        return view.getComponent();
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
