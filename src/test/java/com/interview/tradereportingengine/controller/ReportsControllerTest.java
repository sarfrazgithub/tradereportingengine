package com.interview.tradereportingengine.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.interview.tradereportingengine.Service.TradeInformationService;
import com.interview.tradereportingengine.model.TradeInformation;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ReportsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeInformationService tradeInformationService;


    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        TradeInformation tradeInformation1 = TradeInformation.builder().buyerReference("BUYER-ONE").sellerReference("LMU_BANK").amount(new BigDecimal(100)).currency("AUD").build();
        List<TradeInformation> result = new ArrayList<>();
        result.add(tradeInformation1);
        when(tradeInformationService.getEligibleTrades()).thenReturn(result);
        this.mockMvc.perform(get("/api/v1/reports")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"buyerReference\":\"BUYER-ONE\",\"sellerReference\":\"LMU_BANK\",\"amount\":100,\"currency\":\"AUD\"}]")));
    }

}
