package com.hymane.materialhome.bean.http.ebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public class HotReview extends Base {
    /**
     * _id : 5652c048a003ea7f55f74b78
     * rating : 3
     * content : 本人试毒500，前面还好可是后面真是索然无味，猪脚最开始魂魄寄存在造化玉碟碎片之中，后来结识盘古，但是连比较弱的混沌神魔都打不过的货色竟然帮助盘古开天？盘古就给他精血融合，那么为什么精血化形的12祖巫一点感应都没有？在盘古身陨是将50道鸿蒙紫气其中那遁去的一道.也就是那大道50中的1给了猪脚。可是之后的章节中直到猪脚成圣都再也未有看到，造化玉碟碎片作为猪脚混沌中的寄身之处也是莫名其妙不见鸟？猪脚作为圣人竟然不知道妻子玲玲前世自己死了之后发生了什么？他们可是夫妻有因果气运相连的啊，最最看不懂的是。为什么东皇太一是哥哥啊为什么他是妖皇，帝俊怎么就成弟弟了？为什么帝俊用的是混沌钟，为什么巫妖结仇仅仅是东皇太一帝俊出生散发的太阳真火本源被祝融吸收一些就在以后成为死敌？猪脚创下造化一脉自称造化天尊可是你造化一脉到底是什么教义？巫妖两族都杀戮不少人族可是为什么妖族能发现人族元神精魄可破巫族肉身，巫族就没有任何发现？要知道当时女娲以成圣，也就是说妖族当时在力量还是气运上都要强于巫族，那么天道会不帮巫族？不帮巫族等到巫妖大战如何同妖族两败俱伤？
     * 其实前面龙凤三族的时代我竟然没有看到皇天，阴阳，颠倒，五行，等等这些大神成名？直到猪脚讲道，杀罗睺才冒泡2次，这些人不牛逼？当时的时代不是三族主宰，应该是他们称霸，直到大战罗睺陨落了才轮到三族！
     * 总得来说此书不该有的漏洞实在有点多，想那原始天尊又不是没看到猪脚力抗鸿钧不落下风，那么为何他数次敢对猪脚冷眼热潮？他就这么傻？
     * 此书人物刻画实在模糊。故事情节和其他洪荒小说没什么不同。虽然在其中加了一些东西，但说实话那实在有些碍眼！作者好像对两性相处写的并不是那么好，那请你不要在加上两人的甜蜜生活了，我看着真心胃呕，蛋疼
     * title : 越到后面越是索然无味
     * author : {"_id":"534a355422f7a7c71f0034d7","avatar":"/avatar/77/52/7752998834f5e632a404622f485343eb","nickname":"我就是静静","type":"normal","lv":8,"gender":"female"}
     * helpful : {"yes":304,"total":241,"no":63}
     * likeCount : 16
     * state : normal
     * updated : 2016-08-02T09:49:36.719Z
     * created : 2015-11-23T07:29:12.396Z
     * commentCount : 141
     */

    private List<Reviews> reviews;

    public HotReview() {
        this.reviews = new ArrayList<>();
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public static class Reviews {
        private String _id;
        private int rating;
        private String content;
        private String title;
        /**
         * _id : 534a355422f7a7c71f0034d7
         * avatar : /avatar/77/52/7752998834f5e632a404622f485343eb
         * nickname : 我就是静静
         * type : normal
         * lv : 8
         * gender : female
         */

        private Author author;
        /**
         * yes : 304
         * total : 241
         * no : 63
         */

        private Helpful helpful;
        private int likeCount;
        private String state;
        private String updated;
        private String created;
        private int commentCount;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Helpful getHelpful() {
            return helpful;
        }

        public void setHelpful(Helpful helpful) {
            this.helpful = helpful;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public static class Author {
            private String _id;
            private String avatar;
            private String nickname;
            private String type;
            private int lv;
            private String gender;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getLv() {
                return lv;
            }

            public void setLv(int lv) {
                this.lv = lv;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }
        }

        public static class Helpful {
            private int yes;
            private int total;
            private int no;

            public int getYes() {
                return yes;
            }

            public void setYes(int yes) {
                this.yes = yes;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }
        }
    }
}
