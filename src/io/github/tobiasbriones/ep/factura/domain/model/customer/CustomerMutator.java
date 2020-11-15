/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.customer;

public interface CustomerMutator extends CustomerNameMutator {

    void setAddress(AddressModel value);

    void setPhone(String value);

    void setGenre(String value);

    void setBirthday(String value);

}
