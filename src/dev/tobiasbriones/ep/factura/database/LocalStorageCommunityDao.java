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

package dev.tobiasbriones.ep.factura.database;

import dev.tobiasbriones.ep.factura.data.CommunityDao;
import dev.tobiasbriones.ep.factura.database.util.FileUtils;
import dev.tobiasbriones.ep.factura.domain.model.city.community.Community;
import res.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LocalStorageCommunityDao implements CommunityDao {

    private static final String FILE_NAME = Data.getFileLocation("communities");
    private static final int INITIAL_CAPACITY = 15;
    private final List<Community> cities;
    private boolean isUpToDate;

    public LocalStorageCommunityDao() {
        this.cities = new ArrayList<>(INITIAL_CAPACITY);
        this.isUpToDate = false;
    }

    @Override
    public List<Community> fetchAll() {
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
                 .map(Community::new)
                 .forEach(cities::add);
    }

}
