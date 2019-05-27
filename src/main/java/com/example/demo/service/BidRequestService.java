package com.example.demo.service;

import com.example.demo.model.BidRequest;
import com.example.demo.model.BidRequestItem;
import com.example.demo.model.SupplierResponse;
import com.example.demo.repository.BidRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BidRequestService {

    @Autowired
    private BidRequestRepository bidRequestRepository;

    @Autowired
    private BidRequestItemService bidRequestItemService;

    @Autowired
    private SupplierResponseService supplierResponseService;

    public BidRequest saveBidRequest(BidRequest request) {
        request.setCreatedAt(new Date());
        BidRequest bidRequest = bidRequestRepository.save(request);
        List<BidRequestItem> bidRequestItems = bidRequestItemService.saveBidRequestItem(request.getBidRequestItemList(), bidRequest);
        bidRequest.setBidRequestItemList(bidRequestItems);
        return bidRequest;
    }

    public BidRequest getBidRequestById(Long id) {
        BidRequest bidRequest = bidRequestRepository.getBidRequestById(id);
        if (bidRequest != null) {
            if (bidRequest.getEndDate().before(new Date())) {
                bidRequest.setExpired(true);
            }
            List<BidRequestItem> bidRequestItems = bidRequestItemService.getByBidRequestId(bidRequest.getId());
            formBidItems(bidRequestItems);
            bidRequest.setBidRequestItemList(bidRequestItems);
        }
        return bidRequest;
    }

    public void publishBidToBidders(Long bidRequestId) {
        BidRequest bidRequest = bidRequestRepository.getBidRequestById(bidRequestId);
        //publishing bids through emails at start time
    }

    public List<BidRequest> getAllActiveBidRequests() {
        Date date = new Date();
        List<BidRequest> bidRequests = bidRequestRepository.getBidRequestByStartDateBeforeAndEndDateAfter(date, date);
        List<BidRequestItem> bidRequestItems = bidRequestItemService.getBidRequestItemByBidRequestIds(bidRequests.stream().map(BidRequest::getId).collect(Collectors.toList()));
        Map<Long, List<BidRequestItem>> bidRequestItemMap = bidRequestItems.stream().collect(Collectors.groupingBy(bidRequestItem -> bidRequestItem.getBidRequest().getId()));
        bidRequests.forEach(bidRequest -> bidRequest.setBidRequestItemList(bidRequestItemMap.get(bidRequest.getId())));
        return bidRequests;

    }

    public List<BidRequest> getAllActiveBidRequest(Boolean active, Boolean inactive) {
        List<BidRequest> bidRequests = new ArrayList<>();
        if (active) {
            bidRequests.addAll(getAllActiveBidRequests());
        }
        if (inactive) {
            bidRequests.addAll(bidRequestRepository.getBidRequestByEndDateBefore(new Date()));
        }
        List<Long> bidRequestsIds = bidRequests.stream().map(BidRequest::getId).collect(Collectors.toList());
        List<BidRequestItem> bidRequestItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bidRequestsIds)) {
            bidRequestItems = bidRequestItemService.getBidRequestItemByBidRequestIds(bidRequestsIds);
            formBidItems(bidRequestItems);
        }
        Map<Long, List<BidRequestItem>> bidRequestItemMap = bidRequestItems.stream().collect(Collectors.groupingBy(bidRequestItem -> bidRequestItem.getBidRequest().getId()));
        bidRequests.forEach(bidRequest -> bidRequest.setBidRequestItemList(bidRequestItemMap.get(bidRequest.getId())));
        return bidRequests;
    }

    private void formBidItems(List<BidRequestItem> bidRequestItems){
        List<SupplierResponse> supplierResponses = supplierResponseService.getSupplierResponseByBidRequestItem(bidRequestItems.stream().map(BidRequestItem::getId).collect(Collectors.toList()));
        Map<Long, List<SupplierResponse>> supplierResponseMap = supplierResponses.stream().collect(Collectors.groupingBy(supplierResponse -> supplierResponse.getBidRequestItem().getId()));
        supplierResponseMap.forEach((key, value) -> {
            AtomicInteger i = new AtomicInteger(1);
            Collections.sort(value.stream().map(SupplierResponse::getPerUnitQuantity).collect(Collectors.toList()));
            value.forEach(supplierResponse -> {
                supplierResponse.setRank(i.getAndIncrement());
            });
        });
        bidRequestItems.forEach(bidRequestItem -> bidRequestItem.setSupplierResponseList(supplierResponseMap.get(bidRequestItem.getId())));
    }


}
