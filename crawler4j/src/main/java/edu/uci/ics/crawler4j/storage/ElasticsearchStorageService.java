package edu.uci.ics.crawler4j.storage;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticsearchStorageService implements StorageService{
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchStorageService.class);

    private TransportClient client;

    private void init() {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9301));
        } catch (UnknownHostException ignore) { }

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void store(Page page) {
        XContentBuilder builder = null;
        try {
            builder = content(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IndexResponse indexResponse = client.prepareIndex().setSource(builder).get();


    }

    private XContentBuilder content(Page page) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("docId", page.getWebURL().getDocid())
                .field("url", page.getWebURL().getURL())
                .field("domain", page.getWebURL().getDomain())
                .field("subDomain", page.getWebURL().getSubDomain())
                .field("path", page.getWebURL().getPath())
                .field("parentUrl", page.getWebURL().getParentUrl())
                .field("anchor", page.getWebURL().getAnchor());

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            builder.field("text", htmlParseData.getText())
                    .field("html", htmlParseData.getHtml())
                    .field("links", htmlParseData.getOutgoingUrls());
        }

        return builder.endObject();
    }

    @Override
    public void shutdown() {
        if (null != client) {
            client.close();
        }
    }
}
