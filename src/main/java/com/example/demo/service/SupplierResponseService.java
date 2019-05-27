package com.example.demo.service;

import com.example.demo.model.BidRequestItem;
import com.example.demo.model.SupplierResponse;
import com.example.demo.repository.SupplierResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierResponseService {

    @Autowired
    private BidRequestItemService bidRequestItemService;

    @Autowired
    private SupplierResponseRepository supplierResponseRepository;

    public void saveBid(SupplierResponse supplierResponse) {
        Optional<SupplierResponse> existingSupplierResponse = supplierResponseRepository.getSupplierResponseByNameAndBidRequestItemId(supplierResponse.getName(), supplierResponse.getBidItemId());
        BidRequestItem bidRequestItem = bidRequestItemService.getBidItemRequestByBidRequestItemId(supplierResponse.getBidItemId());
        if (bidRequestItem != null) {
            existingSupplierResponse.ifPresent(supplierResponse1 -> supplierResponse.setId(supplierResponse1.getId()));
            supplierResponse.setBidRequestItem(bidRequestItem);
            if(supplierResponse.getIsAccepted()) {
                supplierResponse.setPerUnitQuantity(supplierResponse.getAmount() / supplierResponse.getQuantity());
            }
            supplierResponseRepository.save(supplierResponse);
        }
    }

    public List<SupplierResponse> getResponseByBidItemId(Long bidItemId) {
        List<SupplierResponse> supplierResponses = supplierResponseRepository.getSupplierResponseByBidRequestItemIdOrderByPerUnitQuantityDesc(bidItemId);
        int i = 1;
        for (SupplierResponse supplierResponse : supplierResponses) {
            supplierResponse.setRank(i++);
        }
        return supplierResponses;
    }

    public List<SupplierResponse> getSupplierResponseByBidRequestItem(List<Long> bidRequestItems){
        return supplierResponseRepository.getSupplierResponseByBidRequestItemIdIn(bidRequestItems);

    }
}
