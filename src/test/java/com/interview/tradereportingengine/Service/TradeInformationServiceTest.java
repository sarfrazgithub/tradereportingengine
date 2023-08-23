package com.interview.tradereportingengine.Service;

import com.interview.tradereportingengine.model.TradeInformation;
import com.interview.tradereportingengine.repository.TradeInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeInformationServiceTest {

    @Mock
    private TradeInformationRepository tradeInformationRepository;

    @InjectMocks
    private TradeInformationService tradeInformationService;

    @BeforeEach
    void setUp() {

        tradeInformationService = new TradeInformationService(tradeInformationRepository);
    }

    @Test
    void getEligibleTradeWithoutAnagram() {
        TradeInformation tradeInformation1 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("LMU_BANK").amount(new BigDecimal(100)).currency("AUD").build();
        TradeInformation tradeInformation2 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("BISON_BANK").amount(new BigDecimal(100)).currency("USD").build();

        List<TradeInformation> result = new ArrayList<>();
        result.add(tradeInformation1);
        result.add(tradeInformation2);
        when(tradeInformationRepository.findAll(any(Specification.class))).thenReturn(result);
        assertThat(tradeInformationService.getEligibleTrades().size() == 2).isTrue();
    }

    @Test
    void getEligibleTradeWithAnagram() {
        TradeInformation tradeInformation1 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("LMU_BANK").amount(new BigDecimal(100)).currency("AUD").build();
        TradeInformation tradeInformation2 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("BISON_BANK").amount(new BigDecimal(100)).currency("USD").build();
        TradeInformation tradeInformation3 = TradeInformation.builder().buyerReference("SONBI_BANK").sellerReference("BISON_BANK").amount(new BigDecimal(100)).currency("USD").build();
        TradeInformation tradeInformation4 = TradeInformation.builder().buyerReference("MUL_BANK").sellerReference("LMU_BANK").amount(new BigDecimal(100)).currency("AUD").build();
        List<TradeInformation> result = new ArrayList<>();
        result.add(tradeInformation1);
        result.add(tradeInformation2);
        result.add(tradeInformation3);
        result.add(tradeInformation4);
        when(tradeInformationRepository.findAll(any(Specification.class))).thenReturn(result);
        assertThat(tradeInformationService.getEligibleTrades().size() == 2).isTrue();
    }
}