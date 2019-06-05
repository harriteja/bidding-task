package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bid_request")
public class BidRequest {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Transient
    private boolean isExpired = false;

    @Transient
    private List<BidRequestItem> bidRequestItemList = new ArrayList<>();

}
