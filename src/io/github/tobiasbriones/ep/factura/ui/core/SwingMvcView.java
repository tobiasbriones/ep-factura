/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.core;

import java.awt.*;
import java.util.function.Supplier;

public abstract class SwingMvcView<V extends Component, C> extends MvcView<V, C> implements SwingView<V> {

    SwingMvcView(Supplier<V> view, C controller) {
        super(view, controller);
    }

    @Override
    public final V getComponent() {
        return getView();
    }

}
