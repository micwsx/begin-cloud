package com.micwsx.cloud;

import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.IClient;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Michael
 * @create 8/17/2020 4:11 PM
 * Ribbon可以单独使用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MicroProductApplication.class)
@WebAppConfiguration
public class RibbonTest {

    @Test
    public void test1(){

        ConfigurationManager.getConfigInstance().setProperty("micro-order.ribbon.listOfServers","localhost:8084");
        try {
            HttpRequest request= HttpRequest.newBuilder().uri(new URI("/queryUser")).build();
            RestClient client = (RestClient)ClientFactory.getNamedClient("micro-order");
            for (int i = 0; i < 10; i++) {
                HttpResponse httpResponse = client.executeWithLoadBalancer(request);
                String entity = httpResponse.getEntity(String.class);
                System.out.println(entity);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
