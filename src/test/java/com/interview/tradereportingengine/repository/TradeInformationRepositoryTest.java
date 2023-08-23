package com.interview.tradereportingengine.repository;

import com.interview.tradereportingengine.model.TradeInformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TradeInformationRepositoryTest {


    @Autowired
    private TradeInformationRepository tradeInformationRepository;

    @BeforeEach
    void setUp() {
        TradeInformation tradeInformation1 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("LMU_BANK").amount(new BigDecimal(100)).currency("AUD").build();
        TradeInformation tradeInformation2 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("BISON_BANK").amount(new BigDecimal(100)).currency("USD").build();
        TradeInformation tradeInformation3 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("NAB_BANK").amount(new BigDecimal(100)).currency("USD").build();
        TradeInformation tradeInformation4 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("ANZ_BANK").amount(new BigDecimal(100)).currency("USD").build();
        TradeInformation tradeInformation5 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("NAB_BANK").amount(new BigDecimal(100)).currency("USD").build();

        tradeInformationRepository.save(tradeInformation1);
        tradeInformationRepository.save(tradeInformation2);
        tradeInformationRepository.save(tradeInformation3);
        tradeInformationRepository.save(tradeInformation4);
        tradeInformationRepository.save(tradeInformation5);

    }

    @Test
    void validateEligibleTrade() {
        var result = tradeInformationRepository.findAll(Specification.where(TradeInformationSpecification.criteriaOne()).or(TradeInformationSpecification.criteriaTwo()));
        assertThat(result.size() == 2).isTrue();
        validateTrade(result);
    }

    void validateTrade(List<TradeInformation> result) {

        for (TradeInformation tradeInfo : result) {


            assertThat((tradeInfo.getSellerReference().equals("LMU_BANK") && tradeInfo.getCurrency().equals("AUD")) ||
                    (tradeInfo.getSellerReference().equals("BISON_BANK") && tradeInfo.getCurrency().equals("USD"))).isTrue();
        }

    }

    @AfterEach
    void tearDown() {
        tradeInformationRepository.deleteAll();
    }
}
