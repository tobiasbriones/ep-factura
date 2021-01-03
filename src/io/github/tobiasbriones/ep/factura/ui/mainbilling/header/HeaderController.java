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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.header;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

import java.time.LocalDate;

final class HeaderController extends MvcController<HeaderView, Header.Output> {

    private final ProductDao productDao;
    private HeaderView view;

    HeaderController(ProductDao productDao) {
        super();
        this.productDao = productDao;
        this.view = null;
    }

    @Override
    public HeaderView getView() {
        return view;
    }

    @Override
    public void setView(HeaderView value) {
        view = value;
    }

    @Override
    public void init() {
        setProducts();
        setDate();
        view.update();
    }

    void onAddButtonClick() {
        final Product product = view.getProduct();

        getOutput().ifPresent(output -> output.onAddProduct(product));
    }

    private void setProducts() {
        final var products = productDao.fetchAll(0, 100);
        view.setProductList(products);
    }

    private void setDate() {
        final var date = LocalDate.now();
        view.setDate(date);
    }
}
