/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.customer;

public interface CustomerMutator {

    CustomerMutator setName(String value);

    CustomerMutator setSurname(String value);

    CustomerMutator setAddress(AddressModel value);

    CustomerMutator setPhone(String value);

    CustomerMutator setGenre(String value);

    CustomerMutator setBirthday(String value);

}
