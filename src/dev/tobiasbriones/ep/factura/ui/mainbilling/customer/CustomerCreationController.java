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

package dev.tobiasbriones.ep.factura.ui.mainbilling.customer;

import dev.tobiasbriones.ep.factura.data.CityDao;
import dev.tobiasbriones.ep.factura.data.CommunityDao;
import dev.tobiasbriones.ep.factura.domain.model.city.CityModel;
import dev.tobiasbriones.ep.factura.domain.model.city.community.CommunityModel;
import dev.tobiasbriones.ep.factura.domain.model.customer.*;
import dev.tobiasbriones.ep.factura.ui.core.MvcController;
import res.Resource;

import javax.swing.*;
import java.awt.*;

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
        return value != null && !value.isBlank();
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
            final var msg = "Llena todos los campos.";
            final String title = "Entrada inválida";
            final JDialog parent = view.getViewComponent();
            final int type = JOptionPane.WARNING_MESSAGE;
            final String iconPath = Resource.getFileLocation("ic_warning_message.png");
            final Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(iconPath));
            JOptionPane.showMessageDialog(parent, msg, title, type, icon);
        }
    }

    void onCancelButtonClick() {
        view.dispose();
    }

    private void createNewCustomer() {
        final Customer newCustomer = Customer.from(view);

        // Call async a Dao or Repository ...
        // Then
        onCustomerCreated(newCustomer);
    }

    private void onCustomerCreated(Customer newCustomer) {
        getOutput().ifPresent(output -> output.onCustomerCreated(newCustomer));
        view.dispose();
    }
}
