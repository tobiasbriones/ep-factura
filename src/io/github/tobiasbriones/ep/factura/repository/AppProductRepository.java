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

package io.github.tobiasbriones.ep.factura.repository;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.product.IdProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;
import io.github.tobiasbriones.ep.factura.domain.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public final class AppProductRepository implements ProductRepository {

    private final ProductDao productDao;

    public AppProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Optional<ProductModel> get(IdProductAccessor id) {
        return productDao.fetch(id);
    }

    @Override
    public List<ProductModel> getAll(int page, int pageSize) {
        return productDao.fetchAll(page, pageSize);
    }

    @Override
    public void add(ProductModel record) {
        productDao.create(record);
    }

    @Override
    public void set(ProductModel record) {
        productDao.update(record);
    }

    @Override
    public void remove(ProductModel record) {
        productDao.delete(record);
    }

}
