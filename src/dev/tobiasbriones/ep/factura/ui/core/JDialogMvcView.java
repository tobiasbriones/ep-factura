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

package dev.tobiasbriones.ep.factura.ui.core;

import javax.swing.*;

public abstract class JDialogMvcView<C> extends SwingMvcView<JDialog, C> {
    protected JDialogMvcView(C controller) {
        super(JDialog::new, controller);
    }

    public final void show() {
        getView().setVisible(true);
    }

    public final void hide() {
        getView().setVisible(false);
    }

    public final void dispose() {
        getViewComponent().dispose();
    }
}
