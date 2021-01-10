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

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;

import java.time.LocalDateTime;

public interface BillMutator {

    void setBasket(BasketModel value);

    void setCustomer(CustomerModel value);

    void setRtn(String value);

    void setDate(LocalDateTime value);

    void clear();

}
