/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.core;

import java.util.Optional;

public abstract class MvcController<View, Out> implements Mvc.Controller<View, Out> {

    private Out output;

    protected MvcController() {
        this.output = null;
    }

    @Override
    public final Optional<Out> getOutput() {
        return Optional.ofNullable(output);
    }

    @Override
    public final void setOutput(Out value) {
        output = value;
    }

}
