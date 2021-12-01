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

package dev.tobiasbriones.ep.factura.ui.mainbilling.print;

import dev.tobiasbriones.ep.factura.ui.core.MvcController;

final class PrintController extends MvcController<PrintView, Print.Output> {

    private PrintView view;

    PrintController() {
        super();
        this.view = null;
    }

    @Override
    public PrintView getView() {
        return view;
    }

    @Override
    public void setView(PrintView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

    void onPrintButtonClick() {
        final boolean mustCreateNewCustomer = view.isCreateNewCustomerSelected();

        getOutput().ifPresent(output -> {
            if (mustCreateNewCustomer) {
                output.onPrintWithNewCustomer();
            }
            else {
                output.onPrint();
            }
        });
    }

}
