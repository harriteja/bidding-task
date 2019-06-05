package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bid_request_item")
public class BidRequestItem {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;

    @Column(name = "item")
    private String item;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_request_id", referencedColumnName = "id", updatable = false)
    private BidRequest bidRequest;

    @Transient
    private List<SupplierResponse> supplierResponseList = new ArrayList<>();

}
