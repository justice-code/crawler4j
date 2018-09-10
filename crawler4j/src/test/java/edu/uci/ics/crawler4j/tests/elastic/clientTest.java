package edu.uci.ics.crawler4j.tests.elastic;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class clientTest {

    @Test
    public void test() throws IOException {

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9301));
        client.prepareIndex("user_test", "user").setSource(jsonBuilder()
                .startObject()
                .field("name", "xuyi")
                .field("user_name", "eddy")
                .field("email", "123@126.com")
                .endObject())
                .get();
    }

    @Test
    public void test2() {
        System.out.println(LocalDateTime.now(ZoneId.of("Asia/Shanghai")).atOffset(ZoneOffset.ofHours(8)).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
