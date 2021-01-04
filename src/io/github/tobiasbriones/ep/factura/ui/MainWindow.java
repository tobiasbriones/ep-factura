/*
 * Copyright (c) 2019-2020 Tobias Briones. All rights reserved.
 *
 * SPDX-License-Identifier: MIT
 *
 * This file is part of Example Project: Factura.
 *
 * This source code is licensed under the MIT License found in the
 * LICENSE file in the root directory of this source tree or at
 * https://opensource.org/licenses/MIT.
 */

package io.github.tobiasbriones.ep.factura.ui;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.Basket;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Address;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.ui.core.rx.AnyObservable;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.HeaderComponent;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.ItemsComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public final class MainWindow extends JFrame implements Header.Output {

    //                                                                                            //
    //                                                                                            //
    //                                           CLASS                                            //
    //                                                                                            //
    //                                                                                            //

    public interface Controller {

        ProductDao getProductDao();

        List<String> getCities();

        List<String> getCommunities();

        Basket getBasket();

        void pushToBasket(Product product);

        void save(Bill bill);

    }

    private static final class NewCustomerDialog extends JDialog {

        private interface Callback {

            List<String> getCities();

            List<String> getCommunities();

            void createCustomer(
                String name,
                String surname,
                Address address,
                String phone,
                String sex,
                String birthday
            );

        }

        private static boolean isSet(JTextField tf) {
            return !tf.getText().trim().isEmpty();
        }

        private static String getRadioValue(ButtonGroup buttonGroup) {
            if (buttonGroup.getSelection() == null) {
                return null;
            }
            return buttonGroup.getSelection().getActionCommand();
        }

        private final MainWindow mw;
        private final Callback callback;
        private final JTextField tf1;
        private final JTextField tf2;
        private final JTextField tf3;
        private final JTextField tf4;
        private final JComboBox<String> cb1;
        private final JComboBox<String> cb2;
        private final ButtonGroup buttonGroup;
        private final JButton cancelButton;
        private final JButton saveButton;

        private NewCustomerDialog(MainWindow mw, Callback callback) {
            super(mw, "Nuevo cliente");
            this.mw = mw;
            this.callback = callback;
            this.tf1 = new JTextField();
            this.tf2 = new JTextField();
            this.tf3 = new JTextField();
            this.tf4 = new JTextField();
            this.cb1 = new JComboBox<>();
            this.cb2 = new JComboBox<>();
            this.buttonGroup = new ButtonGroup();
            this.cancelButton = new JButton("Cancelar");
            this.saveButton = new JButton("Guardar");

            init();
        }

        private void init() {
            final JPanel panel = new JPanel();
            final JPanel genrePanel = new JPanel();
            final JPanel formPanel = new JPanel();
            final JPanel bottomPanel = new JPanel();
            final JRadioButton rb1 = new JRadioButton("M");
            final JRadioButton rb2 = new JRadioButton("F");
            final JRadioButton rb3 = new JRadioButton("Otro");
            final List<String> cities = callback.getCities();
            final List<String> communities = callback.getCommunities();
            final ActionListener l = new ClickListener();

            cities.forEach(cb1::addItem);
            communities.forEach(cb2::addItem);

            rb1.setActionCommand("Masculino");
            rb1.setBackground(Color.WHITE);
            rb2.setActionCommand("Femenino");
            rb2.setBackground(Color.WHITE);
            rb3.setActionCommand("Otro");
            rb3.setBackground(Color.WHITE);
            buttonGroup.add(rb1);
            buttonGroup.add(rb2);
            buttonGroup.add(rb3);
            cancelButton.addActionListener(l);
            saveButton.addActionListener(l);
            genrePanel.setBackground(Color.WHITE);
            genrePanel.add(rb1);
            genrePanel.add(rb2);
            genrePanel.add(rb3);
            formPanel.setLayout(new GridLayout(7, 1, 0, 5));
            formPanel.setBackground(Color.WHITE);
            formPanel.add(new JLabel("Nombre"));
            formPanel.add(tf1);
            formPanel.add(new JLabel("Apellido"));
            formPanel.add(tf2);
            formPanel.add(new JLabel("Ciudad"));
            formPanel.add(cb1);
            formPanel.add(new JLabel("Colonia/Barrio"));
            formPanel.add(cb2);
            formPanel.add(new JLabel("Teléfono"));
            formPanel.add(tf3);
            formPanel.add(new JLabel("Género"));
            formPanel.add(genrePanel);
            formPanel.add(new JLabel("Fecha de nacimiento"));
            formPanel.add(tf4);
            bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 0));
            bottomPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
            bottomPanel.setBackground(Color.WHITE);
            bottomPanel.add(cancelButton);
            bottomPanel.add(saveButton);
            panel.setLayout(new BorderLayout());
            panel.setBackground(Color.WHITE);
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.add(formPanel, BorderLayout.PAGE_START);
            panel.add(bottomPanel, BorderLayout.CENTER);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setVisible(true);
        }

        private final class ClickListener implements ActionListener {

            private boolean isRadioGroupSet() {
                return getRadioValue(buttonGroup) != null;
            }

            private boolean isFormSet() {
                return areTextFieldSet() && areComboBoxSet() && isRadioGroupSet();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    if (!isFormSet()) {
                        JOptionPane.showMessageDialog(mw, "LLena todos los campos.");
                        return;
                    }
                    callCreateNewCustomer();
                }
                dispose();
            }

            private boolean areTextFieldSet() {
                return isSet(tf1) && isSet(tf2) && isSet(tf3);
            }

            private boolean areComboBoxSet() {
                return cb1.getSelectedItem() != null && cb2.getSelectedItem() != null;
            }

            private void callCreateNewCustomer() {
                callback.createCustomer(
                    tf1.getText(),
                    tf2.getText(),
                    new Address(cb1.getSelectedItem().toString(), cb2.getSelectedItem().toString()),
                    tf3.getText(),
                    getRadioValue(buttonGroup),
                    tf4.getText()
                );
            }

        }

    }

    //                                                                                            //
    //                                                                                            //
    //                                          INSTANCE                                          //
    //                                                                                            //
    //                                                                                            //

    private final Controller controller;
    private final AnyObservable basketObservable;
    private final JLabel subtotalLabel;
    private final JLabel isvLabel;
    private final JLabel totalLabel;
    private final JCheckBox newCustomerCB;
    private final JButton printButton;

    public MainWindow(Controller controller) {
        super("Factura");
        this.controller = controller;
        this.basketObservable = new AnyObservable();
        this.subtotalLabel = new JLabel();
        this.isvLabel = new JLabel();
        this.totalLabel = new JLabel();
        this.newCustomerCB = new JCheckBox("Nuevo cliente");
        this.printButton = new JButton("Imprimir");

        init();
    }

    @Override
    public void onAddProduct(Product product) {
        controller.pushToBasket(product);
        basketObservable.notifyObservers();
    }

    private void init() {
        final var productDao = controller.getProductDao();
        final var basket = controller.getBasket();
        final var panel = new JPanel();
        final var infoPanel = new JPanel();
        final var infoWrapperPanel = new JPanel();
        final var outputPanel = new JPanel();
        final var bottomPanel = new JPanel();
        final var gbc = new GridBagConstraints();
        final var inputPanel = HeaderComponent.newInstance(productDao, this).getComponent();
        final var scroll = ItemsComponent.newInstance(basket, basketObservable).getComponent();

        // printButton.addActionListener(this);

        infoPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Subtotal"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        infoPanel.add(subtotalLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("ISV"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        infoPanel.add(isvLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(new JLabel("Total"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        infoPanel.add(totalLabel, gbc);
        infoWrapperPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        infoWrapperPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.decode("#737373")));
        infoWrapperPanel.add(infoPanel);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(scroll, BorderLayout.PAGE_START);
        outputPanel.add(infoWrapperPanel, BorderLayout.CENTER);

        // Bottom panel
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        bottomPanel.add(newCustomerCB, BorderLayout.LINE_START);
        bottomPanel.add(printButton, BorderLayout.LINE_END);

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(inputPanel, BorderLayout.PAGE_START);
        panel.add(outputPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.PAGE_END);
        getContentPane().add(panel);

        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private final class NewCustomerDialogCallback implements NewCustomerDialog.Callback {

        private NewCustomerDialogCallback() {
        }

        @Override
        public List<String> getCities() {
            return controller.getCities();
        }

        @Override
        public List<String> getCommunities() {
            return controller.getCommunities();
        }

        @Override
        public void createCustomer(
            String name,
            String surname,
            Address address,
            String phone,
            String sex,
            String birthday
        ) {
            // create the customer ...
            // ... customer created
            final var msg = "Cliente creado: " + name + " " + surname + " - " + address + " - "
                            + phone + " - " + sex + " - " + birthday;

            JOptionPane.showMessageDialog(MainWindow.this, msg);
        }

    }

}
