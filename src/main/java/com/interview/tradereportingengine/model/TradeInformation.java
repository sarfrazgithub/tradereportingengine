package com.interview.tradereportingengine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Entity
@Table(name = "trade_information")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TradeInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @JsonIgnore
    private long id;

    @Column(name = "buyer_reference")
    private String buyerReference;

    @Column(name = "seller_reference")
    private String sellerReference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency ")
    private String currency;
}
