package com.example.demo.repository;

import com.example.demo.model.BidRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BidRequestRepository extends JpaRepository<BidRequest, Long> {

    BidRequest getBidRequestById(Long id);

    List<BidRequest> getBidRequestByStartDateAfterAndEndDateBefore(Date startDateAfter, Date endDateBeforedate);

    List<BidRequest> getBidRequestByEndDateAfter(Date date);

}
