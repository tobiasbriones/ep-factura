/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.customer;

import java.util.Objects;

public final class Customer implements CustomerModel {

    private String name;
    private String surname;
    private AddressModel address;
    private String phone;
    private String genre;
    private String birthday;

    public Customer() {
        this.name = "";
        this.surname = "";
        this.address = new Address("", "");
        this.phone = "";
        this.genre = "";
        this.birthday = "";
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Customer setName(String value) {
        name = value;
        return this;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public Customer setSurname(String value) {
        surname = value;
        return this;
    }

    @Override
    public AddressModel getAddress() {
        return address;
    }

    @Override
    public Customer setAddress(AddressModel value) {
        address = value;
        return this;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public Customer setPhone(String value) {
        phone = value;
        return this;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public Customer setGenre(String value) {
        genre = value;
        return this;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }

    @Override
    public Customer setBirthday(String value) {
        birthday = value;
        return this;
    }

}
