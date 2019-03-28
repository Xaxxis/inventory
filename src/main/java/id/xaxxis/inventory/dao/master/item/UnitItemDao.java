package id.xaxxis.inventory.dao.master.item;

import id.xaxxis.inventory.entity.master.item.UnitItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitItemDao extends PagingAndSortingRepository<UnitItem, String> {
    UnitItem findByUnitId(String unitId);
    UnitItem findByUnitName(String unitName);
    List<UnitItem> findAll();
}
