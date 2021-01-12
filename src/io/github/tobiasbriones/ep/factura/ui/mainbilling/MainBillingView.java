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
import res.Resource;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

final class MainBillingView extends JFrameMvcView<MainBillingController> {

    private static final String ICON_NAME = "icon.png";
    private static final String ICON_LOCATION = Resource.getFileLocation(ICON_NAME);
    private static final String WINDOW_TITLE = "Factura";
    private final JPanel headerPanel;
    private final JScrollPane itemsPanel;
    private final JPanel summaryPanel;
    private final JPanel printPanel;
    private final JPanel aboutPanel;

    MainBillingView(MainBillingController controller, MainBillingWindow.ChildViewConfig config) {
        super(controller);
        this.headerPanel = config.getHeaderViewComponent();
        this.itemsPanel = config.getItemsViewComponent();
        this.summaryPanel = config.getSummaryViewComponent();
        this.printPanel = config.getPrintViewComponent();
        this.aboutPanel = config.getAboutViewComponent();
    }

    @Override
    public void createView(JFrame view) {
        final var panel = new JPanel();
        final var outputPanel = new JPanel();
        final var bottomPanel = new JPanel();

        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(itemsPanel, BorderLayout.PAGE_START);
        outputPanel.add(summaryPanel, BorderLayout.CENTER);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(printPanel, BorderLayout.PAGE_START);
        bottomPanel.add(aboutPanel, BorderLayout.CENTER);

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(headerPanel, BorderLayout.PAGE_START);
        panel.add(outputPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.PAGE_END);
        view.getContentPane().add(panel);

        view.setTitle(WINDOW_TITLE);
        view.setIconImage(Toolkit.getDefaultToolkit().getImage(ICON_LOCATION));
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.pack();
        view.setLocationRelativeTo(null);
        view.setResizable(false);
    }

    void show() {
        getViewComponent().setVisible(true);
    }

}
