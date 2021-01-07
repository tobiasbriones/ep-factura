/*
 * Copyright (c) 2019-2020 Tobias Briones. All rights reserved.
 *
 * SPDX-License-Identifier: MIT
 *
 * This file is part of Example Project: Factura.
 *
 * This source code is licensed under the MIT License found in the
 * LICENSE file in the root directory of this source tree or at
 * https://opensource.org/licenses/MIT.
 */

package io.github.tobiasbriones.ep.factura;

import io.github.tobiasbriones.ep.factura.database.InMemoryProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketList;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.MainBillingWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ignore) {
        }
        SwingUtilities.invokeLater(Main::new);
    }

    private static MainBillingWindow newMainBillingWindow() {
        return MainBillingWindow.newInstance(Config.newMainDependency());
    }

    private static final class Config {

        static MainBillingWindow.Dependency newMainDependency() {
            return new MainBillingWindow.Dependency(
                new BasketList(), new InMemoryProductDao()
            );
        }

        private Config() {}

    }

    private final MainBillingWindow mw;
    private final List<Bill> bills;

    private Main() {
        this.mw = newMainBillingWindow();
        this.bills = new ArrayList<>();

        init();
    }

    private void init() {
        mw.show();
    }

}
