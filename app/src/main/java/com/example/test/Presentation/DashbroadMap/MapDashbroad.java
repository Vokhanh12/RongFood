package com.example.test.Presentation.DashbroadMap;

import android.content.Context;
import android.os.AsyncTask;
import com.example.test.Model.Location;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Map;

public class MapDashbroad {

    private Location locationStart;

    private Location locationEnd;

    private ArrayList<GeoPoint> arlWaypoints = new ArrayList<>();

    private Context mContex;

    private MapView map;

    public MapDashbroad(Location locationFrist, Location locationSecord, Context mContex, MapView map) {
        this.locationStart = locationFrist;
        this.locationEnd = locationSecord;
        this.mContex = mContex;
        this.map = map;

    }

    public void Findway() {

        //get LocationStart and Location End
        arlWaypoints.add(new GeoPoint(this.locationStart.getLatitude(),this.locationStart.getLongitude())); // Start point
        arlWaypoints.add(new GeoPoint(this.locationEnd.getLatitude(),this.locationEnd.getLongitude())); // End point

        RoadManagerTask roadManagerTask = new RoadManagerTask();
        roadManagerTask.execute(arlWaypoints);

        // Create a bounding box around the two GeoPoints
        BoundingBox boundingBox = BoundingBox.fromGeoPoints(arlWaypoints);
        // Set the bounding box on the map view
        map.zoomToBoundingBox(boundingBox, true);

    }

    private class RoadManagerTask extends AsyncTask<ArrayList<GeoPoint>, Void, Road> {

        @Override
        protected Road doInBackground(ArrayList<GeoPoint>... params) {


            String userAgent = System.getProperty("http.agent");
            ArrayList<GeoPoint> waypoints = params[0];
            RoadManager roadManager = new OSRMRoadManager(mContex, userAgent);

            return roadManager.getRoad(waypoints);


        }

        @Override
        protected void onPostExecute(Road road) {
            // Update UI with road data
            // Draw the road on the map
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
            roadOverlay.setWidth(10f);
            map.getOverlays().add(roadOverlay);
            map.invalidate();


            //double lengthInMeters = roadOverlay.getDistance();

           // tvHello.setText((int)lengthInMeters+" Mét");


        }

    }
}
