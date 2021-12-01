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

package res;

import java.io.File;

public final class Data {
    private static final String FILE_SEPARATOR = File.separator;
    private static final String DATA_ROOT_PATH = "." + FILE_SEPARATOR + "data" + FILE_SEPARATOR;

    public static String getFileLocation(String dataFileLocation) {
        return DATA_ROOT_PATH + dataFileLocation;
    }

    private Data() {}
}
