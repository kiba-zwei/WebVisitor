package com.xlab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VisitorConfig {
    private static final int MIN_SIZE_OF_CONFIG = 2;
    private static final int ONE_SECOND = 1000;
    private static final int DEFAULT_NUM = 10;
    private static final int DEFAULT_INTERVAL = 10;
    private int num;
    private String url;
    private int interval = 10;

    public VisitorConfig(int num, String url) {
        this.num = num;
        this.url = url;
    }

    public VisitorConfig(int num, String url, int interval) {
        this.num = num;
        this.url = url;
        this.interval = interval;
    }

    public static List<VisitorConfig> createConfiges(List<String> config) throws Exception {
        if (config == null || config.size() % MIN_SIZE_OF_CONFIG != 0) {
            throwException("Invaild config.txt");
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
                    throwException("wrong type of config.txt");
            }
        }
        return configs;
    }

    public static List<VisitorConfig> createConfiges(String configs) throws Exception {
        if (configs == null || configs.length() == 0) {
            return null;
        }
        List<VisitorConfig> visitorConfigs = new ArrayList<>();
        JSONObject jobj = JSON.parseObject(configs);
        JSONArray configList = jobj.getJSONArray("config");

        for (int i = 0; i < configList.size(); i++) {
            JSONObject jsonObject = configList.getJSONObject(i);
            try {
                int count = jsonObject.getInteger("count");
                int interval = jsonObject.getInteger("time_interval");
                String url = jsonObject.getString("url");
                VisitorConfig vc = new VisitorConfig(count, url, interval);
                System.out.println(vc);
                visitorConfigs.add(vc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return visitorConfigs;
    }

    public int getNum() {
        if (num < 0) {
            return DEFAULT_NUM;
        }
        return num;
    }

    public String getUrl() {
        return url;
    }

    public int getInterval() {
        if (interval < 0) {
            return DEFAULT_INTERVAL * ONE_SECOND;
        }
        return interval * ONE_SECOND;
    }

    @Override
    public String toString() {
        return "VisitorConfig{" +
                "num=" + num +
                ", url='" + url + '\'' +
                ", interval=" + interval +
                '}';
    }

    private static final void throwException(String msg) throws Exception {
        throw new Exception(msg);
    }
}
