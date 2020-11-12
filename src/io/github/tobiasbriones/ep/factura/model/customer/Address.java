/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.customer;

public final class Address {
    
    public final String city;
    public final String community;
    
    public Address(String city, String community) {
        this.city = city;
        this.community = community;
    }
    
    @Override
    public String toString() {
        return city + ", " + community;
    }
    
}
