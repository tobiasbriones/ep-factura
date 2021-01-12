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

public final class Resource {

    private static final String FILE_SEPARATOR = File.separator;
    private static final String RES_ROOT_PATH = "." + FILE_SEPARATOR + "res" + FILE_SEPARATOR;

    public static String getFileLocation(String resLocation) {
        return RES_ROOT_PATH + resLocation;
    }

    private Resource() {}

}
