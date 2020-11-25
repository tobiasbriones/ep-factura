/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.core;

import java.awt.*;

public final class SwingComponent<V extends Component> implements SwingView<V> {

    private final V view;

    public SwingComponent(SwingView<V> view) {
        this.view = view.getComponent();
    }

    @Override
    public V getComponent() {
        return view;
    }

}