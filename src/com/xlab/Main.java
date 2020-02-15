package com.xlab;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final String TIP_PATH = "README.txt";

    public static void main(String[] args) {
        tips();
        try {
            List<VisitorConfig> configs = VisitorConfig.createConfiges(FileParser.parseConfig());
            for (VisitorConfig vc : configs) {
                visit(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tips() {
        for (String str : FileParser.read(TIP_PATH)) {
            System.out.println(str);
        }
    }

    private static void visit(VisitorConfig vc) throws IOException, InterruptedException {
        for (int i = 1; i <= vc.getNum(); i++) {
            CmdProxy.getInstance().openWeb(vc.getUrl());
            Thread.sleep(vc.getInterval());
            if (i % 10 == 0) {
                CmdProxy.getInstance().closeWeb();
            }
        }

    }
}