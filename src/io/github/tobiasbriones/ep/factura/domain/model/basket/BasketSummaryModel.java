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

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import io.github.tobiasbriones.ep.factura.domain.model.Money;

public interface BasketSummaryModel {

    static double subtotal(double total, double isv) {
        return Money.decimalFormat(total - isv);
    }

    double getIsv();

    double getSubtotal();

    double getTotal();

}
