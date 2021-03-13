package com.xlab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VisitorConfig {
    private static final int MIN_SIZE_OF_CONFIG = 2;
    private static final int ONE_SECOND = 1000;
    private static final int DEFAULT_NUM = 10;
    private static final int DEFAULT_INTERVAL = 10;
    private static final int WEEKEND_MULTIPLIER = 10;
    private int num;
    private String url;
    private int interval = 10;
    // 周末期间PV数据较低，只进行10%速率进行刷新
    private boolean weekendRest = false;
    // 关闭所有网页时间间隔，即每隔1分钟关闭一次所有网页
    private int closeWebInterval = 1 * 60 * 1000;

    public VisitorConfig(int num, String url) {
        this.num = num;
        this.url = url;
    }

    public VisitorConfig(int num, String url, int interval, boolean weekendRest) {
        this.num = num;
        this.url = url;
        this.interval = interval;
        this.weekendRest = weekendRest;
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
        boolean weekendRest = jobj.getBoolean("weekend_rest");
        for (int i = 0; i < configList.size(); i++) {
            JSONObject jsonObject = configList.getJSONObject(i);
            try {
                int count = jsonObject.getInteger("count");
                int interval = jsonObject.getInteger("time_interval");
                String url = jsonObject.getString("url");
                VisitorConfig vc = new VisitorConfig(count, url, interval, weekendRest);
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

    /***
     * @Description 获取每次刷新时间间隔，如果当日为周末，则刷新时间返回10倍

     * @return int 每次刷新时间间隔，如果当日为周末，则刷新时间返回10倍
     * @author kiba
     * @Datetime 2021/3/13
     */
    public int getInterval() {
        if (interval < 0) {
            return DEFAULT_INTERVAL * ONE_SECOND;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_WEEK);
        int multiplier = multiplierTakeEffect() ? WEEKEND_MULTIPLIER : 1;
        return interval * ONE_SECOND * multiplier;
    }

    private boolean multiplierTakeEffect() {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        boolean isWeekend = dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
        return weekendRest && isWeekend;
    }

    public int getCloseWebInterval() {
        return closeWebInterval;
    }

    @Override
    public String toString() {
        return "VisitorConfig{" +
                "num=" + num +
                ", url='" + url + '\'' +
                ", interval=" + interval +
                ", weekendRest=" + weekendRest +
                '}';
    }

    private static final void throwException(String msg) throws Exception {
        throw new Exception(msg);
    }
}
