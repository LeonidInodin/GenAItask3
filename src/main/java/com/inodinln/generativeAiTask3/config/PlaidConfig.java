package com.inodinln.generativeAiTask3.config;

import com.plaid.client.PlaidClient;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Call;

@Configuration
public class PlaidConfig {

    @Value("${PLAID_CLIENT_ID}")
    private String plaidClientId;

    @Value("${PLAID_SECRET}")
    private String plaidSecret;

    @Value("${PLAID_PUBLIC_KEY}")
    private String plaidPublicKey;

    @Value("#{systemProperties['PLAID_ENV'] ?: 'sandbox'}")
    private String plaidEnv;


    @Bean
    public PlaidClient plaidClient() {

        PlaidClient.Builder clientBuilder = PlaidClient.newBuilder()
                .clientIdAndSecret(plaidClientId, plaidSecret)
                .publicKey(plaidPublicKey);


        switch (plaidEnv.toLowerCase()) {
            case "sandbox":
                clientBuilder.sandboxBaseUrl();
                break;
            case "development":
                clientBuilder.developmentBaseUrl();
                break;
            case "production":
                clientBuilder.productionBaseUrl();
                break;
            default:
                clientBuilder.sandboxBaseUrl();
        }

        return clientBuilder.build();
    }
}
