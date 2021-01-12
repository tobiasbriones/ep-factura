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
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerNameAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.core.rx.AnyObservable;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.about.About;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.customer.CustomerCreationDialog;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.Items;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.print.Print;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.Summary;

final class MainBillingMediator {

    @FunctionalInterface
    interface ShowAboutDialogFn {

        void apply();

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
        private static boolean isCustomerValid(CustomerNameAccessor accessor) {
            return !accessor.getFullName().isBlank();
        }

        private final BillModel bill;
        private MainBillingWindow.Input mwInput;

        private PrintOutput() {
            this.bill = new Bill();
            this.mwInput = null;
        }

        void setMwInput(MainBillingWindow.Input value) {
            mwInput = value;
        }

        @Override
        public void onPrint() {
            if (mwInput != null) {
                mwInput.setBill(bill);
                onSendToPrint();
            }
        }

        @Override
        public void onPrintWithNewCustomer() {
            if (mwInput != null) {
                mwInput.setBill(bill);
                mwInput.showCustomerCreationDialog(bill);
            }
        }

        private void onPrintWithNewCustomer(CustomerModel customer) {
            if (mwInput != null) {
                mwInput.setBill(bill);
                bill.setCustomer(customer);
                onSendToPrint();
            }
        }

        private void onSendToPrint() {
            // For a real use case, the parent component (MainBillingWindow)
            // would send an input to each corresponding child to validate and
            // then they would set a red flag in each of the invalid views.
            // The parent would not proceed to print as one children would've
            // return false when sending the validation input event if the user
            // input is invalid.
            if (isBillValid()) {
                mwInput.showBillPrintedDialog(bill);
            }
            else {
                mwInput.showSetAllFieldsDialog();
            }
        }

        private boolean isBillValid() {
            return isCustomerValid(bill.getCustomer()) && !bill.getRtn().isBlank();
        }

    }

    private final AnyObservable basketObservable;
    private final HeaderOutput headerOutput;
    private final ItemsOutput itemsOutput;
    private final PrintOutput printOutput;
    private ShowAboutDialogFn showAboutDialogFn;

    MainBillingMediator(BasketModel basket) {
        this.basketObservable = new AnyObservable();
        this.headerOutput = new HeaderOutput(basket, basketObservable);
        this.itemsOutput = new ItemsOutput(basketObservable);
        this.printOutput = new PrintOutput();
        this.showAboutDialogFn = null;
    }

    void setComponentInput(MainBillingWindow.Input mwInput) {
        printOutput.setMwInput(mwInput);
    }

    void setShowAboutDialogFn(ShowAboutDialogFn value) {
        showAboutDialogFn = value;
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

    void onInitAbout(About about) {
        about.setOutput(showAboutDialogFn::apply);
    }

}
