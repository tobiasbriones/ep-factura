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

import dev.tobiasbriones.ep.factura.ui.core.SwingComponent;

import javax.swing.*;

public final class About implements SwingComponent<JPanel> {
    @FunctionalInterface
    public interface Output {
        void onShowAboutDialog();
    }

    public static About newInstance() {
        final var component = new About();

        component.init();
        return component;
    }

    private final AboutController controller;
    private final AboutView view;

    private About() {
        this.controller = new AboutController();
        this.view = new AboutView(controller);
    }

    @Override
    public JPanel getViewComponent() {
        return view.getViewComponent();
    }

    public void setOutput(About.Output output) {
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
