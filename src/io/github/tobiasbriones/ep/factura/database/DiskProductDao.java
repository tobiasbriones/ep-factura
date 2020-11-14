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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static List<Product> read() throws IOException {
        final List<Product> list = new ArrayList<>(getEstimatedInitialCapacity());

        try (var br = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                final var product = DiskProduct.readProductFrom(line);

                product.ifPresent(list::add);
            }
        }
        return list;
    }

    private static void save(List<Product> products) throws IOException {
        // TODO
    }

    private static final class DiskProduct {

        private static final int NUMBER_OF_ATTRIBUTES = 3;
        private static final String SEPARATOR_TOKEN = ",";

        static Optional<Product> readProductFrom(String productStr) {
            final var tokens = productStr.split(SEPARATOR_TOKEN);
            return readTokens(tokens);
        }

        private static Optional<Product> readTokens(String... tokens) {
            final Optional<Product> product;

            if (tokens.length == NUMBER_OF_ATTRIBUTES) {
                product = readTokenValues(tokens[0], tokens[1], tokens[2]);
            }
            else {
                product = Optional.empty();
            }
            return product;
        }

        private static Optional<Product> readTokenValues(
            String idToken,
            String descriptionToken,
            String priceToken
        ) {
            Optional<Product> product;
            final int id;
            final String description;
            final double price;

            try {
                id = Integer.parseInt(idToken);
                description = descriptionToken;
                price = Double.parseDouble(priceToken);
                product = Optional.of(Product.of(id, description, price));
            }
            catch (NumberFormatException | NullPointerException ignore) {
                product = Optional.empty();
            }
            return product;
        }

        private DiskProduct() {
        }

    }

    private final ProductDao inMemoryDao;

    public DiskProductDao() throws IOException {
        this.inMemoryDao = newInMemoryDao();
    }

    @Override
    public Optional<Product> fetch(IdProductAccessor id) {
        return inMemoryDao.fetch(id);
    }

    @Override
    public List<Product> fetchAll(int page, int pageSize) {
        return inMemoryDao.fetchAll(page, pageSize);
    }

    @Override
    public void create(Product record) {
        inMemoryDao.create(record);
        save();
    }

    @Override
    public void update(Product record) {
        inMemoryDao.update(record);
        save();
    }

    @Override
    public void delete(Product record) {
        inMemoryDao.delete(record);
        save();
    }

    private void save() {
        try {
            save(((InMemoryProductDao) inMemoryDao).getProducts());
        }
        catch (IOException e) {
            e.printStackTrace();
            // Returning a Result to handle errors from the DAO methods is left yet ...
        }
    }

}