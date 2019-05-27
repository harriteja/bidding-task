package com.example.demo.controller;

import com.example.demo.model.SupplierResponse;
import com.example.demo.service.BidRequestService;
import com.example.demo.service.SupplierResponseService;
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

    @GetMapping("get_all")
    public ResponseEntity<Object> getAllBids() {
        return ResponseEntity.ok(bidRequestService.getAllActiveBidRequests());
    }

    @PostMapping("bid")
    public ResponseEntity<Object> bid(@RequestBody SupplierResponse supplierResponse) {
        supplierResponseService.saveBid(supplierResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get_rank")
    public ResponseEntity<Object> getRank(Long bidItemLineId){
        return ResponseEntity.ok(supplierResponseService.getResponseByBidItemId(bidItemLineId));
    }


}
