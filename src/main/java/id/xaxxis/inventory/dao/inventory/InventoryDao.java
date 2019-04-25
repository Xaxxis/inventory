package id.xaxxis.inventory.dao.inventory;

import id.xaxxis.inventory.entity.inventory.Inventory;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryDao extends DataTablesRepository<Inventory, String> {
    List<Inventory> findAllByMasterLocation_LocationId(String locationId);
    List<Inventory> findAll();
    List<Inventory> findAllByMasterLocation_LocationIdAndAndMasterItem_ItemId(String loactionId, String itemId);
    List<Inventory> findAllByMasterItem_CategoryItem_CategoryIdAndMasterLocation_LocationId(String categoryId, String locationId);

    Inventory findByInventoryId_ItemIdAndInventoryId_LocationIdAndInventoryId_OutletId(String itemId, String locationId, String outletId);
    Inventory findByMasterItem_ItemBarcodeAndInventoryId_LocationIdAndInventoryId_OutletId(String barcode, String locationId, String outletId);
}
