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

import io.github.tobiasbriones.ep.factura.data.CityDao;
import io.github.tobiasbriones.ep.factura.database.util.FileUtils;
import io.github.tobiasbriones.ep.factura.domain.model.city.City;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LocalStorageCityDao implements CityDao {

    private static final String FILE_NAME = "cities";
    private static final int INITIAL_CAPACITY = 15;
    private final List<City> cities;
    private boolean isUpToDate;

    public LocalStorageCityDao() {
        this.cities = new ArrayList<>(INITIAL_CAPACITY);
        this.isUpToDate = false;
    }

    @Override
    public List<City> fetchAll() {
        if (!isUpToDate) {
            loadCities();
        }
        return Collections.unmodifiableList(cities);
    }

    private void loadCities() {
        cities.clear();

        try {
            readCitiesFileAndLoad();
            isUpToDate = true;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCitiesFileAndLoad() throws IOException {
        FileUtils.readFile(FILE_NAME, INITIAL_CAPACITY)
                 .stream()
                 .map(City::new)
                 .forEach(cities::add);
    }

}
