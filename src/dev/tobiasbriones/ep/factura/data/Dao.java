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

package dev.tobiasbriones.ep.factura.data;

import java.util.List;
import java.util.Optional;

public interface Dao<R, I> {
    Optional<R> fetch(I id);

    List<R> fetchAll(int page, int pageSize);

    void create(R record);

    void update(R record);

    void delete(R record);
}
