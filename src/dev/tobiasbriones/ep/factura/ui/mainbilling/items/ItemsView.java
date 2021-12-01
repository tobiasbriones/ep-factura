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

package dev.tobiasbriones.ep.factura.ui.mainbilling.items;

import dev.tobiasbriones.ep.factura.ui.core.JScrollPaneMvcView;
import dev.tobiasbriones.ep.factura.ui.core.rx.Observer;
import dev.tobiasbriones.ep.factura.ui.mainbilling.items.editor.ItemEditor;
import dev.tobiasbriones.ep.factura.domain.model.basket.BasketItem;
import dev.tobiasbriones.ep.factura.domain.model.basket.StreamableBasketItems;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

final class ItemsView extends JScrollPaneMvcView<ItemsController> implements Observer {

    private static final int HEIGHT_PX = 300;
    private static final int WIDTH_PX = 800;
    private static final DecimalFormat decimalFormat = new DecimalFormat(".##");

    private static final class ListRenderer extends JPanel implements ListCellRenderer<BasketItem> {
        private static final int ITEM_WIDTH = 600;
        private static final int ITEM_HEIGHT = 30;
        private static final Color BACKGROUND_COLOR = Color.decode("#FFFFFF");
        private static final Color SELECTED_BACKGROUND_COLOR = Color.decode("#EAEAEA");
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
                panelLeft.setBackground(SELECTED_BACKGROUND_COLOR);
                panelRight.setBackground(SELECTED_BACKGROUND_COLOR);
                setBackground(SELECTED_BACKGROUND_COLOR);
            }
            else {
                panelLeft.setBackground(BACKGROUND_COLOR);
                panelRight.setBackground(BACKGROUND_COLOR);
                setBackground(BACKGROUND_COLOR);
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

    private final StreamableBasketItems model;
    private final DefaultListModel<BasketItem> listModel;
    private final JList<BasketItem> list;

    ItemsView(ItemsController controller, StreamableBasketItems model) {
        super(controller);
        this.model = model;
        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);
    }

    @Override
    public void createView(JScrollPane view) {
        list.setCellRenderer(new ListRenderer());
        view.setPreferredSize(new Dimension(WIDTH_PX, HEIGHT_PX));
        view.setViewportView(list);
    }

    @Override
    public void bindEvents(ItemsController controller) {
        list.addMouseListener(new ListMouseAdapter());
    }

    @Override
    public void update() {
        setItems();
    }

    private void setItems() {
        listModel.clear();
        model.stream().forEach(listModel::addElement);
    }

    private final class ListMouseAdapter extends MouseAdapter {
        private ListMouseAdapter() {
            super();
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
            showEditDialog(item);
        }

        private void showEditDialog(BasketItem item) {
            final var dialogComponent = ItemEditor.newInstance(item);

            dialogComponent.setOutput(getController());
            dialogComponent.show();
        }
    }

}
