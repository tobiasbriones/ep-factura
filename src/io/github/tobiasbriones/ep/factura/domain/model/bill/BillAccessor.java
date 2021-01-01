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

package io.github.tobiasbriones.ep.factura.domain.model.bill;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;

public interface BillAccessor extends BillResumeAccessor {

    Customer getCustomer();

    String getRtn();

    LocalDateTime getDate();

    List<BasketItem> getItems();

}
