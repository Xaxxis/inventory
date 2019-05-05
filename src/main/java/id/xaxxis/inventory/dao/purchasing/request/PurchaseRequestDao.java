package id.xaxxis.inventory.dao.purchasing.request;

import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRequestDao extends DataTablesRepository<PurchaseRequest, String> {

    @Override
    long count();

    long countByRequestStatus(RequestStatus status);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM purchase_request pr WHERE pr.created_date like %:createdDate%")
    long countByUseDate(@Param("createdDate")String date);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_request pr WHERE pr.created_date like %:createdDate%")
    Page<PurchaseRequest> findAllByCreatedDate(@Param("createdDate")String date,
                                               Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_request pr WHERE pr.created_date like %:createdDate% AND pr.location_id:locationId")
    List<PurchaseRequest> findAllByCreatedDateAndLocationId(@Param("createdDate")String date, @Param("locationId") String locationId);

    @Query(nativeQuery = true, value = "SELECT * FROM purchase_request pr WHERE pr.created_date like %:createdDate% AND pr.location_id:locationId AND pr.user_id:userId")
    Page<PurchaseRequest> findAllByCreatedDateAndLocationIdAndUserId(@Param("createdDate") String date,
                                                                     @Param("locationId") String locationId,
                                                                     @Param("userId") String userId,
                                                                     Pageable pageable);
    @Override
    DataTablesOutput<PurchaseRequest> findAll(DataTablesInput dataTablesInput);

    @Override
    DataTablesOutput<PurchaseRequest> findAll(DataTablesInput dataTablesInput, Specification<PurchaseRequest> specification);

    PurchaseRequest findByPurchaseRequestNumber(String prNumber);

    PurchaseRequest findByPurchaseReqId(String id);

}
