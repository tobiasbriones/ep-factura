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

package dev.tobiasbriones.ep.factura.io;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import dev.tobiasbriones.ep.factura.domain.model.bill.BillPrinter;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketSummaryAccessor;
import dev.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;
import res.Resource;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public final class Printer implements BillPrinter {
    private final JFrame parent;
    private String customerName;
    private int numberOfProducts;
    private double total;

    public Printer(JFrame parent) {
        this.parent = parent;
        this.customerName = "";
        this.numberOfProducts = 0;
        this.total = 0.0;
    }

    @Override
    public void setBillSummary(BasketSummaryAccessor accessor) {
        total = accessor.getTotal();
    }

    @Override
    public void setBasket(BasketModel value) {
        numberOfProducts = value.size();
    }

    @Override
    public void setCustomer(CustomerModel value) {
        customerName = value.getFullName();
    }

    @Override
    public void setRtn(String value) {
        // ...
    }

    @Override
    public void setDate(LocalDateTime value) {
        // ...
    }

    @Override
    public void print() {
        final String msg = createPrintMsg();
        final String title = createPrintTitle();
        final int type = JOptionPane.INFORMATION_MESSAGE;
        final String iconPath = Resource.getFileLocation("ic_info_message.png");
        final Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(iconPath));
        JOptionPane.showMessageDialog(parent, msg, title, type, icon);
    }

    @Override
    public void clear() {
        customerName = "";
        numberOfProducts = 0;
        total = 0.0;
    }

    private String createPrintTitle() {
        return "Factura - " + customerName;
    }

    private String createPrintMsg() {
        return numberOfProducts + " productos. Total: $" + total;
    }
}
