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

package dev.tobiasbriones.ep.factura.ui.mainbilling.header;

import dev.tobiasbriones.ep.factura.ui.core.JPanelMvcView;
import dev.tobiasbriones.ep.factura.domain.model.customer.CustomerNameAccessor;
import dev.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

final class HeaderView extends JPanelMvcView<HeaderController> implements Header.View {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");

    private static final class ProductBoxRenderer extends DefaultListCellRenderer {
        private ProductBoxRenderer() {
            super();
        }

        @Override
        public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value != null) {
                final var product = (ProductModel) value;
                final var str = product.getCode() + " " + product.getDescription() + " " + product.getPrice();

                setText(str);
            }
            return this;
        }
    }

    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField rtnField;
    private final JTextField dateField;
    private final JComboBox<ProductModel> productsBox;
    private final JButton addButton;

    HeaderView(HeaderController controller) {
        super(controller);
        this.nameField = new JTextField();
        this.surnameField = new JTextField();
        this.rtnField = new JTextField();
        this.dateField = new JTextField();
        this.productsBox = new JComboBox<>();
        this.addButton = new JButton();
    }

    @Override
    public String getFirstName() {
        return nameField.getText();
    }

    @Override
    public void setName(String value) {
        nameField.setText(value);
    }

    @Override
    public String getSurname() {
        return surnameField.getText();
    }

    @Override
    public void setSurname(String value) {
        surnameField.setText(value);
    }

    @Override
    public ProductModel getProduct() {
        return (ProductModel) productsBox.getSelectedItem();
    }

    @Override
    public void setProduct(ProductModel value) {
        productsBox.setSelectedItem(value);
    }

    @Override
    public String getRtn() {
        return rtnField.getText();
    }

    @Override
    public void setRtn(String value) {
        rtnField.setText(value);
    }

    @Override
    public void setProductList(List<? extends ProductModel> products) {
        productsBox.removeAllItems();
        products.forEach(productsBox::addItem);
    }

    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.parse(dateField.getText(), dateTimeFormatter);
    }

    @Override
    public void setDate(LocalDateTime value) {
        dateField.setText(value.format(dateTimeFormatter));
    }

    @Override
    public void createView(JPanel view) {
        final var gbc = new GridBagConstraints();

        view.setLayout(new GridBagLayout());
        view.setBorder(new EmptyBorder(0, 0, 10, 0));

        setCustomerNameRows(view, gbc);
        setRtnDateRows(view, gbc);
        setProductsRow(view, gbc);
    }

    @Override
    public void bindEvents(HeaderController controller) {
        addButton.addActionListener(e -> controller.onAddButtonClick());
    }

    void update(CustomerNameAccessor accessor) {
        setName(accessor.getFirstName());
        setSurname(accessor.getSurname());
    }

    private void setCustomerNameRows(JPanel view, GridBagConstraints gbc) {
        dateField.setEditable(false);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.insets.bottom = 5;
        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        view.add(new JLabel("Nombre"), gbc);

        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        view.add(new JLabel("Apellido"), gbc);

        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        view.add(nameField, gbc);

        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 2;
        gbc.gridy = 1;
        view.add(surnameField, gbc);
    }

    private void setRtnDateRows(JPanel view, GridBagConstraints gbc) {
        gbc.gridwidth = 1;
        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 2;
        gbc.gridy = 2;
        view.add(new JLabel("RTN"), gbc);

        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 3;
        gbc.gridy = 2;
        view.add(new JLabel("Fecha"), gbc);

        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 2;
        gbc.gridy = 3;
        view.add(rtnField, gbc);

        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 3;
        gbc.gridy = 3;
        view.add(dateField, gbc);
    }

    private void setProductsRow(JPanel view, GridBagConstraints gbc) {
        productsBox.setPreferredSize(new Dimension(96, 25));
        productsBox.setRenderer(new ProductBoxRenderer());
        addButton.setText("Agregar");

        gbc.gridx = 0;
        gbc.gridy = 4;
        view.add(new JLabel("Producto"), gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        view.add(productsBox, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 3;
        view.add(addButton, gbc);
    }

}
