/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.domain.repository;

import io.github.tobiasbriones.ep.factura.domain.model.product.IdProductAccessor;
import io.github.tobiasbriones.ep.factura.domain.model.product.Product;

public interface ProductRepository extends Repository<Product, IdProductAccessor> {}