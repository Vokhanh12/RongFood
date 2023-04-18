package com.example.test.Data.LocationDAO;

import com.example.test.Model.Location;

public interface LocationDAO {
    public boolean addLocation(Location location);
    public void updateLocation(Location location);
    public  void deleteLocation(Location location);

}
