package id.xaxxis.inventory.dao.master.location;

import id.xaxxis.inventory.entity.master.location.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutletDao extends JpaRepository<Outlet, String> {
    List<Outlet> findAllByMasterLocation_LocationId(String locationId);
    List<Outlet> findAllByOrderByOutletId();
    Outlet findByOutletId(String outletId);

}
