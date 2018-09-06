package edu.uci.ics.crawler4j.tests.elastic;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class clientTest {

    @Test
    public void test() throws IOException {

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9301));
        client.prepareIndex("user_test", "user").setSource(jsonBuilder()
                .startObject()
                .field("name", "xuyi")
                .field("user_name", "eddy")
                .field("email", "123@126.com")
                .endObject())
                .get();
    }
}
