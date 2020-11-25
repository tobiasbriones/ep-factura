package io.github.tobiasbriones.ep.factura.ui.core;

import java.awt.*;

@FunctionalInterface
public interface SwingView<V extends Component> {

    V getComponent();

}
