package com.example.demo.service;

import com.example.demo.model.BidRequest;
import com.example.demo.model.BidRequestItem;
import com.example.demo.repository.BidRequestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidRequestItemService {

    @Autowired
    private BidRequestItemRepository bidRequestItemRepository;

    public List<BidRequestItem> saveBidRequestItem(List<BidRequestItem> bidRequestItems, BidRequest bidRequest){
        bidRequestItems.forEach(bidRequestItem -> bidRequestItem.setBidRequest(bidRequest));
        return bidRequestItemRepository.saveAll(bidRequestItems);
    }

    public List<BidRequestItem> getByBidRequestId(Long bidRequestId){
        return bidRequestItemRepository.getBidRequestItemByBidRequestId(bidRequestId);
    }

    public List<BidRequestItem> getBidRequestItemByBidRequestIds(List<Long> bidRequestIds){
        return bidRequestItemRepository.getBidRequestItemByBidRequestIdIn(bidRequestIds);
    }

    public BidRequestItem getBidItemRequestByBidRequestItemId(Long bidRequestItemId){
        return bidRequestItemRepository.getBidRequestItemById(bidRequestItemId);
    }




}
