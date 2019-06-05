package com.example.demo.controller;

import com.example.demo.model.SupplierResponse;
import com.example.demo.service.BidRequestService;
import com.example.demo.service.SupplierResponseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("suppliers/")
public class SupplierController {

    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private SupplierResponseService supplierResponseService;

    @ApiOperation(value = "To get all Active Bid Requests")
    @GetMapping("get_all")
    public ResponseEntity<Object> getAllBids(String name) {
        return ResponseEntity.ok(bidRequestService.getAllActiveBidsBySupplierName(name));
    }

    @ApiOperation(value = "To place a bid for item")
    @PostMapping("bid")
    public ResponseEntity<Object> bid(@RequestBody SupplierResponse supplierResponse) {
        supplierResponseService.saveBid(supplierResponse);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "To get rank by bidItemId")
    @GetMapping("get_rank")
    public ResponseEntity<Object> getRank(Long bidItemLineId){
        return ResponseEntity.ok(supplierResponseService.getResponseByBidItemId(bidItemLineId));
    }


}
