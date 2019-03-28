package id.xaxxis.inventory.service.master.location;

import id.xaxxis.inventory.dao.master.location.MasterLocationDao;
import id.xaxxis.inventory.dao.master.location.OutletDao;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.entity.master.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterLocationServiceImpl implements MasterLocationService {
    @Autowired
    private MasterLocationDao masterLocationDao;

    @Autowired
    private OutletDao outletDao;

    @Override
    public List<MasterLocation> findAll() {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String locationId = user.getMasterLocation().getLocationId();
        if(!locationId.equals("ff8080816985d94101698633a8ad0000")){
            return masterLocationDao.findAllByLocationId(locationId);
        }
        return masterLocationDao.findAll();
    }

    @Override
    public List<MasterLocation> findAllLocation() {
        return masterLocationDao.findAll();
    }

    @Override
    public MasterLocation findByLocationName(String locationName) {
        return masterLocationDao.findByLocationName(locationName);
    }

    @Override
    public MasterLocation findByLocationId(String locationId) {
        return masterLocationDao.findByLocationId(locationId);
    }

    @Override
    public MasterLocation createLocation(MasterLocation masterLocation) {
        return masterLocationDao.save(masterLocation);
    }

    @Override
    public MasterLocation save(MasterLocation masterLocation) {
        return masterLocationDao.save(masterLocation);
    }

    @Override
    public Outlet saveOutlet(Outlet outlet) {
        return outletDao.save(outlet);
    }

    @Override
    public Outlet findByOutletId(String outletId) {
        return outletDao.findByOutletId(outletId);
    }

    @Override
    public List<Outlet> findAllOutlet() {
        return outletDao.findAllByOrderByOutletId();
    }

    @Override
    public List<Outlet> findAllOutletByLoc() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String locationID = user.getMasterLocation().getLocationId();
        if(locationID.equals("ff8080816985d94101698633a8ad0000")){
            return outletDao.findAllByOrderByOutletId();
        }
        return outletDao.findAllByMasterLocation_LocationId(locationID);
    }
}
