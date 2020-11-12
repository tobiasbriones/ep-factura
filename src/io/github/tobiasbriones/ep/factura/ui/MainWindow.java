/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui;

import io.github.tobiasbriones.ep.factura.model.BasketItem;
import io.github.tobiasbriones.ep.factura.model.Bill;
import io.github.tobiasbriones.ep.factura.model.Product;
import io.github.tobiasbriones.ep.factura.model.customer.Address;
import io.github.tobiasbriones.ep.factura.model.customer.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class MainWindow extends JFrame implements ActionListener {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".##");
    public interface Controller {

        List<String> getCities();

        List<String> getCommunities();

        void save(Bill bill);

    }

    private static final class ListRenderer extends JPanel implements ListCellRenderer<BasketItem> {

        private final JPanel panelLeft;
        private final JPanel panelRight;
        private final JLabel label1;
        private final JLabel label2;
        private final JLabel label3;
        private final JLabel label4;

        private ListRenderer() {
            this.panelLeft = new JPanel();
            this.panelRight = new JPanel();
            this.label1 = new JLabel();
            this.label2 = new JLabel();
            this.label3 = new JLabel();
            this.label4 = new JLabel();

            label1.setPreferredSize(new Dimension(40, 20));
            label1.setHorizontalAlignment(SwingConstants.RIGHT);
            panelLeft.setBackground(Color.decode("#FFFFFF"));
            panelLeft.add(label1);
            panelLeft.add(label2);
            panelLeft.add(label3);
            label4.setPreferredSize(new Dimension(140, 20));
            label4.setHorizontalAlignment(SwingConstants.RIGHT);
            panelRight.setBackground(Color.decode("#FFFFFF"));
            panelRight.add(label4);
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(600, 30));
            setBorder(new EmptyBorder(0, 10, 0, 10));
            setOpaque(true);
            add(panelLeft, BorderLayout.WEST);
            add(panelRight, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(
            JList<? extends BasketItem> list, BasketItem value, int index,
            boolean isSelected, boolean cellHasFocus
        ) {
            label1.setText(String.valueOf(value.getQuantity()));
            label2.setText(String.valueOf(value.getProduct().code));
            label3.setText(value.getProduct().description);
            label4.setText("$" + DECIMAL_FORMAT.format(value.getAmount()));
            if (isSelected) {
                panelLeft.setBackground(Color.decode("#EAEAEA"));
                panelRight.setBackground(Color.decode("#EAEAEA"));
                setBackground(Color.decode("#EAEAEA"));
            }
            else {
                panelLeft.setBackground(Color.decode("#FFFFFF"));
                panelRight.setBackground(Color.decode("#FFFFFF"));
                setBackground(Color.decode("#FFFFFF"));
            }
            return this;
        }

    }

    private static final class ListMouseAdapter extends MouseAdapter {

        private final MainWindow mw;
        private final DefaultListModel<BasketItem> listModel;
        private final JList<BasketItem> list;

        private ListMouseAdapter(MainWindow mw, JList<BasketItem> list) {
            this.mw = mw;
            this.listModel = (DefaultListModel<BasketItem>) list.getModel();
            this.list = list;
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                final int index = list.locationToIndex(evt.getPoint());

                if (index != -1) {
                    final BasketItem item = list.getSelectedValue();

                    new ItemEditDialog(mw, item, new ItemEditDialog.Callback() {

                        @Override
                        public void onDelete() {
                            listModel.removeElement(item);
                            mw.update();
                        }

                        @Override
                        public void onUpdate(int quantity) {
                            item.setQuantity(quantity);
                            mw.update();
                        }
                    });
                }
            }
        }

    }

    private static final class ItemEditDialog extends JDialog {

        private interface Callback {

            void onDelete();

            void onUpdate(int quantity);

        }

        private ItemEditDialog(MainWindow mw, BasketItem edit, Callback callback) {
            super(mw);
            final JPanel panel = new JPanel();
            final JPanel quantityLabelPanel = new JPanel();
            final JPanel bottomPanel = new JPanel();
            final JTextField quantityField = new JTextField();
            final JButton deleteButton = new JButton("Eliminar");
            final JButton saveButton = new JButton("Guardar");
            final ActionListener l = e -> {
                if (e.getSource() == deleteButton) {
                    dispose();
                    callback.onDelete();
                }
                else {
                    final int quantity;

                    try {
                        quantity = Integer.parseInt(quantityField.getText());

                        if (quantity < 0) {
                            throw new NumberFormatException();
                        }
                    }
                    catch (NumberFormatException ignore) {
                        JOptionPane.showMessageDialog(mw, "Porfavor ingresar un cantidad válida");
                        return;
                    }
                    dispose();
                    callback.onUpdate(quantity);
                }
            };

            quantityField.setText(String.valueOf(edit.getQuantity()));
            deleteButton.addActionListener(l);
            saveButton.addActionListener(l);
            panel.setBackground(Color.WHITE);
            quantityLabelPanel.setBackground(Color.WHITE);
            bottomPanel.setBackground(Color.WHITE);
            quantityLabelPanel.setLayout(new BorderLayout());
            quantityLabelPanel.add(new JLabel("Cantidad", SwingConstants.LEFT));
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            bottomPanel.add(deleteButton);
            bottomPanel.add(saveButton);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.add(quantityLabelPanel);
            panel.add(quantityField);
            panel.add(Box.createVerticalStrut(5));
            panel.add(bottomPanel);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setVisible(true);
        }

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

        private NewCustomerDialog(MainWindow mw, Callback callback) {
            super(mw, "Nuevo cliente");
            final JPanel panel = new JPanel();
            final JPanel genrePanel = new JPanel();
            final JPanel formPanel = new JPanel();
            final JPanel bottomPanel = new JPanel();
            final JTextField tf1 = new JTextField();
            final JTextField tf2 = new JTextField();
            final JTextField tf3 = new JTextField();
            final JTextField tf4 = new JTextField();
            final JComboBox<String> cb1 = new JComboBox<>();
            final JComboBox<String> cb2 = new JComboBox<>();
            final ButtonGroup buttonGroup = new ButtonGroup();
            final JRadioButton rb1 = new JRadioButton("M");
            final JRadioButton rb2 = new JRadioButton("F");
            final JRadioButton rb3 = new JRadioButton("Otro");
            final JButton cancelButton = new JButton("Cancelar");
            final JButton saveButton = new JButton("Guardar");
            final List<String> cities = callback.getCities();
            final List<String> communities = callback.getCommunities();
            final ActionListener l = e -> {
                if (e.getSource() == saveButton) {
                    if (!(isSet(tf1) && isSet(tf2) && isSet(tf3)) || getRadioValue(buttonGroup) == null
                        || cb1.getSelectedItem() == null || cb2.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(mw, "LLena todos los campos.");
                        return;
                    }
                    callback.createCustomer(
                        tf1.getText(),
                        tf2.getText(),
                        new Address(cb1.getSelectedItem().toString(), cb2.getSelectedItem().toString()),
                        tf3.getText(),
                        getRadioValue(buttonGroup),
                        tf4.getText()
                    );
                }
                dispose();
            };

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
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            bottomPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
            bottomPanel.setBackground(Color.WHITE);
            bottomPanel.add(cancelButton);
            bottomPanel.add(saveButton);
            panel.setLayout(new BorderLayout());
            panel.setBackground(Color.WHITE);
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.add(formPanel, BorderLayout.NORTH);
            panel.add(bottomPanel, BorderLayout.CENTER);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setVisible(true);
        }

    }
    private final Controller controller;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField rtnField;
    private final JTextField dateField;
    private final JComboBox<Product> productsCB;
    private final DefaultListModel<BasketItem> listModel;
    private final JList<BasketItem> list;
    private final JButton addButton;
    private final JLabel subtotalLabel;
    private final JLabel isvLabel;
    private final JLabel totalLabel;
    private final JCheckBox newCustomerCB;
    private final JButton printButton;

    public MainWindow(Controller controller, List<Product> productsList) {
        super("Factura");
        this.controller = controller;
        this.nameField = new JTextField();
        this.surnameField = new JTextField();
        this.rtnField = new JTextField();
        this.dateField = new JTextField();
        this.productsCB = new JComboBox<>();
        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);
        this.addButton = new JButton("Agregar");
        this.subtotalLabel = new JLabel();
        this.isvLabel = new JLabel();
        this.totalLabel = new JLabel();
        this.newCustomerCB = new JCheckBox("Nuevo cliente");
        this.printButton = new JButton("Imprimir");
        final JPanel panel = new JPanel();
        final JPanel inputPanel = new JPanel();
        final JPanel infoPanel = new JPanel();
        final JPanel infoWrapperPanel = new JPanel();
        final JPanel outputPanel = new JPanel();
        final JPanel bottomPanel = new JPanel();
        final GridBagConstraints gbc = new GridBagConstraints();
        final JScrollPane scroll = new JScrollPane(list);

        // Input panel
        productsList.forEach(productsCB::addItem);
        scroll.setPreferredSize(new Dimension(800, 300));
        addButton.addActionListener(this);
        printButton.addActionListener(this);
        dateField.setText(new SimpleDateFormat().format(new Date()));
        inputPanel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        gbc.insets.bottom = 5;
        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nombre"), gbc);
        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Apellido"), gbc);
        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(nameField, gbc);
        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(surnameField, gbc);
        gbc.gridwidth = 1;
        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 2;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("RTN"), gbc);
        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 3;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Fecha"), gbc);
        gbc.insets.left = 0;
        gbc.insets.right = 5;
        gbc.gridx = 2;
        gbc.gridy = 3;
        inputPanel.add(rtnField, gbc);
        gbc.insets.left = 5;
        gbc.insets.right = 0;
        gbc.gridx = 3;
        gbc.gridy = 3;
        inputPanel.add(dateField, gbc);
        gbc.gridx = 0;
        inputPanel.add(new JLabel(), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JLabel(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Producto"), gbc);
        gbc.gridx = 1;
        inputPanel.add(productsCB, gbc);
        gbc.gridx = 3;
        inputPanel.add(addButton, gbc);

        // List - Info - Output panel
        list.setCellRenderer(new ListRenderer());
        list.addMouseListener(new ListMouseAdapter(this, list));
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
        infoWrapperPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        infoWrapperPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.decode("#737373")));
        infoWrapperPanel.add(infoPanel);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(scroll, BorderLayout.NORTH);
        outputPanel.add(infoWrapperPanel, BorderLayout.CENTER);

        // Bottom panel
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        bottomPanel.add(newCustomerCB, BorderLayout.WEST);
        bottomPanel.add(printButton, BorderLayout.EAST);

        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(outputPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            final Product product = (Product) productsCB.getSelectedItem();
            final BasketItem basket = isAdded(product);

            if (basket != null) {
                basket.addNew();
                list.setSelectedValue(basket, true);
            }
            else {
                listModel.addElement(new BasketItem(product, 1));
            }
            update();
        }
        else if (e.getSource() == printButton) {
            if (!checkFields()) {
                JOptionPane.showMessageDialog(this, "Porfavor, llenar todos los campos.");
                return;
            }
            if (newCustomerCB.isSelected()) {
                new NewCustomerDialog(this, new NewCustomerDialog.Callback() {

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
                        String name, String surname, Address address, String phone,
                        String sex, String birthday
                    ) {
                        JOptionPane.showMessageDialog(MainWindow.this, "Cliente creado: " +
                                                                       name + " " + surname + " - " + address + " - " + phone + " - " + sex + " - " + birthday);
                        print();
                    }
                });
            }
            else {
                print();
            }
        }
    }

    private boolean checkFields() {
        return !(
            nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty()
            || rtnField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty()
        );
    }

    private BasketItem isAdded(Product product) {
        for (int i = 0; i < listModel.getSize(); i++) {
            if (listModel.getElementAt(i).getProduct() == product) {
                return listModel.elementAt(i);
            }
        }
        return null;
    }

    private void update() {
        BasketItem currentItem;
        double subtotal = 0;
        double isv = 0;
        double total = 0;

        for (int i = 0; i < listModel.getSize(); i++) {
            currentItem = listModel.getElementAt(i);
            subtotal += currentItem.getAmount();
            isv += currentItem.getISV();
            total += currentItem.getTotal();
        }
        subtotalLabel.setText("$" + DECIMAL_FORMAT.format(subtotal));
        isvLabel.setText("$" + DECIMAL_FORMAT.format(isv));
        totalLabel.setText("$" + DECIMAL_FORMAT.format(total));
    }

    private void print() {
        if (listModel.size() == 0) {
            JOptionPane.showMessageDialog(this, "No se han agregado elementos.");
            return;
        }
        final Customer customer = new Customer();
        final Bill bill = new Bill();
        BasketItem currentItem;

        customer.setName(nameField.getText());
        customer.setSurname(surnameField.getText());
        bill.setRtn(rtnField.getText());
        bill.setDate(dateField.getText());
        bill.setCustomer(customer);
        for (int i = 0; i < listModel.getSize(); i++) {
            currentItem = listModel.getElementAt(i);

            bill.addItem(currentItem);
        }
        final String title = "Factura - " + customer.getName();

        controller.save(bill);
        JOptionPane.showMessageDialog(this, bill, title, JOptionPane.INFORMATION_MESSAGE);
        nameField.setText("");
        surnameField.setText("");
        rtnField.setText("");
        subtotalLabel.setText("");
        isvLabel.setText("");
        totalLabel.setText("");
        listModel.removeAllElements();
    }

}
