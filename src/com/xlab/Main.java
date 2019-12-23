package com.xlab;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final int TIME_INTERVAL = 1 * 1000;

    public static void main(String[] args) {
        tips();
        try {
            List<VisitorConfig> configs = VisitorConfig.createConfiges(FileParser.getInstance().read());
            for (VisitorConfig vc : configs) {
                visit(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tips() {
        System.out.println("*******************************************************************************************************************");
        System.out.println("受某同学之托，搞一个刷文章阅读量的工具，具体使用方法如下：\n" +
                "1、下载Java JDK 11 并配置环境变量，见： https://blog.csdn.net/kiba_zwei/article/details/103645213；\n" +
                "2、在config.txt中填入刷新次数与目标网页地址，第一行为次数，第二行为地址，第三行为次数，第四行为地址，以此类推；\n" +
                "3、点击start.bat开始刷流量；\n" +
                "4、本工具实现方式为模拟点击网页的方式进行流量刷新，使用过程中会反复弹出网页，影响电脑使用，请这段时间暂离电脑；\n" +
                "5、刷新时间为：10*N s, N为刷新次数；\n" +
                "6、有使用问题欢迎联系：xingbinkiba@163.com");
        System.out.println("示例：");
        System.out.println("10");
        System.out.println("www.baidu.com");
        System.out.println("*******************************************************************************************************************");
    }

    private static void visit(VisitorConfig vc) throws IOException, InterruptedException {
        for (int i = 1; i <= vc.num; i++) {
            CmdProxy.getInstance().openWeb(vc.url);
            Thread.sleep(TIME_INTERVAL);
            if (i % 10 == 0) {
                CmdProxy.getInstance().closeWeb();
            }
        }

    }
}