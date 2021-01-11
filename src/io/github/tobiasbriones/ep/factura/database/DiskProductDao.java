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
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DiskProductDao implements ProductDao {

    public static final String DEF_PRODUCTS_FILE_PATH = "products";
    private static final int ESTIMATED_INITIAL_CAPACITY = 50;

    private static final class DiskProduct {
        private static final int NUMBER_OF_ATTRIBUTES = 3;
        private static final String SEPARATOR_TOKEN = ",";

        static Optional<ProductModel> readProductFrom(String productStr) {
            final var tokens = productStr.split(SEPARATOR_TOKEN);
            return readTokens(tokens);
        }

        static String writeProductFrom(ProductAccessor accessor) {
            return accessor.getCode() + SEPARATOR_TOKEN +
                   accessor.getDescription() + SEPARATOR_TOKEN +
                   accessor.getPrice();
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
            final int id;
            final String description;
            final double price;
            Optional<ProductModel> product;

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

    private final String filePath;
    private final ProductDao inMemoryDao;

    public DiskProductDao() throws IOException {
        this(DEF_PRODUCTS_FILE_PATH);
    }

    public DiskProductDao(String filePath) throws IOException {
        this.filePath = filePath;
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

    private InMemoryProductDao newInMemoryDao() throws IOException {
        final var products = read();
        return new InMemoryProductDao(products);
    }

    private List<ProductModel> read() throws IOException {
        return FileUtils.readFile(filePath, ESTIMATED_INITIAL_CAPACITY)
                        .stream()
                        .map(DiskProduct::readProductFrom)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
    }

    private void save() {
        // Suppose the max capacity allowed is ESTIMATED_INITIAL_CAPACITY
        final List<ProductModel> allProducts = fetchAll(0, ESTIMATED_INITIAL_CAPACITY);
        final List<String> encodedProducts = allProducts.stream()
                                                        .map(DiskProduct::writeProductFrom)
                                                        .collect(Collectors.toList());

        try {
            FileUtils.saveLines(encodedProducts, filePath);
        }
        catch (IOException e) {
            // This Dao should throw exceptions in a real use case
            e.printStackTrace();
        }
    }

}
