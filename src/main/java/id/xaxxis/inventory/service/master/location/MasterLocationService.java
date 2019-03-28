package id.xaxxis.inventory.service.master.location;


import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;

import java.util.List;

public interface MasterLocationService {
    List<MasterLocation> findAll();
    List<MasterLocation> findAllLocation();
    MasterLocation findByLocationName(String locationName);
    MasterLocation findByLocationId(String locationId);
    MasterLocation createLocation(MasterLocation masterLocation);
    MasterLocation save(MasterLocation masterLocation);

    Outlet saveOutlet(Outlet outlet);
    Outlet findByOutletId(String outletId);
    List<Outlet> findAllOutlet();
    List<Outlet> findAllOutletByLoc();
}
