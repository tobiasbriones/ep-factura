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

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItemModel;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.ui.core.rx.AnyObservable;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.Items;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.print.Print;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.Summary;

import javax.swing.*;

final class MainBillingMediator implements Header.Output, Print.Output, Items.Output {

    private final BasketModel basket;
    private final AnyObservable basketObservable;
    private final Header header;
    private final Items items;
    private final Summary summary;
    private final Print print;

    MainBillingMediator(BasketModel basket, ProductDao productDao) {
        this.basket = basket;
        this.basketObservable = new AnyObservable();
        this.header = Header.newInstance(productDao);
        this.items = Items.newInstance(basket);
        this.summary = Summary.newInstance(basket);
        this.print = Print.newInstance();
    }

    JPanel getHeaderViewComponent() {
        return header.getViewComponent();
    }

    JPanel getItemsViewComponent() {
        return items.getViewComponent();
    }

    JPanel getSummaryViewComponent() {
        return summary.getViewComponent();
    }

    JPanel getPrintViewComponent() {
        return print.getViewComponent();
    }

    @Override
    public void onAddProduct(ProductModel product) {
        pushToBasket(product);
        basketObservable.notifyObservers();
    }

    @Override
    public void onPrint() {

    }

    @Override
    public void onPrintWithNewCustomer() {

    }

    @Override
    public void onItemUpdated(BasketItemModel item) {
        basketObservable.notifyObservers();
    }

    void init() {
        header.setOutput(this);
        items.setOutput(this);
        items.subscribe(basketObservable);
        summary.subscribe(basketObservable);
        print.setOutput(this);
    }

    private void pushToBasket(ProductModel product) {
        basket.pushProduct(product);
    }

}
