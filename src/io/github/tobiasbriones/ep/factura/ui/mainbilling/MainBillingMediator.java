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
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.core.rx.AnyObservable;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.Items;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.print.Print;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.Summary;

final class MainBillingMediator {

    @FunctionalInterface
    interface ShowCustomerCreationDialogFn {

        void apply();

    }

    @FunctionalInterface
    interface PrintFn {

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

    private static final class PrintOutput implements Print.Output {
        private PrintFn printFn;
        private ShowCustomerCreationDialogFn showCustomerDialogFn;

        private PrintOutput() {
            this.printFn = null;
            this.showCustomerDialogFn = null;
        }

        void setPrintFn(PrintFn value) {
            printFn = value;
        }

        void setShowCustomerDialogFn(ShowCustomerCreationDialogFn value) {
            showCustomerDialogFn = value;
        }

        @Override
        public void onPrint() {
            if (printFn != null) {
                printFn.apply();
            }
        }

        @Override
        public void onPrintWithNewCustomer() {
            if (showCustomerDialogFn != null) {
                showCustomerDialogFn.apply();
            }
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

    void setPrintFn(PrintFn printFn) {
        printOutput.setPrintFn(printFn);
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

}
