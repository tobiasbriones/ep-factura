/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.bill;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;

import java.time.LocalDateTime;

public interface BillMutator {

    BillMutator setCustomer(Customer value);

    BillMutator setRtn(String value);

    BillMutator setDate(LocalDateTime value);

    void addItem(BasketItem item);

    void clear();

}
