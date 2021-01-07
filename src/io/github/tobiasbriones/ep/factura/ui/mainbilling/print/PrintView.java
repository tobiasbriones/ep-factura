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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.print;

import io.github.tobiasbriones.ep.factura.ui.core.JPanelMvcView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

final class PrintView extends JPanelMvcView<PrintController> {

    private final JCheckBox createNewCustomerBox;
    private final JButton printButton;

    PrintView(PrintController controller) {
        super(controller);
        this.createNewCustomerBox = new JCheckBox();
        this.printButton = new JButton();
    }

    boolean isCreateNewCustomerSelected() {
        return createNewCustomerBox.isSelected();
    }

    @Override
    public void createView(JPanel view) {
        printButton.setText("Imprimir");
        createNewCustomerBox.setText("Nuevo cliente");

        view.setLayout(new BorderLayout());
        view.setBorder(new EmptyBorder(10, 10, 10, 10));
        view.add(createNewCustomerBox, BorderLayout.LINE_START);
        view.add(printButton, BorderLayout.LINE_END);
    }

    @Override
    public void bindEvents(PrintController controller) {
        printButton.addActionListener(e -> controller.onPrintButtonClick());
    }

}
