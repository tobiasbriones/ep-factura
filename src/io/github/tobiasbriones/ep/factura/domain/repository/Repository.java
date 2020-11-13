/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<R, I> {

    Optional<R> get(I id);

    List<R> getAll(int page, int pageSize);

    void add(R record);

    void set(R record);

    void remove(R record);

}
