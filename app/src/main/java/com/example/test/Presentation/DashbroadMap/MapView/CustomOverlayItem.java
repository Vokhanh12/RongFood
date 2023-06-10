package com.example.test.Presentation.DashbroadMap.MapView;

import com.example.test.Model.Store;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class CustomOverlayItem extends OverlayItem {
    private Store storeData;

    public CustomOverlayItem(Store storeData, String title, String snippet, GeoPoint geoPoint) {
        super(title, snippet, geoPoint);
        this.storeData = storeData;
    }

    public Store getStoreData() {
        return storeData;
    }
}