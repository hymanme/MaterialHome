package com.hymane.materialhome.utils;

import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 */

public class BookChapterFactory {
    private static LRUMap<String, ArrayList<String>> chapters = new LRUMap<>(10);

    public BookChapterFactory(String bookId, int lineHeight) {
    }
}
