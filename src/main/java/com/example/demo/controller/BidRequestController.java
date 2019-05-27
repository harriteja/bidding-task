package com.example.demo.controller;

import com.example.demo.model.BidRequest;
import com.example.demo.service.BidRequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager/")
public class BidRequestController {

    @Autowired
    private BidRequestService bidRequestService;

    @ApiOperation(value = "To save the bid request")
    @PostMapping("save")
    public ResponseEntity<Object> saveRequest(@RequestBody BidRequest bidRequest) {
        try {
            return ResponseEntity.ok(bidRequestService.saveBidRequest(bidRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "To publish the bid to suppliers by SMS or EMAIL notification")
    @PostMapping("publish")
    public ResponseEntity<Object> publish(@RequestParam Long requestId) {
        try {
            bidRequestService.publishBidToBidders(requestId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "To know status of bid by bidRequestId")
    @GetMapping("get")
    public ResponseEntity<Object> getBidRequest(@RequestParam Long bidRequestId) {
        try {
            return ResponseEntity.ok(bidRequestService.getBidRequestById(bidRequestId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "To get all bids active or inactive")
    @GetMapping("get_all")
    public ResponseEntity<Object> getAllActiveBidRequest(@RequestParam Boolean active, @RequestParam Boolean inactive) {
        return ResponseEntity.ok(bidRequestService.getAllActiveBidRequest(active, inactive));
    }

}
