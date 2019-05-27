package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "supplier")
@Setter
@Getter
public class SupplierResponse {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "per_unit_quantity")
    private Double perUnitQuantity;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Transient
    private Long bidItemId;

    @Transient
    private Integer rank;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_request_item_id", referencedColumnName = "id", updatable = false)
    private BidRequestItem bidRequestItem;

}
