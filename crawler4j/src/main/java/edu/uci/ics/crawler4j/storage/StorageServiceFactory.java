package edu.uci.ics.crawler4j.storage;

public class StorageServiceFactory {
    private static StorageService elastic = new ElasticsearchStorageService();

    public static StorageService elasticInstance() {
        return elastic;
    }
}
