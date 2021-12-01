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

import dev.tobiasbriones.ep.factura.domain.model.city.City;
import dev.tobiasbriones.ep.factura.domain.model.city.community.Community;

import java.util.Objects;

public final class Address implements AddressModel {
    private final City city;
    private final Community community;

    public Address(City city, Community community) {
        this.city = city;
        this.community = community;
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
    public City getCity() {
        return city;
    }

    @Override
    public Community getCommunity() {
        return community;
    }

    @Override
    public String toString() {
        return "Address[" +
               "city=" + city + ", " +
               "community=" + community +
               "]";
    }
}
