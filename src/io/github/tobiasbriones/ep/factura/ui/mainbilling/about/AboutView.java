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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.about;

import io.github.tobiasbriones.ep.factura.ui.core.JPanelMvcView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

final class AboutView extends JPanelMvcView<AboutController> {

    private final JButton printButton;

    AboutView(AboutController controller) {
        super(controller);
        this.printButton = new JButton();
    }

    @Override
    public void createView(JPanel view) {
        final var icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("ic_about.png"));

        printButton.setIcon(icon);
        view.setLayout(new BorderLayout());
        view.add(printButton, BorderLayout.LINE_START);
    }

    @Override
    public void bindEvents(AboutController controller) {
        printButton.addActionListener(e -> controller.onAboutButtonClick());
    }

}
