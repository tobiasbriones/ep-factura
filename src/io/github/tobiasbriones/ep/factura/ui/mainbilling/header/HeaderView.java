/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.mainbilling.header;

import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.ui.core.JPanelMvcView;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

final class HeaderView extends JPanelMvcView<HeaderController> implements Header.View {

    private static final class ProductBoxRenderer extends DefaultListCellRenderer {

        private ProductBoxRenderer() {
            super();
        }

        @Override
        public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
        ) {
            if (value != null) {
                final var product = (Product) value;
                final var str = product.getCode() + " " + product.getPrice();

                setText(str);
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }

    }

    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField rtnField;
    private final JTextField dateField;
    private final JComboBox<Product> productsBox;
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
    public void createView(JPanel view) {
        final var gbc = new GridBagConstraints();

        view.setLayout(new GridBagLayout());

        productsBox.setRenderer(new ProductBoxRenderer());
        addButton.setText("Agregar");

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
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

        gbc.gridx = 0;
        view.add(new JLabel(), gbc);

        gbc.gridx = 1;
        view.add(new JLabel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        view.add(new JLabel("Producto"), gbc);

        gbc.gridx = 1;
        view.add(productsBox, gbc);

        gbc.gridx = 3;
        view.add(addButton, gbc);
    }

    @Override
    public void bindEvents(HeaderController controller) {
        addButton.addActionListener(e -> controller.onAddButtonClick());
    }

    @Override
    public String getName() {
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
    public Product getProduct() {
        return (Product) productsBox.getSelectedItem();
    }

    @Override
    public void setProduct(Product value) {
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
    public void setProductList(List<? extends Product> products) {
        productsBox.removeAllItems();
        products.forEach(productsBox::addItem);
    }

    @Override
    public void setDate(LocalDate value) {
        dateField.setText(String.valueOf(value));
    }

}