package edu.uci.ics.crawler4j.storage;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ElasticsearchStorageService implements StorageService{
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchStorageService.class);

    private TransportClient client;

    public ElasticsearchStorageService() {
        init();
    }

    private void init() {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9301));
        } catch (UnknownHostException ignore) { }

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void store(Page page) {
        XContentBuilder builder = content(page);

        IndexResponse indexResponse = client.prepareIndex("crawl_storage", "page").setSource(builder).get();

        logger.debug(indexResponse.status().name());

    }

    @Override
    public void bulk(List<Page> pages) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        pages.forEach(page -> {
            XContentBuilder builder = content(page);
            bulkRequest.add(client.prepareIndex("crawl_storage", "page").setSource(builder));

        });
        BulkResponse responses = bulkRequest.get();
        if (responses.hasFailures()) {
            Arrays.stream(responses.getItems()).filter(BulkItemResponse::isFailed).forEach(bulkItemResponse -> logger.error(bulkItemResponse.getFailureMessage()));
        }

    }

    private XContentBuilder content(Page page) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                    .field("docId", page.getWebURL().getDocid())
                    .field("url", page.getWebURL().getURL())
                    .field("domain", page.getWebURL().getDomain())
                    .field("subDomain", page.getWebURL().getSubDomain())
                    .field("path", page.getWebURL().getPath())
                    .field("parentUrl", page.getWebURL().getParentUrl())
                    .field("anchor", page.getWebURL().getAnchor())
                    .field("timestamp", LocalDateTime.now(ZoneId.of("Asia/Shanghai")).atOffset(ZoneOffset.ofHours(8)).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

            if (page.getParseData() instanceof HtmlParseData) {
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                builder.field("text", htmlParseData.getText())
                        .field("html", htmlParseData.getHtml())
                        .field("links", htmlParseData.getOutgoingUrls().stream().map(WebURL::getURL).collect(Collectors.toList()));
            }

            return builder.endObject();
        } catch (IOException e) {
            logger.error("content error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        if (null != client) {
            client.close();
        }
    }

}
