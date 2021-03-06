package com.terry.app.zhdemo.bean;

import java.util.List;

/**
 * Created by Taozi on 2016/8/15.
 */
public class Latest {

    /**
     * date : 20160815
     * stories : [{"images":["http://pic3.zhimg.com/33d3a44ac758255ef9c1607d1e9cb18e.jpg"],"type":0,"id":8685560,"ga_prefix":"081513","title":"水母：虽然我很可能伤害你，但是我美呀"},{"images":["http://pic1.zhimg.com/734f89ff4638b865ed08a0a00ee69458.jpg"],"type":0,"id":8687506,"ga_prefix":"081512","title":"大误 · 地球上有五个王，人类，阻止他们见面"},{"images":["http://pic1.zhimg.com/649628e8f9a90e3d47e67adeb05d5954.jpg"],"type":0,"id":8668427,"ga_prefix":"081511","title":"提升父母生活质量的利器：一副合适的老花眼镜"},{"images":["http://pic1.zhimg.com/c7db9ece589de25b5e32daf275cf0d4c.jpg"],"type":0,"id":8685068,"ga_prefix":"081510","title":"强酸和强碱直接接触到人的皮肤，哪个更可怕？"},{"images":["http://pic1.zhimg.com/f2b39f30a74cd0fec14e1ebc387c5eec.jpg"],"type":0,"id":8686182,"ga_prefix":"081509","title":"猫头鹰告诉你，为什么你在一堆「T」里找不到「I」？"},{"images":["http://pic1.zhimg.com/c6d32c6f345e017a93eb9550129b0560.jpg"],"type":0,"id":8686414,"ga_prefix":"081508","title":"如果一定要做亲子鉴定\u2026\u2026先做这道喵星人的题"},{"images":["http://pic3.zhimg.com/298289bae14e0656381d0aebc4bf530a.jpg"],"type":0,"id":8685231,"ga_prefix":"081507","title":"挑对象、搞暧昧与决定分手，靠感觉？还是博弈论？"},{"images":["http://pic3.zhimg.com/659ad28fb0fd76d11d3e86fb848bb2fa.jpg"],"type":0,"id":8686508,"ga_prefix":"081507","title":"碳纤维有多强？最不缺钱的 F1 车队都在一直用呢"},{"title":"国产电影用八个月，交出了一份糟糕的答卷","ga_prefix":"081507","images":["http://pic2.zhimg.com/82f52a22b84b79ad85172711616d5321.jpg"],"multipic":true,"type":0,"id":8686818},{"images":["http://pic3.zhimg.com/5df734e93626e77175c4091658ff3332.jpg"],"type":0,"id":8686861,"ga_prefix":"081507","title":"读读日报 24 小时热门 TOP 5 · 我在微信朋友圈养了 4 个女骗子"},{"images":["http://pic3.zhimg.com/e9a6149a387aec3557c2be48739d86f2.jpg"],"type":0,"id":8686569,"ga_prefix":"081506","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/c76e8648b8a76daabe52f7888e806e99.jpg","type":0,"id":8668427,"ga_prefix":"081511","title":"提升父母生活质量的利器：一副合适的老花眼镜"},{"image":"http://pic4.zhimg.com/9df9623a33e568c4923c99d563391f23.jpg","type":0,"id":8686818,"ga_prefix":"081507","title":"国产电影用八个月，交出了一份糟糕的答卷"},{"image":"http://pic4.zhimg.com/cc52bb034034bbc2dd71422a9eecc267.jpg","type":0,"id":8686861,"ga_prefix":"081507","title":"读读日报 24 小时热门 TOP 5 · 我在微信朋友圈养了 4 个女骗子"},{"image":"http://pic4.zhimg.com/b9c2a2d89d5e8338deb9659ec7d1edc7.jpg","type":0,"id":8685278,"ga_prefix":"081420","title":"整点儿奥运 · 每一个不会游泳的人都有自己的理由"},{"image":"http://pic4.zhimg.com/74edb8c697968bb422625559fd2aee3f.jpg","type":0,"id":8685662,"ga_prefix":"081416","title":"Wings 这 5 位中国少年，今天一人赚了 1000 万"}]
     */

    private String date;
    /**
     * images : ["http://pic3.zhimg.com/33d3a44ac758255ef9c1607d1e9cb18e.jpg"]
     * type : 0
     * id : 8685560
     * ga_prefix : 081513
     * title : 水母：虽然我很可能伤害你，但是我美呀
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic2.zhimg.com/c76e8648b8a76daabe52f7888e806e99.jpg
     * type : 0
     * id : 8668427
     * ga_prefix : 081511
     * title : 提升父母生活质量的利器：一副合适的老花眼镜
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
