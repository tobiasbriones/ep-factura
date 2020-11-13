/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.bill;

public interface BillResumeAccessor {

    double getSubtotal();

    double getIsv();

    double getTotal();

}