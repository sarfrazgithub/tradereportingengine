package com.interview.tradereportingengine.repository;

import com.interview.tradereportingengine.model.TradeInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TradeInformationRepository extends JpaRepository<TradeInformation, Long> , JpaSpecificationExecutor<TradeInformation> {

}
