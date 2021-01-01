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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.items.editor;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import io.github.tobiasbriones.ep.factura.ui.core.JDialogMvcView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

final class ItemEditorView extends JDialogMvcView<ItemEditorController> {

    private final BasketItem item;
    private final JTextField quantityField;
    private final JButton deleteButton;
    private final JButton saveButton;

    ItemEditorView(ItemEditorController controller, BasketItem item) {
        super(controller);
        this.item = item;
        this.quantityField = new JTextField();
        this.deleteButton = new JButton();
        this.saveButton = new JButton();
    }

    @Override
    public void createView(JDialog view) {
        final var panel = new JPanel();
        final var quantityLabelPanel = new JPanel();
        final var bottomPanel = new JPanel();

        deleteButton.setText("Eliminar");
        saveButton.setText("Guardar");
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

        view.getContentPane().add(panel);
        view.pack();
        view.setLocationRelativeTo(null);
        view.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    @Override
    public void bindEvents(ItemEditorController controller) {
        deleteButton.addActionListener(e -> controller.onDelete(item));
        saveButton.addActionListener(e -> retrieveQuantity(controller::onUpdate));
    }

    @Override
    public void update() {
        quantityField.setText(String.valueOf(item.getQuantity()));
        getView().setVisible(true);
    }

    void onDestroy() {
        getView().dispose();
    }

    private void retrieveQuantity(Consumer<? super Integer> consumer) {
        try {
            final int quantity = Integer.parseInt(quantityField.getText());
            consumer.accept(quantity);
        }
        catch (NumberFormatException e) {
            showQuantityFieldInvalid();
        }
    }

    private void showQuantityFieldInvalid() {
        final var msg = "Porfavor ingresar una cantidad válida";
        JOptionPane.showMessageDialog(getView(), msg);
    }

}
