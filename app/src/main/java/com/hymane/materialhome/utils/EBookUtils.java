package com.hymane.materialhome.utils;

import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.common.URL;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public class EBookUtils {
    public static String getHotRankingId(@Constant.Gender String gender) {
        if (gender.equals(Constant.Gender.MALE)) {
            return Constant.EBOOK_RANK_ID_HOT_MALE;
        } else {
            return Constant.EBOOK_RANK_ID_HOT_FEMALE;
        }
    }

    public static String getRetainedRankingId(@Constant.Gender String gender) {
        if (gender.equals(Constant.Gender.MALE)) {
            return Constant.EBOOK_RANK_ID_RETAINED_MALE;
        } else {
            return Constant.EBOOK_RANK_ID_RETAINED_FEMALE;
        }
    }

    public static String getFinishedRankingId(@Constant.Gender String gender) {
        if (gender.equals(Constant.Gender.MALE)) {
            return Constant.EBOOK_RANK_ID_FINISHED_MALE;
        } else {
            return Constant.EBOOK_RANK_ID_FINISHED_FEMALE;
        }
    }

    public static String getPotentialRankingId(@Constant.Gender String gender) {
        if (gender.equals(Constant.Gender.MALE)) {
            return Constant.EBOOK_RANK_ID_POTENTIAL_MALE;
        } else {
            return Constant.EBOOK_RANK_ID_POTENTIAL_FEMALE;
        }
    }

    public static String getImageUrl(String name) {
        return URL.HOST_URL_ZSSQ_IMG + name;
    }
}
