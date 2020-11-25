/*
 * Copyright (c) 2020 Tobias Briones.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.github.tobiasbriones.ep.factura.ui.mainbilling.items.editor;

import io.github.tobiasbriones.ep.factura.ui.core.MvcController;

import static io.github.tobiasbriones.ep.factura.ui.mainbilling.items.editor.ItemEditor.Output;

final class ItemEditorController extends MvcController<ItemEditorView, Output> implements Output {

    private ItemEditorView view;

    ItemEditorController() {
        super();
        this.view = null;
    }

    @Override
    public ItemEditorView getView() {
        return view;
    }

    @Override
    public void setView(ItemEditorView value) {
        view = value;
    }

    @Override
    public void init() {
        view.update();
    }

    @Override
    public void onDelete() {
        getOutput().ifPresent(Output::onDelete);
        view.onDestroy();
    }

    @Override
    public void onUpdate(int quantity) {
        getOutput().ifPresent(output -> output.onUpdate(quantity));
        view.onDestroy();
    }

}
