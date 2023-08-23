package com.interview.tradereportingengine.Service;


import com.interview.tradereportingengine.model.TradeInformation;
import com.interview.tradereportingengine.repository.TradeInformationRepository;
import com.interview.tradereportingengine.repository.TradeInformationSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeInformationService {

    private final TradeInformationRepository tradeInformationRepository;

    public List<TradeInformation> getEligibleTrades() {

        var result = tradeInformationRepository.findAll(TradeInformationSpecification.criteriaOne().or(TradeInformationSpecification.criteriaTwo()))
                .stream().filter(this::notAnagram).collect(Collectors.toList());

        log.info("Number of Eligbile Trades: " + result.size());

        return result;
    }

    private Boolean notAnagram(TradeInformation tradeInformation) {

        if (tradeInformation.getBuyerReference().length() != tradeInformation.getSellerReference().length()) {
            return true;
        }
        char[] buyer = tradeInformation.getBuyerReference().toCharArray();
        char[] seller = tradeInformation.getSellerReference().toCharArray();
        Arrays.sort(buyer);
        Arrays.sort(seller);
        return !Arrays.equals(buyer, seller);

    }

}

