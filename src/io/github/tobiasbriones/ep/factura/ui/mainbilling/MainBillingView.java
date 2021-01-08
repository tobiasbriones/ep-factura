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

import io.github.tobiasbriones.ep.factura.ui.core.JFrameMvcView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

final class MainBillingView extends JFrameMvcView<MainBillingController> {

    private static final String WINDOW_TITLE = "Factura";
    private final JPanel headerPanel;
    private final JPanel itemsPanel;
    private final JPanel summaryPanel;
    private final JPanel printPanel;

    MainBillingView(MainBillingController controller, MainBillingWindow.ChildViewConfig config) {
        super(controller);
        this.headerPanel = config.getHeaderViewComponent();
        this.itemsPanel = config.getItemsViewComponent();
        this.summaryPanel = config.getSummaryViewComponent();
        this.printPanel = config.getPrintViewComponent();
    }

    @Override
    public void createView(JFrame view) {
        final var panel = new JPanel();
        final var endPanel = new JPanel();

        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.PAGE_AXIS));
        endPanel.add(summaryPanel);
        endPanel.add(printPanel);

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(headerPanel, BorderLayout.PAGE_START);
        panel.add(itemsPanel, BorderLayout.CENTER);
        panel.add(endPanel, BorderLayout.PAGE_END);
        view.getContentPane().add(panel);

        view.setTitle(WINDOW_TITLE);
        view.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.pack();
        view.setLocationRelativeTo(null);
        view.setResizable(false);
    }

    void show() {
        getViewComponent().setVisible(true);
    }

}
