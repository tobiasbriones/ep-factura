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
import io.github.tobiasbriones.ep.factura.database.util.FileUtils;
import io.github.tobiasbriones.ep.factura.domain.model.product.IdProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DiskProductDao implements ProductDao {

    private static final String PRODUCTS_FILE_PATH = "products";
    private static final int ESTIMATED_INITIAL_CAPACITY = 50;

    private static int getEstimatedInitialCapacity() {
        return ESTIMATED_INITIAL_CAPACITY;
    }

    private static InMemoryProductDao newInMemoryDao() throws IOException {
        final var products = read();
        return new InMemoryProductDao(products);
    }

    private static List<ProductModel> read() throws IOException {
        return FileUtils.readFile(PRODUCTS_FILE_PATH, getEstimatedInitialCapacity())
                        .stream()
                        .map(DiskProduct::readProductFrom)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
    }

    private static final class DiskProduct {
        private static final int NUMBER_OF_ATTRIBUTES = 3;
        private static final String SEPARATOR_TOKEN = ",";

        private static Optional<ProductModel> readProductFrom(String productStr) {
            final var tokens = productStr.split(SEPARATOR_TOKEN);
            return readTokens(tokens);
        }

        private static Optional<ProductModel> readTokens(String... tokens) {
            final Optional<ProductModel> product;

            if (tokens.length == NUMBER_OF_ATTRIBUTES) {
                product = readTokenValues(tokens[0], tokens[1], tokens[2]);
            }
            else {
                product = Optional.empty();
            }
            return product;
        }

        private static Optional<ProductModel> readTokenValues(
            String idToken,
            String descriptionToken,
            String priceToken
        ) {
            Optional<ProductModel> product;
            final int id;
            final String description;
            final double price;

            try {
                id = Integer.parseInt(idToken);
                description = descriptionToken;
                price = Double.parseDouble(priceToken);
                product = Optional.of(ProductModel.of(id, description, price));
            }
            catch (NumberFormatException | NullPointerException ignore) {
                product = Optional.empty();
            }
            return product;
        }

        private DiskProduct() {}
    }

    private final ProductDao inMemoryDao;

    public DiskProductDao() throws IOException {
        this.inMemoryDao = newInMemoryDao();
    }

    @Override
    public Optional<ProductModel> fetch(IdProductAccessor id) {
        return inMemoryDao.fetch(id);
    }

    @Override
    public List<ProductModel> fetchAll(int page, int pageSize) {
        return inMemoryDao.fetchAll(page, pageSize);
    }

    @Override
    public void create(ProductModel record) {
        inMemoryDao.create(record);
        save();
    }

    @Override
    public void update(ProductModel record) {
        inMemoryDao.update(record);
        save();
    }

    @Override
    public void delete(ProductModel record) {
        inMemoryDao.delete(record);
        save();
    }

    private void save() {
        // TODO
    }

}
