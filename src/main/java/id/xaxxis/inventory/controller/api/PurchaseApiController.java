package id.xaxxis.inventory.controller.api;

import id.xaxxis.inventory.dao.purchasing.order.PurchaseOrderDao;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.service.purchasing.order.PurchaseOrderService;
import id.xaxxis.inventory.service.purchasing.request.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/purchasing")
public class PurchaseApiController {

    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    public PurchaseApiController(PurchaseRequestService purchaseRequestService,
                                 PurchaseOrderService purchaseOrderService) {
        this.purchaseRequestService = purchaseRequestService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @RequestMapping(value = "/request/prlist", method = RequestMethod.GET)
    public DataTablesOutput<PurchaseRequest> prdataList(@Valid DataTablesInput input) {
        return purchaseRequestService.findAll(input);
    }

    @RequestMapping(value = "/order/poList", method = RequestMethod.GET)
    public DataTablesOutput<PurchaseOrder> poDataList(@Valid DataTablesInput input){
        return purchaseOrderService.findAll(input);
    }

    @RequestMapping(value = "/order/listsss", method = RequestMethod.GET)
    public List testList(){
        return (List) purchaseOrderDao.findAll();
    }

}
