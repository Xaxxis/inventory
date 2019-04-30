package id.xaxxis.inventory.service.purchasing.pr;

import id.xaxxis.inventory.dto.purchasing.PurchaseRequestCart;
import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequestItem;
import id.xaxxis.inventory.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;
import java.util.Map;

public interface PurchaseRequestService {

    long countByStatus(RequestStatus status);

    void addItem(PurchaseRequestCart prCart, String itemId);

    void removeItem(String itemId);

    void updateItem(PurchaseRequestCart prCart, String itemId);

    Map<String, PurchaseRequestCart> getItemInCart();

    void createPurchaseRequest(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem);

    String generatePrNumber();

    DataTablesOutput<PurchaseRequest> findAll(DataTablesInput input);

    Page<PurchaseRequest> findAllByCreatedDate(String date, Pageable pageable);

    PurchaseRequest findByPrNumber(String prNumber);

    PurchaseRequest findByPrId(String prId);

    List<PurchaseRequestItem> findAllItemReq(String id);

    PurchaseRequestItem deleteItemReq(String id);

}
