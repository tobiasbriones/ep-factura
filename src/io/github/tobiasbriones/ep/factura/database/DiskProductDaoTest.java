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

import io.github.tobiasbriones.ep.factura.domain.model.product.ProductModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

final class DiskProductDaoTest {

    //                                                                                            //
    //                                                                                            //
    //                                           CLASS                                            //
    //                                                                                            //
    //                                                                                            //

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TMP_FILE_NAME = "products_test";

    public static void main(String[] args) {
        final var test = new DiskProductDaoTest();

        test.run();
    }

    @FunctionalInterface
    interface TestCase {

        void run() throws Exception;

    }

    private static void it(String name, TestCase testCase) {
        try {
            testCase.run();
            passed(name);
        }
        catch (Exception e) {
            failed(name, e.getMessage());
        }
        clean();
    }

    private static void passed(String name) {
        final var ansiGreenColor = "\u001B[32m";
        final var ansiColorReset = "\u001B[0m";
        final var msg = ansiGreenColor
                        + "\u2714 "
                        + ansiColorReset
                        + name
                        + ansiGreenColor
                        + " passed"
                        + ansiColorReset;
        System.out.println(msg);
    }

    private static void failed(String name, String exceptionMsg) {
        final var msg = "\u2718 " + name + " failed: " + exceptionMsg;
        System.err.println(msg);
    }

    private static void assertTrue(boolean condition) throws Exception {
        if (!condition) {
            throw new Exception();
        }
    }

    private static void clean() {
        try {
            Files.delete(Path.of(TMP_FILE_NAME));
        }
        catch (NoSuchFileException ignore) {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //                                                                                            //
    //                                                                                            //
    //                                          INSTANCE                                          //
    //                                                                                            //
    //                                                                                            //

    private DiskProductDaoTest() {}

    private void run() {
        it("createsFileIfNotExists", this::createsFileIfNotExists);
        it("onlyReadsValidItems", this::onlyReadsValidItems);
        it("readsItems", this::readsItems);
        it("saves", this::saves);
        it("fetchesById", this::fetchesById);
        it("deletesItem", this::deletesItem);
        it("updatesItem", this::updatesItem);
        it("createsItem", this::createsItem);
    }

    private void createsFileIfNotExists() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);

        if (Files.notExists(Path.of(TMP_FILE_NAME))) {
            throw new Exception();
        }
    }

    private void readsItems() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var item1 = "1,Desc1,100.0";
        final var item2 = "15,Desc15,10.5";
        final var contents = item1 + LINE_SEPARATOR + item2;

        Files.writeString(Path.of(TMP_FILE_NAME), contents);
        final var expected1 = ProductModel.of(1, "Desc1", 100.0);
        final var expected2 = ProductModel.of(15, "Desc15", 10.5);
        final var result = dao.fetchAll(0, 2);

        assertTrue(result.get(0).equals(expected1));
        assertTrue(result.get(1).equals(expected2));
    }

    private void onlyReadsValidItems() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var item1 = "1,Desc1,100.0";
        final var item2 = "15,Desc15,10.5";
        final var invalidItem = "1s,Desc,10.0";
        final var contents = item1 + LINE_SEPARATOR + item2 + LINE_SEPARATOR + invalidItem;

        Files.writeString(Path.of(TMP_FILE_NAME), contents);
        final var expected1 = ProductModel.of(1, "Desc1", 100.0);
        final var expected2 = ProductModel.of(15, "Desc15", 10.5);
        final var result = dao.fetchAll(0, 3);

        assertTrue(result.size() == 2);
        assertTrue(result.get(0).equals(expected1));
        assertTrue(result.get(1).equals(expected2));
    }

    private void saves() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var product = ProductModel.of(1, "Description", 50.0);

        dao.create(product);

        final var expected = "1,Description,50.0" + LINE_SEPARATOR;
        final var result = Files.readString(Path.of(TMP_FILE_NAME));

        assertTrue(result.equals(expected));
    }

    private void fetchesById() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var item1 = "1,Desc1,100.0";
        final var item2 = "15,Desc15,10.5";
        final var item3 = "1500,Desc1500,100.5";
        final var item4 = "9,Desc9,20.0";
        final var contents = item1 + LINE_SEPARATOR +
                             item2 + LINE_SEPARATOR +
                             item3 + LINE_SEPARATOR +
                             item4 + LINE_SEPARATOR;

        Files.writeString(Path.of(TMP_FILE_NAME), contents);
        final var expected = ProductModel.of(15, "Desc15", 10.5);
        final var result = dao.fetch(() -> 15);

        assertTrue(result.isPresent() && result.get().equals(expected));
    }

    private void deletesItem() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var item1 = "1,Desc1,100.0";
        final var deleteItem = "15,Desc15,10.5";
        final var item3 = "100,Desc100,70.0";
        final var contents = item1 + LINE_SEPARATOR + deleteItem + LINE_SEPARATOR + item3;

        Files.writeString(Path.of(TMP_FILE_NAME), contents);
        final var expected1 = ProductModel.of(1, "Desc1", 100.0);
        final var expected2 = ProductModel.of(100, "Desc100", 70.0);
        final var expectedSize = 2;
        final var result = dao.fetchAll(0, 10);

        assertTrue(result.size() == expectedSize);
        assertTrue(result.get(0).equals(expected1));
        assertTrue(result.get(1).equals(expected2));
    }

    private void updatesItem() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var item1 = "1,Desc1,100.0";
        final var updateItem = "15,Desc15,10.5";
        final var item3 = "100,Desc100,70.0";
        final var contents = item1 + LINE_SEPARATOR + updateItem + LINE_SEPARATOR + item3;

        Files.writeString(Path.of(TMP_FILE_NAME), contents);
        final var expected1 = ProductModel.of(1, "Desc1", 100.0);
        final var expected2 = ProductModel.of(15, "Desc50", 559.99);
        final var expected3 = ProductModel.of(100, "Desc100", 70.0);
        final var expectedSize = 3;

        dao.update(expected2);
        final var result = dao.fetchAll(0, 10);

        assertTrue(result.size() == expectedSize);
        assertTrue(result.get(0).equals(expected1));
        assertTrue(result.get(1).equals(expected2));
        assertTrue(result.get(2).equals(expected3));
    }

    private void createsItem() throws Exception {
        final var dao = new DiskProductDao(TMP_FILE_NAME);
        final var expected = ProductModel.of(1, "Desc1", 100.0);
        final var expectedSize = 1;

        dao.create(expected);
        final var result = dao.fetchAll(0, 10);

        assertTrue(result.size() == expectedSize);
        assertTrue(result.get(0).equals(expected));
    }

}
