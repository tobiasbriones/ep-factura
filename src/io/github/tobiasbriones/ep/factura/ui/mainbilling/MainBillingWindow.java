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

package io.github.tobiasbriones.ep.factura.ui.mainbilling;

import io.github.tobiasbriones.ep.factura.data.CityDao;
import io.github.tobiasbriones.ep.factura.data.CommunityDao;
import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketModel;
import io.github.tobiasbriones.ep.factura.domain.model.bill.BillAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.bill.BillModel;
import io.github.tobiasbriones.ep.factura.domain.model.bill.BillMutator;
import io.github.tobiasbriones.ep.factura.domain.model.customer.CustomerModel;
import io.github.tobiasbriones.ep.factura.domain.usecase.PrintBillUseCase;
import io.github.tobiasbriones.ep.factura.io.Printer;
import io.github.tobiasbriones.ep.factura.ui.core.SwingComponent;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.about.About;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.customer.CustomerCreationDialog;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.header.Header;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.items.Items;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.print.Print;
import io.github.tobiasbriones.ep.factura.ui.mainbilling.summary.Summary;
import res.Resource;

import javax.swing.*;
import java.awt.*;

public final class MainBillingWindow implements SwingComponent<JFrame> {

    interface Input {

        void setBill(BillMutator bill);

        void showBillPrintedDialog(BillModel bill);

        void showSetAllFieldsDialog();

        void showAboutDialog();

        void showCustomerCreationDialog(BillAccessor accessor);

    }

    interface ChildViewConfig {

        JPanel getHeaderViewComponent();

        JScrollPane getItemsViewComponent();

        JPanel getSummaryViewComponent();

        JPanel getPrintViewComponent();

        JPanel getAboutViewComponent();

    }

    public static MainBillingWindow newInstance(DependencyConfig config) {
        final var component = new MainBillingWindow(config);

        component.init();
        return component;
    }

    // Again, this would be a record class in Java 17+
    public static final class DependencyConfig {
        private final BasketModel basket;
        private final ProductDao productDao;
        private final CityDao cityDao;
        private final CommunityDao communityDao;

        public DependencyConfig(
            BasketModel basket,
            ProductDao productDao,
            CityDao cityDao,
            CommunityDao communityDao
        ) {
            this.basket = basket;
            this.productDao = productDao;
            this.cityDao = cityDao;
            this.communityDao = communityDao;
        }

        BasketModel basket() { return basket; }

        ProductDao productDao() { return productDao; }

        CityDao cityDao() { return cityDao; }

        CommunityDao communityDao() { return communityDao; }
    }

    private static final class ChildrenConfig implements ChildViewConfig {
        private static ChildrenConfig newInstance(DependencyConfig config) {
            return new ChildrenConfig(
                Header.newInstance(config.productDao()),
                Items.newInstance(config.basket()),
                Summary.newInstance(config.basket()),
                Print.newInstance(),
                About.newInstance()
            );
        }

        private final Header header;
        private final Items items;
        private final Summary summary;
        private final Print print;
        private final About about;

        private ChildrenConfig(
            Header header,
            Items items,
            Summary summary,
            Print print,
            About about
        ) {
            this.header = header;
            this.items = items;
            this.summary = summary;
            this.print = print;
            this.about = about;
        }

        @Override
        public JPanel getHeaderViewComponent() {
            return header.getViewComponent();
        }

        @Override
        public JScrollPane getItemsViewComponent() {
            return items.getViewComponent();
        }

        @Override
        public JPanel getSummaryViewComponent() {
            return summary.getViewComponent();
        }

        @Override
        public JPanel getPrintViewComponent() {
            return print.getViewComponent();
        }

        @Override
        public JPanel getAboutViewComponent() {
            return about.getViewComponent();
        }

        void initChildren(MainBillingMediator mediator) {
            mediator.onInitHeader(header);
            mediator.onInitItems(items);
            mediator.onInitSummary(summary);
            mediator.onInitPrint(print);
            mediator.onInitAbout(about);
        }

        void callHeaderSetBill(BillMutator bill) {
            header.onSetBill(bill);
        }
    }

    private final DependencyConfig config;
    private final ChildrenConfig childrenConfig;
    private final ComponentInput input;
    private final MainBillingMediator mediator;
    private final MainBillingController controller;
    private final MainBillingView view;

    private MainBillingWindow(DependencyConfig config) {
        this.config = config;
        this.childrenConfig = ChildrenConfig.newInstance(config);
        this.input = new ComponentInput();
        this.mediator = new MainBillingMediator(config.basket());
        this.controller = new MainBillingController();
        this.view = new MainBillingView(controller, childrenConfig);
    }

    @Override
    public JFrame getViewComponent() {
        return view.getViewComponent();
    }

    public void show() {
        view.show();
    }

    private void init() {
        mediator.setComponentInput(input);
        childrenConfig.initChildren(mediator);
        view.init();
        initController();
    }

    private void initController() {
        controller.setView(view);
        controller.init();
    }

    private final class ComponentInput implements Input {
        private ComponentInput() {}

        // This shouldn't go here. I put it because this example app ends right
        // here, and there's nothing else further after printing.
        @Override
        public void setBill(BillMutator bill) {
            childrenConfig.callHeaderSetBill(bill);
            bill.setBasket(config.basket());
        }

        @Override
        public void showBillPrintedDialog(BillModel bill) {
            final JFrame parent = getViewComponent();
            final var printer = new Printer(parent);
            final var printUseCase = new PrintBillUseCase(bill);

            printUseCase.execute(printer);
        }

        @Override
        public void showSetAllFieldsDialog() {
            final String msg = "Llena todos los campos.";
            final String title = "Entrada inválida";
            final JFrame parent = getViewComponent();
            final int type = JOptionPane.WARNING_MESSAGE;
            final String iconPath = Resource.getFileLocation("ic_warning_message.png");
            final Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(iconPath));
            JOptionPane.showMessageDialog(parent, msg, title, type, icon);
        }

        @Override
        public void showAboutDialog() {
            final var lineSeparator = System.lineSeparator();
            final String msg = "<html><strong>Example Project: Factura</strong></html>" + lineSeparator +
                               "Billing application made in Java-Swing." + lineSeparator +
                               "Great job by studying the Example Projects!" + lineSeparator + lineSeparator +
                               "© 2019-2020 Tobias Briones.";
            final String title = "Factura";
            final JFrame parent = getViewComponent();
            final int type = JOptionPane.INFORMATION_MESSAGE;
            final String iconPath = Resource.getFileLocation("icon.png");
            final Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(iconPath));
            JOptionPane.showMessageDialog(parent, msg, title, type, icon);
        }

        @Override
        public void showCustomerCreationDialog(BillAccessor accessor) {
            final CustomerCreationDialog dialog = newCustomerCreationDialog(accessor.getCustomer());

            showCustomerCreationDialog(dialog);
        }

        private void showCustomerCreationDialog(CustomerCreationDialog dialog) {
            mediator.onInitCustomerCreationDialog(dialog);
            dialog.show();
        }

        private CustomerCreationDialog newCustomerCreationDialog(CustomerModel customer) {
            final CityDao cityDao = config.cityDao();
            final CommunityDao communityDao = config.communityDao();
            return CustomerCreationDialog.newInstance(customer, cityDao, communityDao);
        }
    }

}
