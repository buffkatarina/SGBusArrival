package com.buffkatarina.busarrival.ui.fragments.search;

import android.content.Context;

import androidx.annotation.NonNull;

public class SearchView extends androidx.appcompat.widget.SearchView {

    public SearchView(@NonNull Context context) {
        super(context);

    }

    @Override
    public void onActionViewCollapsed() {
        clearFocus();
    }

}
