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

public class CustomerCreationDialogComponent {

    public static SwingComponent<JDialog> newInstance(
        CityDao cityDao,
        CommunityDao communityDao,
        CustomerCreationDialog.Output output
    ) {
        final var controller = new CustomerCreationDialogController(cityDao, communityDao);
        final var view = new CustomerCreationDialogView(controller);

        controller.setOutput(output);
        init(view, controller);
        return new SwingComponent<>(view);
    }

    private static void init(CustomerCreationDialogView view, CustomerCreationDialogController controller) {
        initView(view);
        initController(view, controller);
    }

    private static void initView(CustomerCreationDialogView view) {
        view.init();
    }

    private static void initController(CustomerCreationDialogView view, CustomerCreationDialogController controller) {
        controller.setView(view);
        controller.init();
    }

    private CustomerCreationDialogComponent() {}

}
