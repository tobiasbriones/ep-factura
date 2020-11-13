/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.bill;

import io.github.tobiasbriones.ep.factura.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.model.customer.Customer;

import java.util.List;

public interface BillAccessor extends BillResumeAccessor {

    Customer getCustomer();

    String getRtn();

    String getDate();

    List<BasketItem> getItems();

}
