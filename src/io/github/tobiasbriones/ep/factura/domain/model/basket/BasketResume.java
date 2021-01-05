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

package io.github.tobiasbriones.ep.factura.domain.model.basket;

import java.util.Objects;

public final class BasketResume implements BasketResumeModel {

    private final Double isv;
    private final Double subtotal;
    private final Double total;

    BasketResume(double isv, double subtotal, double total) {
        this.isv = isv;
        this.subtotal = subtotal;
        this.total = total;
    }

    @Override
    public double getIsv() {
        return isv;
    }

    @Override
    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "BasketResume[" +
               "isv=" + isv + ", " +
               "subtotal=" + subtotal + ", " +
               "total=" + total + ", " +
               "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BasketResumeModel basketResume = (BasketResumeModel) obj;
        return Double.compare(basketResume.getIsv(), isv) == 0 &&
               Double.compare(
                   basketResume.getSubtotal(),
                   subtotal
               ) == 0 &&
               Double.compare(basketResume.getTotal(), total) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isv, subtotal, total);
    }

}
