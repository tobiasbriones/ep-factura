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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.customer;

import io.github.tobiasbriones.ep.factura.data.CityDao;
import io.github.tobiasbriones.ep.factura.data.CommunityDao;
import io.github.tobiasbriones.ep.factura.domain.model.city.City;
import io.github.tobiasbriones.ep.factura.domain.model.city.CityModel;
import io.github.tobiasbriones.ep.factura.domain.model.city.community.Community;
import io.github.tobiasbriones.ep.factura.domain.model.city.community.CommunityModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.AddressModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerNameAccessor;
import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

import javax.swing.*;
import java.util.List;

final class CustomerCreationController extends MvcController<CustomerCreationView, CustomerCreationDialog.Output> {

    private static boolean isFormSet(CustomerAccessor accessor) {
        return isSet(accessor.getPhone()) &&
               isSet(accessor.getGenre()) &&
               isSet(accessor.getBirthday()) &&
               isNameSet(accessor) &&
               isAddressSet(accessor);
    }

    private static boolean isAddressSet(CustomerAccessor accessor) {
        return accessor.getAddress() != null && areAddressAttributesSet(accessor.getAddress());
    }

    private static boolean areAddressAttributesSet(AddressModel address) {
        return isCitySet(address.getCity()) && isCommunitySet(address.getCommunity());
    }

    private static boolean isCitySet(CityModel city) {
        return isSet(city.getName());
    }

    private static boolean isCommunitySet(CommunityModel community) {
        return isSet(community.getName());
    }

    private static boolean isNameSet(CustomerNameAccessor accessor) {
        return isSet(accessor.getName()) && isSet(accessor.getSurname());
    }

    private static boolean isSet(String value) {
        return !value.trim().isEmpty();
    }

    private final CityDao cityDao;
    private final CommunityDao communityDao;
    private CustomerCreationView view;

    CustomerCreationController(CityDao cityDao, CommunityDao communityDao) {
        super();
        this.cityDao = cityDao;
        this.communityDao = communityDao;
        this.view = null;
    }

    List<City> getCities() {
        return cityDao.fetchAll();
    }

    List<Community> getCommunities() {
        return communityDao.fetchAll();
    }

    @Override
    public CustomerCreationView getView() {
        return view;
    }

    @Override
    public void setView(CustomerCreationView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

    void onSaveButtonClick() {
        if (isFormSet(view)) {
            createNewCustomer();
        }
        else {
            final var msg = "LLena todos los campos.";
            JOptionPane.showMessageDialog(view.getViewComponent(), msg);
        }
        view.dispose();
    }

    void onCancelButtonClick() {
        view.dispose();
    }

    private void createNewCustomer() {
        final Customer customer = Customer.from(view);
        getOutput().ifPresent(output -> output.onCreateCustomer(customer));
    }

}
