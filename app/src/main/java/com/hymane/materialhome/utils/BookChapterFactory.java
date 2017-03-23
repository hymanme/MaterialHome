package com.hymane.materialhome.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;

import com.hymane.materialhome.bean.http.ebook.ChapterPage;
import com.hymane.materialhome.bean.http.ebook.ChapterRead;
import com.hymane.materialhome.utils.common.DensityUtils;
import com.hymane.materialhome.utils.common.FileUtils;
import com.hymane.materialhome.utils.common.ScreenUtils;
import com.hymane.materialhome.utils.common.UIUtils;

import org.apache.commons.collections4.map.LRUMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 * 1：加载网络章节信息，并保存到本地文件，可能加载多个章节，即预加载{@link BookChapterFactory#cacheChapter(ChapterRead.Chapter, String, int)}
 * 2：读取分页后的章节文本{@link BookChapterFactory#getChapterContent(int)}
 * 3：LRU 中如果有缓存该章节内容，直接返回内容给 2，如果 LRU 中没有缓存，那么读取缓存文件{@link BookChapterFactory#readChapterFile(int)}
 * 4：读取到文件内容之后对其进行分页{@link BookChapterFactory#split(int, String, int, String)},分页完之后添加到 LRU 缓存中，最后直接返回给 2。
 */

public class BookChapterFactory {
    private int mScreenWidth;   //屏幕宽度
    private int mScreenHeight;  //屏幕高度
    private int mMarginWidth = 12; // 左右与边缘的距离
    private int mMarginHeight = 16; // 上下与边缘的距离
    private float mVisibleHeight; // 绘制内容的宽
    private float mVisibleWidth; // 绘制内容的宽

    private Paint mPaint;
    private int mFontSize = 16; //sp
    private float mFontSizePx;
    private int mTextColor = Color.LTGRAY;

    private int mLineCount = 0; // 每页可以显示的行数
    private int mLineWordCount = 0; // 每行可以显示的字数

    private static LRUMap<String, ArrayList<ChapterPage>> chapters = new LRUMap<>(10);
    private static final String basePath = FileUtils.createRootPath(UIUtils.getContext()) + "/book/";
    private static final String CHAPTER_FILE_EXTENSION = ".txt";
    private String bookId;      //图书id

    public BookChapterFactory(String bookId, int lineHeight) {
        this.bookId = bookId;
        mScreenWidth = ScreenUtils.getScreenWidth(UIUtils.getContext());
        mScreenHeight = ScreenUtils.getScreenHeight(UIUtils.getContext());

        mVisibleWidth = mScreenWidth - DensityUtils.dp2px(UIUtils.getContext(), mMarginWidth) * 2;
        mVisibleHeight = mScreenHeight - DensityUtils.dp2px(UIUtils.getContext(), mMarginHeight) * 2
                - ScreenUtils.getStatusHeight(UIUtils.getContext())
                - ScreenUtils.getVirtualBarHeight(UIUtils.getContext())
                - lineHeight * 2;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(mTextColor);

        mFontSizePx = DensityUtils.sp2px(UIUtils.getContext(), mFontSize);
        mLineWordCount = (int) (mVisibleWidth / mFontSizePx);
        mLineCount = (int) (mVisibleHeight / lineHeight); // 可显示的行数

        Log.i("READER=", "mLineCount = " + mLineCount);
        Log.i("READER=", "mFontSizePx = " + mFontSizePx);
        Log.i("READER=", "mLineWordCount = " + mLineWordCount);
        Log.i("READER=", "mVisibleWidth = " + mVisibleWidth);
        Log.i("READER=", "mVisibleHeight = " + mVisibleHeight);
    }

    /**
     * 保存图书章节数据到文件
     *
     * @param chapter
     * @param chapterNo
     */
    public static void cacheChapter(final ChapterRead.Chapter chapter, String bookId, int chapterNo) {
        File file = getBookChapterFile(bookId, chapterNo);
        FileUtils.writeFile(file.getAbsolutePath(), chapter.getBody(), false);
        Log.i("cacheChapter", "cacheChapter: " + bookId + "||" + chapterNo);
    }

    /**
     * 判断是否缓存文章分页结果
     *
     * @param chapter 章节id
     * @return
     */
    public boolean hasChapterCached(int chapter) {
        ArrayList<ChapterPage> chapterCache = chapters.get(bookId + "-" + chapter);
        return chapterCache != null && chapterCache.size() > 0;
    }

    /**
     * 读取文章并进行分页处理
     *
     * @param chapter 章节id
     * @return 章节内容的分页集合
     */
    public synchronized ArrayList<ChapterPage> getChapterContent(int chapter) {
        ArrayList<ChapterPage> pages = chapters.get(bookId + "-" + chapter);
        if (pages != null && pages.size() > 0) {
            //内存缓存中存在该章节图书，直接返回缓存，
            return pages;
        }
        //内存缓存中不存在该章节，读取文件缓存，然后添加到LRU缓存
        String temp = readChapterFile(chapter);
        if (temp == null) {
            //无本地缓存
            return null;
        }
        try {
            //分页章节内容
            pages = split(chapter, temp, mLineWordCount * 2, "GBK");
            chapters.put(bookId + "-" + chapter, pages);
            return pages;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //编码不支持，结果为空
        return null;
    }

    /**
     * 读取文章的段落集合
     * 返回文本单个章节内容
     */
    private String readChapterFile(int chapter) {
        String temp = "";
        BufferedReader bufferedReader = null;
        File txtFile = findBookChapterFile(bookId, chapter);
        if (txtFile == null) {
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO 编码错误
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            // TODO 文件不存在
            e.printStackTrace();
            return null;
        }
        //章节文件缓存读取成功，进行逐行读取文本
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                temp += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return temp;
    }

    /***
     * 分页处理
     * @param chapter 章节
     * @param text 章节内容
     * @param length 长度
     * @param encoding 编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public ArrayList<ChapterPage> split(int chapter, String text, int length, String encoding) throws UnsupportedEncodingException {
        //一章文本进行分页，每一个 ChapterPage 是一页内容
        ArrayList<ChapterPage> texts = new ArrayList<>();
        final String endChar = "";
        String temp = "    ";
        String c;
        int lines = 0;  //行数
        int pos = 2;    //单行字节位置，控制每一行字符数
        int startInd = 0;
        //循环整个章节文本
        for (int i = 0; text != null && i < text.length(); ) {
            byte[] b = String.valueOf(text.charAt(i)).getBytes(encoding);
            pos += b.length;
            if (pos >= length) {
                int endInd;
                if (pos == length) {
                    endInd = ++i;
                } else {
                    endInd = i;
                }
                temp += text.substring(startInd, endInd) + endChar; // 加入一行
                Log.e("CONTENT:", "lines=" + lines + "|" + text.substring(startInd, endInd));
                lines++;
                if (lines == mLineCount) { // 超出一页
                    Log.d("Lines=", "NO_N==" + lines);
                    texts.add(new ChapterPage(chapter, texts.size(), temp)); // 加入
                    temp = "";
                    lines = 0;
                }
                pos = 0;
                startInd = i;
            } else {
                c = new String(b, encoding);
                if (c.equals("\n")) {
                    temp += text.substring(startInd, i + 1);
                    Log.e("CONTENT:", "lines=" + lines + "|" + text.substring(startInd, i + 1));
                    lines++;
                    if (lines == mLineCount) {
                        Log.d("Lines=", "n==" + lines);
                        texts.add(new ChapterPage(chapter, texts.size(), temp)); // 加入
                        temp = "";
                        lines = 0;
                    }
                    temp += "    ";
                    pos = 2;
                    startInd = i + 1;
                }
                i++;
            }
        }
        if (startInd < text.length()) {
            temp += text.substring(startInd) + endChar;
            Log.e("CONTENT:", "lines=" + lines + "|" + text.substring(startInd));
            lines++;
        }
        if (!TextUtils.isEmpty(temp)) {
            Log.d("Lines=", "startInd==" + lines);
            if (!TextUtils.isEmpty(temp.trim())) {
                texts.add(new ChapterPage(chapter, texts.size(), temp)); // 加入
            }
        }
        return texts;
    }

    /**
     * 获取章节文本缓存文件
     *
     * @param chapter
     * @return
     */
    private static File findBookChapterFile(String bookId, int chapter) {
        File file = new File(basePath + bookId + File.separator + chapter + CHAPTER_FILE_EXTENSION);
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    /**
     * 获取章节文本缓存文件
     *
     * @param chapter
     * @return
     */
    private static File getBookChapterFile(String bookId, int chapter) {
        File file = new File(basePath + bookId + File.separator + chapter + CHAPTER_FILE_EXTENSION);
        if (!file.exists()) {
            FileUtils.createFile(file);
        }
        return file;
    }

}
