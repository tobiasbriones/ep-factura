/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.data;

import java.util.List;
import java.util.Optional;

public interface Dao<R, I> {

    Optional<R> fetch(I id);

    List<R> fetchAll(int page, int pageSize);

    void create(R record);

    void update(R record);

    void delete(R record);

}
