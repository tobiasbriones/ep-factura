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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.summary;

import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

final class SummaryController extends MvcController<SummaryView, Void> {

    private SummaryView view;

    SummaryController() {
        super();
        this.view = null;
    }

    @Override
    public SummaryView getView() {
        return view;
    }

    @Override
    public void setView(SummaryView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

}
