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

package dev.tobiasbriones.ep.factura.domain.model.customer;

public interface CustomerMutator extends CustomerNameMutator {
    void setAddress(AddressModel value);

    void setPhone(String value);

    void setGenre(String value);

    void setBirthday(String value);
}
