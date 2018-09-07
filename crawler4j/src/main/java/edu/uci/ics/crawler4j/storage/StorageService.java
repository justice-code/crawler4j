package edu.uci.ics.crawler4j.storage;

import edu.uci.ics.crawler4j.crawler.Page;

import java.util.List;

public interface StorageService {

     void store(Page page);

     void bulk(List<Page> pages);

     void shutdown();
}
