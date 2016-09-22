package com.hymane.materialhome.common;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/7
 * Description:
 */
public class Constant {
    public static final String THEME_MODEL = "theme_model";
    public static final String USER_GENDER = "user_gender";

    /**
     * category index
     */
    public static final int CATEGORY_LITERATURE = 0;
    public static final int CATEGORY_POPULAR = 1;
    public static final int CATEGORY_CULTURE = 2;
    public static final int CATEGORY_LIFE = 3;
    public static final int CATEGORY_MANAGEMENT = 4;
    public static final int CATEGORY_TECHNOLOGY = 5;
    public static final int CATEGORY_COUNTRY = 6;
    public static final int CATEGORY_SUBJECT = 7;
    public static final int CATEGORY_AUTHOR = 8;
    public static final int CATEGORY_PUBLISHER = 9;
    public static final int CATEGORY_THRONG = 10;
    public static final int CATEGORY_RELIGION = 11;
    public static final int CATEGORY_OTHER = 12;

    //最热排行
    public static final String EBOOK_RANK_ID_HOT_MALE = "54d42d92321052167dfb75e3";
    public static final String EBOOK_RANK_ID_HOT_FEMALE = "54d43437d47d13ff21cad58b";
    //留存排行
    public static final String EBOOK_RANK_ID_RETAINED_MALE = "564547c694f1c6a144ec979b";
    public static final String EBOOK_RANK_ID_RETAINED_FEMALE = "5645482405b052fe70aeb1b5";
    //完结排行
    public static final String EBOOK_RANK_ID_FINISHED_MALE = "564eb878efe5b8e745508fde";
    public static final String EBOOK_RANK_ID_FINISHED_FEMALE = "564eb8a9cf77e9b25056162d";
    //潜力排行
    public static final String EBOOK_RANK_ID_POTENTIAL_MALE = "564547c694f1c6a144ec979b";
    public static final String EBOOK_RANK_ID_POTENTIAL_FEMALE = "5645482405b052fe70aeb1b5";

    @StringDef({
            Gender.MALE,
            Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }
}
