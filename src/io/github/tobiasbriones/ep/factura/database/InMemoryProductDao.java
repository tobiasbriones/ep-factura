/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.database;

import io.github.tobiasbriones.ep.factura.data.ProductDao;
import io.github.tobiasbriones.ep.factura.domain.model.product.IdProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class InMemoryProductDao implements ProductDao {

    private static final int ESTIMATED_NUMBER_OF_PRODUCTS = 1000;

    @FunctionalInterface
    private interface InMemoryProductFetcher {

        /**
         * Fetches the product from the product list and the given product code.
         *
         * @param productList list of in memory products
         * @param productCode code of the product to fetch
         *
         * @return the product if found, null otherwise
         */
        Product fetch(List<Product> productList, int productCode);

    }

    private final List<Product> products;

    public InMemoryProductDao() {
        this.products = new ArrayList<>(ESTIMATED_NUMBER_OF_PRODUCTS);

        init();
    }

    @Override
    public Optional<Product> fetch(IdProductAccessor id) {
        return fetchByCode(id.getCode());
    }

    @Override
    public List<Product> fetchAll(int page, int pageSize) {
        if (page < 0 || pageSize < 0) {
            final var msg = "Page and Page Size are non-negative integers";
            throw new RuntimeException(msg);
        }
        final var indexStart = page * pageSize;
        final var indexEnd = indexStart + pageSize;
        return fetchRange(indexStart, indexEnd);
    }

    @Override
    public void create(Product record) {
        products.add(record);
    }

    @Override
    public void update(Product record) {
        if (isInBounds(record.getCode())) {
            products.set(record.getCode(), record);
        }
    }

    @Override
    public void delete(Product record) {
        if (isInBounds(record.getCode())) {
            products.remove(record.getCode());
        }
    }

    private void init() {
        for (int i = 0; i < ESTIMATED_NUMBER_OF_PRODUCTS; i++) {
            final var description = "Product " + i + " description";
            final var price = Math.random() * 1500.0d;

            products.add(Product.of(i, description, price));
        }
    }

    private boolean isInBounds(int index) {
        return index >= 0 && index < products.size();
    }

    private Optional<Product> fetchByCode(int code) {
        final InMemoryProductFetcher fetcher = (
            productList,
            productCode
        ) -> isInBounds(productCode) ? productList.get(productCode) : null;
        return Optional.ofNullable(fetcher.fetch(products, code));
    }

    private List<Product> fetchRange(int indexStart, int indexEnd) {
        final var productsFound = new ArrayList<Product>(indexEnd - indexStart);

        for (int i = indexStart; i < indexEnd; i++) {
            if (!isInBounds(i)) {
                break;
            }
            productsFound.add(products.get(i));
        }
        return Collections.unmodifiableList(productsFound);
    }

}
