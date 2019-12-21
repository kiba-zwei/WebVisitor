package com.xlab;

import java.util.ArrayList;
import java.util.List;

public class VisitorConfig {
    private static final int MIN_SIZE_OF_CONFIG = 2;
    int num;
    String url;

    public VisitorConfig(int num, String url) {
        this.num = num;
        this.url = url;
    }

    public static List<VisitorConfig> createConfiges(List<String> config) throws Exception {
        if (config == null || config.size() % MIN_SIZE_OF_CONFIG != 0) {
            throwException("Invaild config");
        }
        List<VisitorConfig> configs = new ArrayList<>();
        int num = 0;
        String url = "";
        for (int i = 0; i < config.size(); i++) {
            int type = i % MIN_SIZE_OF_CONFIG;
            switch (type) {
                case 0:
                    num = Integer.valueOf(config.get(i));
                    break;
                case 1:
                    url = config.get(i);
                    configs.add(new VisitorConfig(num, url));
                    break;
                default:
                    throwException("wrong type of config");
            }
        }
        return configs;
    }

    private static final void throwException(String msg) throws Exception {
        throw new Exception(msg);
    }
}
