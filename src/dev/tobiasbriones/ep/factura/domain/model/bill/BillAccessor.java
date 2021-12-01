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

package dev.tobiasbriones.ep.factura.domain.model.bill;

import dev.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketSummaryAccessor;
import dev.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;

import java.time.LocalDateTime;

public interface BillAccessor extends BasketSummaryAccessor {
    BasketModel getBasket();

    CustomerModel getCustomer();

    String getRtn();

    LocalDateTime getDate();
}
