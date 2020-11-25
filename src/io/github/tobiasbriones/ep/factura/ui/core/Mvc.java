/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.core;

import java.util.Optional;

final class Mvc {

    interface View<V, C> extends Initializable {

        void createView(V view);

        default void bindEvents(C controller) {}

        default void clear() {}

        default void update() {}

    }

    interface Controller<View, Output> extends Initializable {

        View getView();

        void setView(View value);

        Optional<Output> getOutput();

        void setOutput(Output value);

    }

    private Mvc() {}

}
