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

import java.util.function.Supplier;

public abstract class MvcView<V, C> implements Mvc.View<V, C> {

    private final V view;
    private final C controller;

    MvcView(Supplier<V> viewSupplier, C controller) {
        this.view = viewSupplier.get();
        this.controller = controller;
    }

    public final V getView() {
        return view;
    }

    protected final C getController() {
        return controller;
    }

    @Override
    public final void init() {
        createView(view);
        bindEvents(controller);
    }

}
