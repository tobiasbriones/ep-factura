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

import io.github.tobiasbriones.ep.factura.domain.model.basket.Basket;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketSummaryModel;
import io.github.tobiasbriones.ep.factura.ui.core.JPanelMvcView;
import io.github.tobiasbriones.ep.factura.ui.core.rx.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

final class SummaryView extends JPanelMvcView<SummaryController> implements Observer {

    private final Basket basket;
    private final JLabel subtotalLabel;
    private final JLabel isvLabel;
    private final JLabel totalLabel;
    private final JCheckBox createNewCustomerBox;
    private final JButton printButton;
    private BasketSummaryModel model;

    SummaryView(SummaryController controller, Basket basket) {
        super(controller);
        this.basket = basket;
        this.subtotalLabel = new JLabel();
        this.isvLabel = new JLabel();
        this.totalLabel = new JLabel();
        this.createNewCustomerBox = new JCheckBox();
        this.printButton = new JButton();
        this.model = basket.computeSummary();
    }

    boolean isCreateNewCustomerSelected() {
        return createNewCustomerBox.isSelected();
    }

    @Override
    public void createView(JPanel view) {
        final var infoPanel = new JPanel();
        final var infoWrapperPanel = new JPanel();
        final var actionPanel = new JPanel();
        final var gbc = new GridBagConstraints();

        printButton.setText("Imprimir");
        createNewCustomerBox.setText("Nuevo cliente");

        infoPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Subtotal"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        infoPanel.add(subtotalLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("ISV"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        infoPanel.add(isvLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(new JLabel("Total"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        infoPanel.add(totalLabel, gbc);
        infoWrapperPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        infoWrapperPanel.setPreferredSize(new Dimension(630, 96));
        infoWrapperPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#737373")));
        infoWrapperPanel.add(infoPanel);

        actionPanel.setLayout(new BorderLayout());
        actionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        actionPanel.add(createNewCustomerBox, BorderLayout.LINE_START);
        actionPanel.add(printButton, BorderLayout.LINE_END);

        view.setLayout(new BorderLayout());
        view.setBorder(new EmptyBorder(10, 0, 5, 0));
        view.add(infoWrapperPanel, BorderLayout.LINE_END);
        view.add(actionPanel, BorderLayout.PAGE_END);
    }

    @Override
    public void bindEvents(SummaryController controller) {
        printButton.addActionListener(e -> controller.onPrintButtonClick());
    }

    @Override
    public void update() {
        setModel();
        setIsv();
        setSubtotal();
        setTotal();
    }

    private void setModel() {
        model = basket.computeSummary();
    }

    private void setSubtotal() {
        subtotalLabel.setText(String.valueOf(model.getIsv()));
    }

    private void setIsv() {
        isvLabel.setText(String.valueOf(model.getSubtotal()));
    }

    private void setTotal() {
        totalLabel.setText(String.valueOf(model.getTotal()));
    }

}
