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

package io.github.tobiasbriones.ep.factura.ui.mainbilling.items;

import io.github.tobiasbriones.ep.factura.domain.model.basket.BasketItemModel;

public final class Items {

    @FunctionalInterface
    public interface Output {

        void onItemUpdated(BasketItemModel item);

    }

    private Items() {}

}
