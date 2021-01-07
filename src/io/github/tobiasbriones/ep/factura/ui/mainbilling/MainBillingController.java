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

package io.github.tobiasbriones.ep.factura.ui.mainbilling;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

import javax.swing.*;

final class MainBillingController extends MvcController<MainBillingView, Void> {

    private final MainBillingMediator mediator;
    private MainBillingView view;

    MainBillingController(BasketModel basket, ProductDao productDao) {
        super();
        this.mediator = new MainBillingMediator(basket, productDao);
        this.view = null;
    }

    JPanel getHeaderViewComponent() {
        return mediator.getHeaderViewComponent();
    }

    JPanel getItemsViewComponent() {
        return mediator.getItemsViewComponent();
    }

    JPanel getSummaryViewComponent() {
        return mediator.getSummaryViewComponent();
    }

    JPanel getPrintViewComponent() {
        return mediator.getPrintViewComponent();
    }

    @Override
    public MainBillingView getView() {
        return view;
    }

    @Override
    public void setView(MainBillingView value) {
        view = value;
    }

    @Override
    public void init() {
        mediator.init();
    }

}
