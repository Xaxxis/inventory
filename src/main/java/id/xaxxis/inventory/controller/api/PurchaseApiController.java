package id.xaxxis.inventory.controller.api;

import id.xaxxis.inventory.dao.purchasing.PurchaseRequestDao;
import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequest;
import id.xaxxis.inventory.service.purchasing.pr.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/purchasing")
public class PurchaseApiController {

    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseRequestDao purchaseRequestDao;

    @Autowired
    public PurchaseApiController(PurchaseRequestService purchaseRequestService,
                                 PurchaseRequestDao purchaseRequestDao) {
        this.purchaseRequestService = purchaseRequestService;
        this.purchaseRequestDao = purchaseRequestDao;
    }

    @RequestMapping(value = "/request/prlist", method = RequestMethod.GET)
    public DataTablesOutput<PurchaseRequest> prdataList(@Valid DataTablesInput input) {
        return purchaseRequestService.findAll(input);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity test(){
        ResponseEntity local = new ResponseEntity(purchaseRequestDao.findAll(), HttpStatus.OK);
        return local;

    }
}