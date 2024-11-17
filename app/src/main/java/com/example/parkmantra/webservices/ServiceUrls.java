package com.example.parkmantra.webservices;

public class ServiceUrls {
    private static ServiceUrls INSTANCE;

    public static ServiceUrls getInstace() {
        if (INSTANCE == null)
            init();
        return INSTANCE;
    }

    private String HOSTURL = "https://9002-idx-parkmantra-1730691768146.cluster-bec2e4635ng44w7ed22sa22hes.cloudworkstations.dev/app/";

    public String getMETHODNAME(ServiceMethods serviceMethods) {
        switch (serviceMethods) {
            case WS_LOGINWITHMOBILEPASS:
                return HOSTURL + NKeys.LOGIN;
            default:
                return HOSTURL;
        }
    }

    public static void init() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceUrls();
        }
    }
}
