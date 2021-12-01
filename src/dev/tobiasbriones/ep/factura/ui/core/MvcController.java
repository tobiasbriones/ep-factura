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

import java.util.Optional;

public abstract class MvcController<View, Output> implements Mvc.Controller<View, Output> {

    private Output output;

    protected MvcController() {
        this.output = null;
    }

    @Override
    public final Optional<Output> getOutput() {
        return Optional.ofNullable(output);
    }

    @Override
    public final void setOutput(Output value) {
        output = value;
    }

}
