/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.repository;

import io.github.tobiasbriones.ep.factura.domain.model.product.IdProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;
import io.github.tobiasbriones.ep.factura.domain.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public final class AppProductRepository implements ProductRepository {


    public AppProductRepository() {
    }

    @Override
    public Optional<Product> get(IdProductAccessor id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll(int page, int pageSize) {
        return null;
    }

    @Override
    public void add(Product record) {

    }

    @Override
    public void set(Product record) {

    }

    @Override
    public void remove(Product record) {

    }

}
