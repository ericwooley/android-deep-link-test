package com.example.eriwoole.deep_link_test;

import android.net.Uri;

/**
 * Created by eriwoole on 10/20/15.
 */
public class WebPage {
    private Uri pageURI;
    private String title;
    public void WebPage(String uri, String title) {
        this.pageURI = Uri.parse(uri);
        this.title = title;
    }
}
