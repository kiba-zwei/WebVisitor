package com.xlab;

import java.io.IOException;

public class CmdProxy {
    /**
     * 使用IE浏览器打开url
     *
     * @param url 需要打开的网页
     * @throws IOException
     */
    public void openWeb(String url) throws IOException {
        Runtime.getRuntime().exec("cmd /c start iexplore " + url);
    }

    /**
     * 关闭IE浏览器
     *
     * @throws IOException
     */
    public void closeWeb() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
    }

    public static CmdProxy getInstance() {
        return SingleInstance.INSTANCE;
    }

    private CmdProxy() {
    }

    private static final class SingleInstance {
        private static final CmdProxy INSTANCE = new CmdProxy();
    }
}
