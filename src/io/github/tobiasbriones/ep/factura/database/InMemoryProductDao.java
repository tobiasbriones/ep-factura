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

package io.github.tobiasbriones.ep.factura.database;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.product.IdProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import java.util.*;

public final class InMemoryProductDao implements ProductDao {

    private static final int ESTIMATED_NUMBER_OF_PRODUCTS = 1000;
    private final Map<Integer, ProductModel> products;

    public InMemoryProductDao() {
        this.products = new HashMap<>(ESTIMATED_NUMBER_OF_PRODUCTS);

        init();
    }

    /**
     * Special constructor to let {@link DiskProductDao} load a list of products
     * from the local file.
     *
     * @param products list of products to assign to this product DAO, a new
     *                 copy of the products is created
     */
    InMemoryProductDao(Collection<ProductModel> products) {
        this.products = new HashMap<>(products.size());

        products.forEach(product -> this.products.put(product.getCode(), product));
    }

    /**
     * Special getter so that {@link DiskProductDao} can perform its operations.
     *
     * @return the unmodifiable list of products of this product DAO
     */
    List<ProductModel> getProducts() {
        return List.copyOf(products.values());
    }

    @Override
    public Optional<ProductModel> fetch(IdProductAccessor id) {
        return fetchByCode(id.getCode());
    }

    @Override
    public List<ProductModel> fetchAll(int page, int pageSize) {
        if (page < 0 || pageSize < 0) {
            final var msg = "Page and Page Size are non-negative integers";
            throw new RuntimeException(msg);
        }
        final var indexStart = page * pageSize;
        final var indexEnd = indexStart + pageSize;
        return fetchRangeByCode(indexStart, indexEnd);
    }

    @Override
    public void create(ProductModel record) {
        products.put(record.getCode(), record);
    }

    @Override
    public void update(ProductModel record) {
        if (isInBounds(record.getCode())) {
            products.replace(record.getCode(), record);
        }
    }

    @Override
    public void delete(ProductModel record) {
        if (isInBounds(record.getCode())) {
            products.remove(record.getCode());
        }
    }

    private void init() {
        for (int i = 0; i < ESTIMATED_NUMBER_OF_PRODUCTS; i++) {
            final var description = "Product " + i + " description";
            final var price = Math.random() * 1500.0d;

            products.put(i, ProductModel.of(i, description, price));
        }
    }

    private boolean isInBounds(int productCode) {
        return products.containsKey(productCode);
    }

    private Optional<ProductModel> fetchByCode(int code) {
        return Optional.ofNullable(products.get(code));
    }

    private List<ProductModel> fetchRangeByCode(int productCodeStart, int productCodeEnd) {
        final var productsFound = new ArrayList<ProductModel>(productCodeEnd - productCodeStart);

        for (int i = productCodeStart; i < productCodeEnd; i++) {
            if (!isInBounds(i)) {
                continue;
            }
            productsFound.add(products.get(i));
        }
        return Collections.unmodifiableList(productsFound);
    }

}
