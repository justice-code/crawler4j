package edu.uci.ics.crawler4j.fetcher;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;

import java.io.File;
import java.io.IOException;

public class FilePageFetcher extends PageFetcher{

    public FilePageFetcher() {
    }

    @Override
    public PageFetchResult fetchPage(WebURL webUrl) throws InterruptedException, IOException, PageBiggerThanMaxSizeException {
        PageFetchResult result = new PageFetchResult();
        result.setEntity(new FileEntity(new File(webUrl.getURL()), ContentType.TEXT_HTML));
        result.setFetchedUrl(webUrl.getURL());
        result.setResponseHeaders(new Header[0]);
        return result;
    }

    @Override
    public synchronized void shutDown() {
        super.shutDown();
    }

    /**
     * Creates a new HttpUriRequest for the given url. The default is to create a HttpGet without
     * any further configuration. Subclasses may override this method and provide their own logic.
     *
     * @param url the url to be fetched
     * @return the HttpUriRequest for the given url
     */
    @Override
    protected HttpUriRequest newHttpUriRequest(String url) {
        return super.newHttpUriRequest(url);
    }

    @Override
    protected CrawlConfig getConfig() {
        return super.getConfig();
    }
}
