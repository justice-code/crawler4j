package edu.uci.ics.crawler4j.examples.blog.mine;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.storage.StorageServiceFactory;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;

import java.util.Set;
import java.util.regex.Pattern;

public class BlogCrawler extends WebCrawler {

    private static final Pattern STATIC_EXTENSIONS = Pattern.compile(".*\\.(css|js|bmp|gif|jpg|png)$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (STATIC_EXTENSIONS.matcher(href).matches()) {
            return false;
        }

        return href.startsWith("https://justice-love.com/");
    }

    @Override
    public void visit(Page page) {
        StorageServiceFactory.elasticInstance().store(page);
        logger.debug("=============");
    }
}
