/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.core;

import javax.swing.*;

public abstract class JDialogMvcView<C> extends SwingMvcView<JDialog, C> {

    protected JDialogMvcView(C controller) {
        super(JDialog::new, controller);
    }

}
