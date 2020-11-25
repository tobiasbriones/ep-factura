/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.mainbilling.header;

import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

final class HeaderController extends MvcController<HeaderView, Header.Output> {

    private HeaderView view;

    HeaderController() {
        super();
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
        view.update();
    }

    void onAddButtonClick() {
        final Product product = view.getProduct();

        getOutput().ifPresent(output -> output.onAddProduct(product));
    }

}