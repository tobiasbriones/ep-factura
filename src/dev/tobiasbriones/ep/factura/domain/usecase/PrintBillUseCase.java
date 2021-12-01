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

package dev.tobiasbriones.ep.factura.domain.usecase;

import dev.tobiasbriones.ep.factura.domain.model.bill.BillAccessor;
import dev.tobiasbriones.ep.factura.domain.model.bill.BillPrinter;

public final class PrintBillUseCase {
    private final BillPrinter printer;

    public PrintBillUseCase(BillPrinter printer) {
        this.printer = printer;
    }

    public void execute(BillAccessor bill) {
        printer.clear();

        printer.setCustomer(bill.getCustomer());
        printer.setDate(bill.getDate());
        printer.setRtn(bill.getRtn());
        printer.setBasket(bill.getBasket());
        printer.setBillSummary(bill);
        printer.print();
    }
}
