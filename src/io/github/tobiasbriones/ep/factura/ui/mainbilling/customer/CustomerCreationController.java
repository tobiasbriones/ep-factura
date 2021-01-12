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
import io.github.tobiasbriones.ep.factura.domain.model.city.CityModel;
import io.github.tobiasbriones.ep.factura.domain.model.city.community.CommunityModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.*;
import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

import javax.swing.*;

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
        return isSet(accessor.getFirstName()) && isSet(accessor.getSurname());
    }

    private static boolean isSet(String value) {
        return !value.trim().isEmpty();
    }

    private final CustomerModel customer;
    private final CityDao cityDao;
    private final CommunityDao communityDao;
    private CustomerCreationView view;

    CustomerCreationController(
        CustomerModel customer,
        CityDao cityDao,
        CommunityDao communityDao
    ) {
        super();
        this.customer = customer;
        this.cityDao = cityDao;
        this.communityDao = communityDao;
        this.view = null;
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
        view.setCustomer(customer);
        view.setCities(cityDao.fetchAll());
        view.setCommunities(communityDao.fetchAll());
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
    }

    void onCancelButtonClick() {
        view.dispose();
    }

    private void createNewCustomer() {
        final Customer customer = Customer.from(view);

        // Call async a Dao or Repository ...
        // Then
        onCustomerCreated(customer);
    }

    private void onCustomerCreated(Customer customer) {
        getOutput().ifPresent(output -> output.onCustomerCreated(customer));
        view.dispose();
    }

}
