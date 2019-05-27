package com.example.demo.repository;

import com.example.demo.model.BidRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRequestItemRepository extends JpaRepository<BidRequestItem, Long> {

    List<BidRequestItem> getBidRequestItemByBidRequestId(Long bidRequestId);

    List<BidRequestItem> getBidRequestItemByBidRequestIdIn(List<Long> bidRequestIds);

    BidRequestItem getBidRequestItemById(Long bidRequestItemId);

}
