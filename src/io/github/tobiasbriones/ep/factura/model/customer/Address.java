/*
 * Copyright (c) 2019-2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.customer;

import java.util.Objects;

public final class Address implements AddressModel {

    private final String city;
    private final String community;

    public Address(String city, String community) {
        this.city = city;
        this.community = community;
    }

    @Override
    public String toString() {
        return "Address[" +
               "city=" + city + ", " +
               "community=" + community + ", " +
               "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, community);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AddressModel address = (AddressModel) obj;
        return Objects.equals(city, address.getCity()) &&
               Objects.equals(community, address.getCommunity());
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCommunity() {
        return community;
    }

}
