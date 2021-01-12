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

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItemModel;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.domain.model.bill.BillModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.core.rx.AnyObservable;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.customer.CustomerCreationDialog;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.Items;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.print.Print;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.Summary;

final class MainBillingMediator {

    @FunctionalInterface
    interface ShowBillPrintedDialogFn {

        void apply(BillModel bill);

    }

    @FunctionalInterface
    interface SetBillFn {

        void apply(BillModel bill);

    }

    @FunctionalInterface
    interface ShowCustomerCreationDialogFn {

        void apply(BillModel bill);

    }

    private static final class HeaderOutput implements Header.Output {
        private final BasketModel basket;
        private final AnyObservable basketObservable;

        private HeaderOutput(BasketModel basket, AnyObservable basketObservable) {
            this.basket = basket;
            this.basketObservable = basketObservable;
        }

        @Override
        public void onAddProduct(ProductModel product) {
            basket.pushProduct(product);
            basketObservable.notifyObservers();
        }
    }

    private static final class ItemsOutput implements Items.Output {
        private final AnyObservable basketObservable;

        private ItemsOutput(AnyObservable basketObservable) {
            this.basketObservable = basketObservable;
        }

        @Override
        public void onItemUpdated(BasketItemModel item) {
            basketObservable.notifyObservers();
        }
    }

    private static final class PrintOutput implements Print.Output {
        private final BillModel bill;
        private ShowBillPrintedDialogFn showBillPrintedDialogFn;
        private SetBillFn setBillFn;
        private ShowCustomerCreationDialogFn showCustomerDialogFn;

        private PrintOutput() {
            this.bill = new Bill();
            this.showBillPrintedDialogFn = null;
            this.setBillFn = null;
            this.showCustomerDialogFn = null;
        }

        void setShowBillPrintedDialogFn(ShowBillPrintedDialogFn value) {
            showBillPrintedDialogFn = value;
        }

        void setSetBillFn(SetBillFn value) {
            setBillFn = value;
        }

        void setShowCustomerDialogFn(ShowCustomerCreationDialogFn value) {
            showCustomerDialogFn = value;
        }

        @Override
        public void onPrint() {
            validateFunctions();
            setBillFn.apply(bill);
            showBillPrintedDialogFn.apply(bill);
        }

        @Override
        public void onPrintWithNewCustomer() {
            validateFunctions();
            setBillFn.apply(bill);
            showCustomerDialogFn.apply(bill);
        }

        private void onPrintWithNewCustomer(CustomerModel customer) {
            validateFunctions();
            setBillFn.apply(bill);
            bill.setCustomer(customer);
            showBillPrintedDialogFn.apply(bill);
        }

        private void validateFunctions() {
            if (showBillPrintedDialogFn == null || setBillFn == null || showCustomerDialogFn == null) {
                final var msg = "PrintOutput Function not set";
                throw new RuntimeException(msg);
            }
        }
    }

    private final AnyObservable basketObservable;
    private final HeaderOutput headerOutput;
    private final ItemsOutput itemsOutput;
    private final PrintOutput printOutput;

    MainBillingMediator(BasketModel basket) {
        this.basketObservable = new AnyObservable();
        this.headerOutput = new HeaderOutput(basket, basketObservable);
        this.itemsOutput = new ItemsOutput(basketObservable);
        this.printOutput = new PrintOutput();
    }

    void setShowBillPrintedDialog(ShowBillPrintedDialogFn value) {
        printOutput.setShowBillPrintedDialogFn(value);
    }

    void setSetBillFn(SetBillFn value) {
        printOutput.setSetBillFn(value);
    }

    void setShowCustomerDialogFn(ShowCustomerCreationDialogFn value) {
        printOutput.setShowCustomerDialogFn(value);
    }

    void onInitHeader(Header header) {
        header.setOutput(headerOutput);
    }

    void onInitItems(Items items) {
        items.setOutput(itemsOutput);
        items.subscribe(basketObservable);
    }

    void onInitSummary(Summary summary) {
        summary.subscribe(basketObservable);
    }

    void onInitPrint(Print print) {
        print.setOutput(printOutput);
    }

    void onInitCustomerCreationDialog(CustomerCreationDialog dialog) {
        dialog.setOutput(printOutput::onPrintWithNewCustomer);
    }

}
