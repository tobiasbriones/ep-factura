/*
 * Copyright (c) 2019 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.domain.model.bill.Bill;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Address;
import io.github.tobiasbriones.ep.factura.domain.model.customer.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class MainWindow extends JFrame implements ActionListener {

    //                                                                                            //
    //                                                                                            //
    //                                           CLASS                                            //
    //                                                                                            //
    //                                                                                            //

    private static final int WIDTH = 800;
    private static final int HEIGHT = 300;
    private static final DecimalFormat decimalFormat = new DecimalFormat(".##");

    public interface Controller {

        List<String> getCities();

        List<String> getCommunities();

        void save(Bill bill);

    }

    private static final class ListRenderer extends JPanel implements ListCellRenderer<BasketItem> {

        private static final int ITEM_WIDTH = 600;
        private static final int ITEM_HEIGHT = 30;
        private final JPanel panelLeft;
        private final JPanel panelRight;
        private final JLabel quantityLabel;
        private final JLabel productCodeLabel;
        private final JLabel productDescriptionLabel;
        private final JLabel priceLabel;

        private ListRenderer() {
            super();
            this.panelLeft = new JPanel();
            this.panelRight = new JPanel();
            this.quantityLabel = new JLabel();
            this.productCodeLabel = new JLabel();
            this.productDescriptionLabel = new JLabel();
            this.priceLabel = new JLabel();

            init();
        }

        @Override
        public Component getListCellRendererComponent(
            JList<? extends BasketItem> list,
            BasketItem value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
        ) {
            quantityLabel.setText(String.valueOf(value.getQuantity()));
            productCodeLabel.setText(String.valueOf(value.getProduct().getCode()));
            productDescriptionLabel.setText(value.getProduct().getDescription());
            priceLabel.setText("$" + decimalFormat.format(value.getAmount()));

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

        private void init() {
            final var quantityLabelWidth = 40;
            final var quantityLabelHeight = 20;
            final var priceLabelWidth = 140;
            final var priceLabelHeight = 20;

            quantityLabel.setPreferredSize(new Dimension(quantityLabelWidth, quantityLabelHeight));
            quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            panelLeft.setBackground(Color.decode("#FFFFFF"));
            panelLeft.add(quantityLabel);
            panelLeft.add(productCodeLabel);
            panelLeft.add(productDescriptionLabel);

            priceLabel.setPreferredSize(new Dimension(priceLabelWidth, priceLabelHeight));
            priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            panelRight.setBackground(Color.decode("#FFFFFF"));
            panelRight.add(priceLabel);

            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
            setBorder(new EmptyBorder(0, 10, 0, 10));
            setOpaque(true);
            add(panelLeft, BorderLayout.LINE_START);
            add(panelRight, BorderLayout.LINE_END);
        }

    }

    private static final class ListMouseAdapter extends MouseAdapter {

        private final MainWindow mw;
        private final DefaultListModel<BasketItem> listModel;
        private final JList<BasketItem> list;

        private ListMouseAdapter(MainWindow mw, JList<BasketItem> list) {
            super();
            this.mw = mw;
            this.listModel = (DefaultListModel<BasketItem>) list.getModel();
            this.list = list;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                final int index = list.locationToIndex(e.getPoint());

                if (index != -1) {
                    final BasketItem item = list.getSelectedValue();

                    handleDoubleClickOn(item);
                }
            }
        }

        private void handleDoubleClickOn(BasketItem item) {
            final var callback = new MyCallback(item);
            new ItemEditDialog(mw, item, callback);
        }

        private class MyCallback implements ItemEditDialog.Callback {

            private final BasketItem item;

            private MyCallback(BasketItem item) {
                this.item = item;
            }

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

        }

    }

    private static final class ItemEditDialog extends JDialog {

        private interface Callback {

            void onDelete();

            void onUpdate(int quantity);

        }

        private final MainWindow mw;
        private final BasketItem edit;
        private final Callback callback;
        private final JTextField quantityField;
        private final JButton deleteButton;
        private final JButton saveButton;

        private ItemEditDialog(MainWindow mw, BasketItem edit, Callback callback) {
            super(mw);
            this.mw = mw;
            this.edit = edit;
            this.callback = callback;
            this.quantityField = new JTextField();
            this.deleteButton = new JButton("Eliminar");
            this.saveButton = new JButton("Guardar");

            init();
        }

        private void init() {
            final JPanel panel = new JPanel();
            final JPanel quantityLabelPanel = new JPanel();
            final JPanel bottomPanel = new JPanel();
            final var l = new ClickListener();

            quantityField.setText(String.valueOf(edit.getQuantity()));
            deleteButton.addActionListener(l);
            saveButton.addActionListener(l);
            panel.setBackground(Color.WHITE);
            quantityLabelPanel.setBackground(Color.WHITE);
            bottomPanel.setBackground(Color.WHITE);
            quantityLabelPanel.setLayout(new BorderLayout());
            quantityLabelPanel.add(new JLabel("Cantidad", SwingConstants.LEFT));
            bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 0));
            bottomPanel.add(deleteButton);
            bottomPanel.add(saveButton);
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
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

        private final class ClickListener implements ActionListener {

            private ClickListener() {
            }

            private int getQuantityInt() {
                final int quantity = Integer.parseInt(quantityField.getText());

                if (quantity < 0) {
                    throw new NumberFormatException();
                }
                return quantity;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteButton) {
                    dispose();
                    callback.onDelete();
                }
                else {
                    final int quantity;

                    try {
                        quantity = getQuantityInt();
                    }
                    catch (NumberFormatException ignore) {
                        JOptionPane.showMessageDialog(mw, "Porfavor ingresar una cantidad válida");
                        return;
                    }
                    dispose();
                    callback.onUpdate(quantity);
                }
            }

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

    public MainWindow(Controller controller, Iterable<Product> products) {
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

        init(products);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            handleAddButtonClick();
        }
        else if (e.getSource() == printButton) {
            handlePrintButtonClick();
        }
    }

    private void handlePrintButtonClick() {
        if (!checkFields()) {
            JOptionPane.showMessageDialog(this, "Porfavor, llenar todos los campos.");
            return;
        }
        if (newCustomerCB.isSelected()) {
            new NewCustomerDialog(this, new NewCustomerDialogCallback());
        }
        else {
            print();
        }
    }

    private void handleAddButtonClick() {
        final Product product = (Product) productsCB.getSelectedItem();
        final BasketItem basket = isAdded(product);

        if (basket != null) {
            basket.incrementQuantity();
            list.setSelectedValue(basket, true);
        }
        else {
            listModel.addElement(new BasketItem(product, 1));
        }
        update();
    }

    private void init(Iterable<Product> products) {
        final var panel = new JPanel();
        final var inputPanel = new JPanel();
        final var infoPanel = new JPanel();
        final var infoWrapperPanel = new JPanel();
        final var outputPanel = new JPanel();
        final var bottomPanel = new JPanel();
        final var gbc = new GridBagConstraints();
        final var scroll = new JScrollPane(list);

        // Input panel
        products.forEach(productsCB::addItem);
        scroll.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addButton.addActionListener(this);
        printButton.addActionListener(this);
        dateField.setEnabled(false);
        dateField.setText(LocalDate.now().toString());
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
        double subtotal = 0.0d;
        double isv = 0.0d;
        double total = 0.0d;

        for (int i = 0; i < listModel.getSize(); i++) {
            currentItem = listModel.getElementAt(i);
            subtotal += currentItem.getAmount();
            isv += currentItem.getISV();
            total += currentItem.getTotal();
        }
        subtotalLabel.setText("$" + decimalFormat.format(subtotal));
        isvLabel.setText("$" + decimalFormat.format(isv));
        totalLabel.setText("$" + decimalFormat.format(total));
    }

    private void print() {
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se han agregado elementos.");
            return;
        }
        final Customer customer = new Customer();
        final Bill bill = new Bill();
        BasketItem currentItem;

        customer.setName(nameField.getText());
        customer.setSurname(surnameField.getText());
        bill.setRtn(rtnField.getText());
        bill.setDate(LocalDateTime.now());
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
            print();
        }

    }

}
