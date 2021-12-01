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

package dev.tobiasbriones.ep.factura.ui.mainbilling.about;

import dev.tobiasbriones.ep.factura.ui.core.MvcController;

final class AboutController extends MvcController<AboutView, About.Output> {
    private AboutView view;

    AboutController() {
        super();
        this.view = null;
    }

    @Override
    public AboutView getView() {
        return view;
    }

    @Override
    public void setView(AboutView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

    void onAboutButtonClick() {
        getOutput().ifPresent(About.Output::onShowAboutDialog);
    }
}
