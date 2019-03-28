package id.xaxxis.inventory.dao.master.item;

import id.xaxxis.inventory.entity.master.item.MasterItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterItemDao extends PagingAndSortingRepository<MasterItem, String> {
    MasterItem findByItemBarcode(String itemBarcode);
    MasterItem findByItemId(String itemId);

    @Query(value = "SELECT * FROM master_item as i ORDER BY i.category_id", nativeQuery = true)
    List<MasterItem> findAll();
    List<MasterItem> findAllByCategoryItem_CategoryName(String categoryName);

}
