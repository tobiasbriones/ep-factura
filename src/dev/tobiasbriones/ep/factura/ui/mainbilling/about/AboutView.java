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

import dev.tobiasbriones.ep.factura.ui.core.JPanelMvcView;
import res.Resource;

import javax.swing.*;
import java.awt.*;

final class AboutView extends JPanelMvcView<AboutController> {
    private static final String IC_ABOUT_FILE_NAME = "ic_about.png";
    private static final String IC_ABOUT_FILE_LOCATION = Resource.getFileLocation(IC_ABOUT_FILE_NAME);
    private final JButton showDialogButton;

    AboutView(AboutController controller) {
        super(controller);
        this.showDialogButton = new JButton();
    }

    @Override
    public void createView(JPanel view) {
        final var icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(IC_ABOUT_FILE_LOCATION));

        showDialogButton.setFocusable(false);
        showDialogButton.setIcon(icon);
        view.setLayout(new BorderLayout());
        view.add(showDialogButton, BorderLayout.LINE_START);
    }

    @Override
    public void bindEvents(AboutController controller) {
        showDialogButton.addActionListener(e -> controller.onAboutButtonClick());
    }
}
