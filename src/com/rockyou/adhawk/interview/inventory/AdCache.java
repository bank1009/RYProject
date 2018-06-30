package com.rockyou.adhawk.interview.inventory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class AdCache {

    public Collection<AdUnit> ads;

    public void initialize() {
        ads = Arrays.asList(new AdUnit(1, new BigDecimal("4.00"), "http://click.url/onclick?unit=1", "http://creative.url/image.png", Collections.singletonList("pl11")),
                            new AdUnit(2, new BigDecimal("3.00"), "http://click.url/onclick?unit=2", "http://creative.url/image2.png", Collections.singletonList("pl11")),
                            new AdUnit(3, new BigDecimal("2.00"), "http://click.url/onclick?unit=3", "http://creative.url/image3.png", Collections.singletonList("pl11")),
                            // new AdUnit(66, new BigDecimal("5.00"), "http://click.url/onclick?unit=3", "http://creative.url/image3.png", Collections.singletonList("pl11")), // TODO: mock for test

                            new AdUnit(4, new BigDecimal("5.00"), "http://click.url/onclick?unit=4&bid=${AUCTION_PRICE}", "http://creative.url/image4.png", Collections.singletonList("pl12")),

                            new AdUnit(5, new BigDecimal("4.00"), "http://click.url/onclick?unit=5&bid=${AUCTION_PRICE}", "http://creative.url/image5.png", Collections.singletonList("pl13")),
                            new AdUnit(6, new BigDecimal("3.00"), "http://click.url/onclick?unit=6&bid=${AUCTION_PRICE}", "http://creative.url/image6.png", Collections.singletonList("pl13")),
                            new AdUnit(7, new BigDecimal("3.00"), "http://click.url/onclick?unit=7&bid=${AUCTION_PRICE}", "http://creative.url/image7.png", Collections.singletonList("pl13")),

                            new AdUnit(8, new BigDecimal("4.00"), "http://click.url/onclick?unit=8&bid=${AUCTION_PRICE}", "http://creative.url/image8.png", Collections.singletonList("pl99")),
                            new AdUnit(9, new BigDecimal("4.00"), "http://click.url/onclick?unit=9&bid=${AUCTION_PRICE}", "http://creative.url/image9.png", Collections.singletonList("pl99")),
                            new AdUnit(10, new BigDecimal("4.00"), "http://click.url/onclick?unit=10&bid=${AUCTION_PRICE}", "http://creative.url/image10.png", Collections.singletonList("pl99")));
    }
}
