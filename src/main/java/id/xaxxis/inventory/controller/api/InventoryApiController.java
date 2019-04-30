package id.xaxxis.inventory.controller.api;

import id.xaxxis.inventory.entity.inventory.Inventory;
import id.xaxxis.inventory.service.inventory.InventoryService;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/app/inventory")
public class InventoryApiController {

    private final InventoryService inventoryService;

    public InventoryApiController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DataTablesOutput<Inventory> inventoryList(@Valid DataTablesInput input) {
        return inventoryService.findAllApi(input);
    }
}
