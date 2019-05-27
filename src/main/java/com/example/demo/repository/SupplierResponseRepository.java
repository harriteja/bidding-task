package com.example.demo.repository;

import com.example.demo.model.SupplierResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierResponseRepository extends JpaRepository<SupplierResponse, Long> {
    List<SupplierResponse> getSupplierResponseByBidRequestItemIdOrderByPerUnitQuantityDesc(Long bidItemId);

    Optional<SupplierResponse> getSupplierResponseByNameAndBidRequestItemId(String name, Long bidRequestItemId);

    List<SupplierResponse> getSupplierResponseByBidRequestItemIdIn(List<Long> bidRequestItems);
}
