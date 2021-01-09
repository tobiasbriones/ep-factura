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

import io.github.tobiasbriones.ep.factura.domain.model.city.City;
import io.github.tobiasbriones.ep.factura.domain.model.city.community.Community;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Address;
import io.github.tobiasbriones.ep.factura.domain.model.customer.AddressModel;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerAccessor;
import io.github.tobiasbriones.ep.factura.ui.core.JDialogMvcView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

final class CustomerCreationView extends JDialogMvcView<CustomerCreationController> implements CustomerAccessor {

    private static String getRadioValue(ButtonGroup buttonGroup) {
        if (buttonGroup.getSelection() == null) {
            return null;
        }
        return buttonGroup.getSelection().getActionCommand();
    }

    private final CustomerCreationController controller;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField phoneField;
    private final JTextField birthdayField;
    private final JComboBox<City> citiesBox;
    private final JComboBox<Community> communitiesBox;
    private final ButtonGroup buttonGroup;
    private final JButton cancelButton;
    private final JButton saveButton;

    CustomerCreationView(CustomerCreationController controller) {
        super(controller);
        this.controller = controller;
        this.nameField = new JTextField();
        this.surnameField = new JTextField();
        this.phoneField = new JTextField();
        this.birthdayField = new JTextField();
        this.citiesBox = new JComboBox<>();
        this.communitiesBox = new JComboBox<>();
        this.buttonGroup = new ButtonGroup();
        this.cancelButton = new JButton();
        this.saveButton = new JButton();
    }

    @Override
    public String getName() {
        return nameField.getText();
    }

    @Override
    public String getSurname() {
        return surnameField.getText();
    }

    @Override
    public AddressModel getAddress() {
        final City city = (City) citiesBox.getSelectedItem();
        final Community community = (Community) communitiesBox.getSelectedItem();
        return (city != null && community != null)
               ? new Address(city, community)
               : null;
    }

    @Override
    public String getPhone() {
        return phoneField.getText();
    }

    @Override
    public String getGenre() {
        return getRadioValue(buttonGroup);
    }

    @Override
    public String getBirthday() {
        return birthdayField.getText();
    }

    @Override
    public void createView(JDialog view) {
        final Container container = view.getContentPane();

        createPanel(container);
        view.pack();
        view.setLocationRelativeTo(null);
        view.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    @Override
    public void bindEvents(CustomerCreationController controller) {
        saveButton.addActionListener(e -> controller.onSaveButtonClick());
        cancelButton.addActionListener(e -> controller.onCancelButtonClick());
    }

    @Override
    public void update() {
        controller.getCities().forEach(citiesBox::addItem);
        controller.getCommunities().forEach(communitiesBox::addItem);
    }

    void show() {
        getView().setVisible(true);
    }

    void dispose() {
        getViewComponent().dispose();
    }

    private void createPanel(Container container) {
        final var panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        createFormPanel(panel);
        createBottomPanel(panel);
        container.add(panel);
    }

    private void createFormPanel(JPanel panel) {
        final var formPanel = new JPanel();
        final var genrePanel = new JPanel();
        final var rb1 = new JRadioButton("M");
        final var rb2 = new JRadioButton("F");
        final var rb3 = new JRadioButton("Otro");

        rb1.setActionCommand("Masculino");
        rb1.setBackground(Color.WHITE);
        rb2.setActionCommand("Femenino");
        rb2.setBackground(Color.WHITE);
        rb3.setActionCommand("Otro");
        rb3.setBackground(Color.WHITE);

        buttonGroup.add(rb1);
        buttonGroup.add(rb2);
        buttonGroup.add(rb3);

        genrePanel.setBackground(Color.WHITE);
        genrePanel.add(rb1);
        genrePanel.add(rb2);
        genrePanel.add(rb3);

        formPanel.setLayout(new GridLayout(7, 1, 0, 5));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(new JLabel("Nombre"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Apellido"));
        formPanel.add(surnameField);
        formPanel.add(new JLabel("Ciudad"));
        formPanel.add(citiesBox);
        formPanel.add(new JLabel("Colonia/Barrio"));
        formPanel.add(communitiesBox);
        formPanel.add(new JLabel("Teléfono"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Género"));
        formPanel.add(genrePanel);
        formPanel.add(new JLabel("Fecha de nacimiento"));
        formPanel.add(birthdayField);
        panel.add(formPanel, BorderLayout.PAGE_START);
    }

    private void createBottomPanel(JPanel panel) {
        final var bottomPanel = new JPanel();

        cancelButton.setText("Cancelar");
        saveButton.setText("Guardar");

        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 0));
        bottomPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        panel.add(bottomPanel, BorderLayout.CENTER);
    }

}
