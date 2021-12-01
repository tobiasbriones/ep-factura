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

package dev.tobiasbriones.ep.factura.ui.mainbilling.summary;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import dev.tobiasbriones.ep.factura.ui.core.JPanelMvcView;
import dev.tobiasbriones.ep.factura.ui.core.rx.Observer;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketSummaryModel;

import javax.swing.*;
import java.awt.*;

final class SummaryView extends JPanelMvcView<SummaryController> implements Observer {

    private static String moneyValueToString(double money) {
        return "$" + money;
    }

    private final BasketModel basket;
    private final JLabel subtotalLabel;
    private final JLabel isvLabel;
    private final JLabel totalLabel;
    private BasketSummaryModel model;

    SummaryView(SummaryController controller, BasketModel basket) {
        super(controller);
        this.basket = basket;
        this.subtotalLabel = new JLabel();
        this.isvLabel = new JLabel();
        this.totalLabel = new JLabel();
        this.model = basket.computeSummary();
    }

    @Override
    public void createView(JPanel view) {
        final var infoPanel = new JPanel();
        final var gbc = new GridBagConstraints();

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

        view.setLayout(new FlowLayout(FlowLayout.TRAILING));
        view.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.decode("#737373")));
        view.add(infoPanel);
    }

    @Override
    public void update() {
        setModel();
        setSubtotal();
        setIsv();
        setTotal();
    }

    private void setModel() {
        model = basket.computeSummary();
    }

    private void setSubtotal() {
        subtotalLabel.setText(moneyValueToString(model.getSubtotal()));
    }

    private void setIsv() {
        isvLabel.setText(moneyValueToString(model.getIsv()));
    }

    private void setTotal() {
        totalLabel.setText(moneyValueToString(model.getTotal()));
    }

}
