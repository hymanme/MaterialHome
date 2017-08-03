package com.hymane.materialhome.bean.http.douban;

import com.hymane.materialhome.bean.BookRatingBean;
import com.hymane.materialhome.bean.ImageBean;
import com.hymane.materialhome.bean.SeriesBean;
import com.hymane.materialhome.bean.BookTagBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/8
 * Description:
 */
public class BookInfoResponse implements Serializable {
    public static final long serialVersionUID = 7060254125600464481L;
    public static final String serialVersionName = "bookInfo";
    private String id;
    private String title;
    private String subtitle;
    private String publisher;
    private BookRatingBean rating;
    private String[] author;
    private String[] translator;
    private String pubdate;
    private List<BookTagBean> tags;
    private String image;
    private String ebook_url;
    private String pages;
    private String binding;
    private String origin_title;
    private ImageBean images;
    private String isbn13;
    private String author_intro;
    private String summary;
    private String ebook_price;
    private String price;
    private SeriesBean series;
    private String alt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static String getSerialVersionName() {
        return serialVersionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BookRatingBean getRating() {
        return rating;
    }

    public void setRating(BookRatingBean rating) {
        this.rating = rating;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String[] getTranslator() {
        return translator;
    }

    public void setTranslator(String[] translator) {
        this.translator = translator;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public List<BookTagBean> getTags() {
        return tags;
    }

    public void setTags(List<BookTagBean> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public ImageBean getImages() {
        return images;
    }

    public void setImages(ImageBean images) {
        this.images = images;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEbook_price() {
        return ebook_price;
    }

    public void setEbook_price(String ebook_price) {
        this.ebook_price = ebook_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public String getUrl() {
        return alt;
    }

    public void setUrl(String url) {
        this.alt = url;
    }

    public String getInfoString() {
        if (this.author.length > 0) {
            return this.author[0].split("、")[0] + "/" + this.publisher + "/" + this.pubdate;
        }
        return "-";
    }
}
