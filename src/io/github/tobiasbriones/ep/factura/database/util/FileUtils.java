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

package io.github.tobiasbriones.ep.factura.database.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    private static final int DEF_FILE_LINES_INITIAL_CAPACITY = 15;

    /**
     * Returns a list containing the lines of the given file.
     *
     * @param fileName file name to read
     *
     * @return a list containing the lines of the given file
     *
     * @throws IOException if something fails
     * @see FileUtils#readFile(String, int)
     */
    public static List<String> readFile(String fileName) throws IOException {
        return readFile(fileName, DEF_FILE_LINES_INITIAL_CAPACITY);
    }

    /**
     * Returns a list containing the lines of the given file.
     *
     * @param fileName        file name to read
     * @param initialCapacity initial capacity to assign to the the list before
     *                        reading the file, use this param to improve
     *                        performance
     *
     * @return a list containing the lines of the given file
     *
     * @throws IOException if something fails
     */
    public static List<String> readFile(String fileName, int initialCapacity) throws IOException {
        final List<String> list = new ArrayList<>(initialCapacity);

        try (var br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();

            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
        }
        return list;
    }

    /**
     * Writes the lines into the given file.
     *
     * @param lines    list of lines to write into the file
     * @param fileName file name to write into
     *
     * @throws IOException if something fails
     */
    public static void saveLines(Iterable<String> lines, String fileName) throws IOException {
        try (var bw = new BufferedWriter(new FileWriter(fileName))) {
            for (var line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    private FileUtils() {}

}
