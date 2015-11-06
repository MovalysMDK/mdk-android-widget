/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.soprasteria.movalysmdk.widget.sample.custom_adapter.PresenterAdapter;

import java.util.ArrayList;

/**
 * Test activity for the MDKPresenter widget.
 */
public class PresenterActivity extends AbstractWidgetTestableActivity {
    /**
     *  The listView.
     */
    private ListView mListView;
    /**
     * The presenter adapter.
     */
    private PresenterAdapter mAdapter;
    /**
     * The random data.
     */
    private ArrayList mRandomData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter);
        getDummyData();
        this.mListView = (ListView) findViewById(R.id.presenter_list_view);
        this.mAdapter = new PresenterAdapter(this, mRandomData);
        this.mListView.setAdapter(mAdapter);

    }

    /**
     * To get the data.
     */
    private void getDummyData() {
        this.mRandomData.add("Aaaaaaaaaaaaaaaa");
        this.mRandomData.add("Bbbbbbbbbbbbbbb");
        this.mRandomData.add("Cccccccccccccccc");
        this.mRandomData.add("Ddddddddddddddd");
        this.mRandomData.add("Eeeeeeeeeeeeeeee");
        this.mRandomData.add("Ffffffffffffffff");
    }

    @Override
    protected int[] getWidgetIds() {
        return new int[0];
    }


}
