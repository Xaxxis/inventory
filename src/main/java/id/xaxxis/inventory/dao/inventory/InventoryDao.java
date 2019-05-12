package id.xaxxis.inventory.dao.inventory;

import id.xaxxis.inventory.entity.inventory.Inventory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryDao extends DataTablesRepository<Inventory, String> {
    List<Inventory> findAllByMasterLocation_LocationId(String locationId);

    List<Inventory> findAll();

    List<Inventory> findAllByMasterLocation_LocationIdAndAndMasterItem_ItemId(String loactionId, String itemId);

    List<Inventory> findAllByMasterItem_CategoryItem_CategoryIdAndMasterLocation_LocationId(String categoryId, String locationId);


    DataTablesOutput<Inventory> findAll(DataTablesInput dataTablesInput, Specification<Inventory> specification);

    DataTablesOutput<Inventory> findAll(DataTablesInput input);

    DataTablesOutput<Inventory> findAllByOutlet_OutletId(String outletId);


    @Override
    Optional<Inventory> findById(String invId);

}
