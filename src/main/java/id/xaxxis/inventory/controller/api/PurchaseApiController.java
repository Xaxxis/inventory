package id.xaxxis.inventory.controller.api;

import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.service.purchasing.request.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/purchasing")
public class PurchaseApiController {

    private final PurchaseRequestService purchaseRequestService;

    @Autowired
    public PurchaseApiController(PurchaseRequestService purchaseRequestService) {
        this.purchaseRequestService = purchaseRequestService;
    }

    @RequestMapping(value = "/request/prlist", method = RequestMethod.GET)
    public DataTablesOutput<PurchaseRequest> prdataList(@Valid DataTablesInput input) {
        return purchaseRequestService.findAll(input);
    }

}
