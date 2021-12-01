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

package dev.tobiasbriones.ep.factura.domain.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<R, I> {

    Optional<R> get(I id);

    List<R> getAll(int page, int pageSize);

    void add(R record);

    void set(R record);

    void remove(R record);

}
