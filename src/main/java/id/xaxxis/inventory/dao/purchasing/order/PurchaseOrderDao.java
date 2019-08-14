package id.xaxxis.inventory.dao.purchasing.order;

import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseOrderDao extends DataTablesRepository<PurchaseOrder, String> {

    Optional<PurchaseOrder> findPurchaseOrderByPrNumber(String PrNumber);

    DataTablesOutput<PurchaseOrder> findAll(DataTablesInput input);

    DataTablesOutput<PurchaseOrder> findAll(DataTablesInput input, Specification<PurchaseOrder> specification);


    Optional<PurchaseOrder> findByPrNumber(String prNumber);

}
