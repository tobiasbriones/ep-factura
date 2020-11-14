/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Default implementation of {@link Basket} based on a {@link LinkedHashSet}.
 */
public final class BasketList extends AbstractBasket {

    private static final int DEF_INITIAL_CAPACITY = 15;
    private final Set<BasketItem> items;

    public BasketList(int initialCapacity) {
        super();
        this.items = new LinkedHashSet<>(initialCapacity);
    }

    public BasketList() {
        this(DEF_INITIAL_CAPACITY);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void remove(BasketItem basketItem) {
        items.remove(basketItem);
    }

    @Override
    public Iterator<BasketItem> iterator() {
        return items.iterator();
    }

    @Override
    protected boolean contains(BasketItem basketItem) {
        return items.contains(basketItem);
    }

    @Override
    protected void pushItem(BasketItem basketItem) {
        items.add(basketItem);
    }

}