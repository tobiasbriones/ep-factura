/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.model.customer;

public final class Customer {
    
    private String name;
    private String surname;
    private Address address;
    private String phone;
    private String genre;
    private String birthday;
    
    public Customer() {}
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public String getBirthday() {
        return birthday;
    }
    
}
