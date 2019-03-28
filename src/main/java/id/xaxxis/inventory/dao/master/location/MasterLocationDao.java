package id.xaxxis.inventory.dao.master.location;

import id.xaxxis.inventory.entity.master.location.MasterLocation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterLocationDao extends PagingAndSortingRepository<MasterLocation, String> {
    List<MasterLocation> findAll();
    MasterLocation findByLocationName(String locationName);
    MasterLocation findByLocationId(String locationId);
    List<MasterLocation> findAllByLocationId(String locationId);
}
