package com.gabriel.microservices.order.stubs;

import com.github.tomakehurst.wiremock.client.WireMock;

public class InventoryClientStub {
    public static void stubInventoryCall(String skuCode, Integer quantity) {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/inventory?skuCode=" + skuCode+"&quantity=" + quantity))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("true")));
    }
}
