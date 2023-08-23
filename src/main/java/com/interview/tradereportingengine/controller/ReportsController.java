package com.interview.tradereportingengine.controller;


import com.interview.tradereportingengine.Service.TradeInformationService;
import com.interview.tradereportingengine.model.TradeInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportsController {

    private final TradeInformationService tradeInformationService;

    @GetMapping("/v1/reports")
    public ResponseEntity<List<TradeInformation>> getReport() {

        return ResponseEntity.ok(tradeInformationService.getEligibleTrades());

    }

}
