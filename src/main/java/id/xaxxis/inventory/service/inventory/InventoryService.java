package id.xaxxis.inventory.service.inventory;

import id.xaxxis.inventory.entity.inventory.Inventory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    Inventory saveInv(Inventory inventory);
    List<Inventory> findAll();
    List<Inventory> findAllByMasterLocation(String locationId);
    List<Inventory> findAllBylocationAndItem(String locationId, String itemId);
    List<Inventory> findAllByItemCategoryAndLocation(String categoryId);

    Inventory findByInventoryId(String itemId, String outletId);

    Optional<Inventory> findByInvId(String invId);

    DataTablesOutput<Inventory> findAllApi(DataTablesInput input);

    DataTablesOutput<Inventory> findAllByOutlet(String outletId);
}
