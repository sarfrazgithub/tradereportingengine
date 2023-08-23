package com.interview.tradereportingengine.parser;

import com.interview.tradereportingengine.model.TradeInformation;
import com.interview.tradereportingengine.repository.TradeInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class XMLParser implements CommandLineRunner {

    private final TradeInformationRepository tradeInformationRepository;

    private static final String buyerPartyRef = "//buyerPartyReference/@href";
    private static final String sellerPartyRef = "//sellerPartyReference/@href";
    private static final String amount = "//paymentAmount/amount";
    private static final String currency = "//paymentAmount/currency";

     void parse() {
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            File fileResource = ResourceUtils.getFile("./src/main/resources/events");
            for (File file : Objects.requireNonNull(fileResource.listFiles())) {

                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                XPath xpath = XPathFactory.newInstance().newXPath();

                var buyerNode = (NodeList) xpath.compile(buyerPartyRef).evaluate(doc, XPathConstants.NODESET);
                var sellerNode = (NodeList) xpath.compile(sellerPartyRef).evaluate(doc, XPathConstants.NODESET);
                var amountNode = (NodeList) xpath.compile(amount).evaluate(doc, XPathConstants.NODESET);
                var currencyNode = (NodeList) xpath.compile(currency).evaluate(doc, XPathConstants.NODESET);

                TradeInformation tradeInformation = TradeInformation.builder()
                        .buyerReference(extractContent(buyerNode))
                        .sellerReference(extractContent(sellerNode))
                        .amount(new BigDecimal(Objects.requireNonNull(extractContent(amountNode))))
                        .currency(extractContent(currencyNode))
                        .build();

                tradeInformationRepository.save(tradeInformation);

               // log.info("Trade Information: {}", tradeInformation);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractContent(NodeList node) {
        return node.item(0) != null ? node.item(0).getTextContent() : null;
    }

    @Override
    public void run(String... args) throws Exception {
        parse();
    }
}
