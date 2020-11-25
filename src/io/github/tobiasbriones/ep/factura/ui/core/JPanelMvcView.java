/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.core;

import javax.swing.*;

public abstract class JPanelMvcView<C> extends SwingMvcView<JPanel, C> {

    protected JPanelMvcView(C presenter) {
        super(JPanel::new, presenter);
    }

}
