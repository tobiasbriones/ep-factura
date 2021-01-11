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

import java.io.File;
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

    private DiskProductDaoTest() {}

}
