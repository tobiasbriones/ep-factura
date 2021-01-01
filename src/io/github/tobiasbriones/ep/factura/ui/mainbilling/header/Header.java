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

import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerNameAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerNameMutator;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;

import java.time.LocalDate;
import java.util.List;

final class Header {

    interface View extends CustomerNameAccessor, CustomerNameMutator {

        Product getProduct();

        void setProduct(Product value);

        String getRtn();

        void setRtn(String value);

        void setProductList(List<? extends Product> products);

        void setDate(LocalDate value);

    }

    @FunctionalInterface
    interface Output {

        void onAddProduct(Product product);

    }

    private Header() {}

}
