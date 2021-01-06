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

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Default implementation of {@link BasketModel} based on a {@link LinkedHashSet}.
 */
public final class BasketList extends AbstractBasket {

    private static final int DEF_INITIAL_CAPACITY = 15;
    private final Set<BasketItem> items;

    public BasketList() {
        this(DEF_INITIAL_CAPACITY);
    }

    public BasketList(int initialCapacity) {
        super();
        this.items = new LinkedHashSet<>(initialCapacity);
    }

    @Override
    public Stream<BasketItem> stream() {
        return items.stream();
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean remove(BasketItem item) {
        return items.remove(item);
    }

    @Override
    protected boolean contains(BasketItem item) {
        return items.contains(item);
    }

    @Override
    protected void pushItem(BasketItem item) {
        items.add(item);
    }

}
