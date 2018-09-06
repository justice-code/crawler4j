package edu.uci.ics.crawler4j.storage;

import edu.uci.ics.crawler4j.crawler.Page;

public interface StorageService {

     void store(Page page);

     void shutdown();
}
