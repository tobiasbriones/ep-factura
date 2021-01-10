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

package io.github.tobiasbriones.ep.factura.domain.model.customer;

import io.github.tobiasbriones.ep.factura.domain.model.city.City;
import io.github.tobiasbriones.ep.factura.domain.model.city.community.Community;

public final class Customer implements CustomerModel {

    public static Customer from(CustomerAccessor accessor) {
        final var customer = new Customer();

        customer.setName(accessor.getFirstName());
        customer.setSurname(accessor.getSurname());
        customer.setAddress(accessor.getAddress());
        customer.setPhone(accessor.getPhone());
        customer.setGenre(accessor.getGenre());
        customer.setBirthday(accessor.getBirthday());
        return customer;
    }

    private String name;
    private String surname;
    private AddressModel address;
    private String phone;
    private String genre;
    private String birthday;

    public Customer() {
        this.name = "";
        this.surname = "";
        this.address = new Address(new City(""), new Community(""));
        this.phone = "";
        this.genre = "";
        this.birthday = "";
    }

    @Override
    public String getFirstName() {
        return name;
    }

    @Override
    public void setName(String value) {
        name = value;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String value) {
        surname = value;
    }

    @Override
    public AddressModel getAddress() {
        return address;
    }

    @Override
    public void setAddress(AddressModel value) {
        address = value;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String value) {
        phone = value;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public void setGenre(String value) {
        genre = value;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(String value) {
        birthday = value;
    }

    @Override
    public String toString() {
        return "Customer[" +
               "name=" + name + ", " +
               "surname=" + surname + ", " +
               "address=" + address + ", " +
               "phone=" + phone + ", " +
               "genre=" + genre + ", " +
               "birthday=" + birthday +
               "]";
    }

}
