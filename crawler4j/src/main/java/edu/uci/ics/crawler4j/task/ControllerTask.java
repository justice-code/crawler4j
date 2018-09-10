package edu.uci.ics.crawler4j.task;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ControllerTask {

    private CrawlController controller;
    private Class clazz;
    private int numberOfCrawlers;

}
