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

    void setCustomer(Customer value);

    void setRtn(String value);

    void setDate(LocalDateTime value);

    void addItem(BasketItem item);

    void clear();

}
