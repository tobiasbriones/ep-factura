/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.customer;

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

}
