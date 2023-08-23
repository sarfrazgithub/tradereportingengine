package com.interview.tradereportingengine.repository;

import com.interview.tradereportingengine.model.TradeInformation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TradeInformationSpecification {


    public static Specification<TradeInformation> criteriaOne() {
        Specification<TradeInformation> bank = (trade, cq, cb) -> cb.equal(trade.get("sellerReference"), "LMU_BANK");
        Specification<TradeInformation> currency = (trade, cq, cb) -> cb.equal(trade.get("currency"), "AUD");
        return Specification.where(bank.and(currency));
    }

    public static Specification<TradeInformation> criteriaTwo() {
        Specification<TradeInformation> bank = (trade, cq, cb) -> cb.equal(trade.get("sellerReference"), "BISON_BANK");
        Specification<TradeInformation> currency = (trade, cq, cb) -> cb.equal(trade.get("currency"), "USD");
        return Specification.where(bank.and(currency));
    }
}
