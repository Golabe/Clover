package cn.yoogr.clover.mvp.model;

/**
 * Created by yuequan on 2017/11/27.
 */

public class PhotoItemModel {

    /**
     * userPictureShareedCount : 0
     * title : a sword dance
     * photoCount : 0
     * uploaderInfo : {"nickName":"John Yu","avatar":{"a1":"https://img.500px.me/avatar-500px644027808e8da4ede3543e3bf99b5f5b2d55eae.jpg!a1","baseUrl":"https://img.500px.me/avatar-500px644027808e8da4ede3543e3bf99b5f5b2d55eae.jpg"},"userName":"px-John-Yu7","coverUrl":{"baseUrl":"https://img.500px.me/cover-500px6440278e645f9acbac2401a964f390f338de28b.jpg","id":"500px6440278"},"id":"500px6440278"}
     * id : 500px237115235
     * state : 0
     * height : 1100
     * uploaderId : 500px6440278
     * url : {"p1":"https://img.500px.me/500px237115235.jpg!p1","p2":"https://img.500px.me/500px237115235.jpg!p2","baseUrl":"https://img.500px.me/500px237115235.jpg","p3":"https://img.500px.me/500px237115235.jpg!p3","p4":"https://img.500px.me/500px237115235.jpg!p4","id":"500px237115235"}
     * commentCount : 0
     * pictureLikeedCount : 0
     * createdDate : 1511762917508
     * width : 768
     * resourceType : 0
     * rating : 34.2372
     * ratingMax : 34.2372
     */

    private int userPictureShareedCount;
    private String title;
    private int photoCount;
    private UploaderInfoBean uploaderInfo;
    private String id;
    private int state;
    private int height;
    private String uploaderId;
    private UrlBean url;
    private int commentCount;
    private int pictureLikeedCount;
    private long createdDate;
    private int width;
    private int resourceType;
    private double rating;
    private double ratingMax;

    public int getUserPictureShareedCount() {
        return userPictureShareedCount;
    }

    public void setUserPictureShareedCount(int userPictureShareedCount) {
        this.userPictureShareedCount = userPictureShareedCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public UploaderInfoBean getUploaderInfo() {
        return uploaderInfo;
    }

    public void setUploaderInfo(UploaderInfoBean uploaderInfo) {
        this.uploaderInfo = uploaderInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public UrlBean getUrl() {
        return url;
    }

    public void setUrl(UrlBean url) {
        this.url = url;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPictureLikeedCount() {
        return pictureLikeedCount;
    }

    public void setPictureLikeedCount(int pictureLikeedCount) {
        this.pictureLikeedCount = pictureLikeedCount;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRatingMax() {
        return ratingMax;
    }

    public void setRatingMax(double ratingMax) {
        this.ratingMax = ratingMax;
    }

    public static class UploaderInfoBean {
        /**
         * nickName : John Yu
         * avatar : {"a1":"https://img.500px.me/avatar-500px644027808e8da4ede3543e3bf99b5f5b2d55eae.jpg!a1","baseUrl":"https://img.500px.me/avatar-500px644027808e8da4ede3543e3bf99b5f5b2d55eae.jpg"}
         * userName : px-John-Yu7
         * coverUrl : {"baseUrl":"https://img.500px.me/cover-500px6440278e645f9acbac2401a964f390f338de28b.jpg","id":"500px6440278"}
         * id : 500px6440278
         */

        private String nickName;
        private AvatarBean avatar;
        private String userName;
        private CoverUrlBean coverUrl;
        private String id;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public CoverUrlBean getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(CoverUrlBean coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarBean {
            /**
             * a1 : https://img.500px.me/avatar-500px644027808e8da4ede3543e3bf99b5f5b2d55eae.jpg!a1
             * baseUrl : https://img.500px.me/avatar-500px644027808e8da4ede3543e3bf99b5f5b2d55eae.jpg
             */

            private String a1;
            private String baseUrl;

            public String getA1() {
                return a1;
            }

            public void setA1(String a1) {
                this.a1 = a1;
            }

            public String getBaseUrl() {
                return baseUrl;
            }

            public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
            }
        }

        public static class CoverUrlBean {
            /**
             * baseUrl : https://img.500px.me/cover-500px6440278e645f9acbac2401a964f390f338de28b.jpg
             * id : 500px6440278
             */

            private String baseUrl;
            private String id;

            public String getBaseUrl() {
                return baseUrl;
            }

            public void setBaseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }

    public static class UrlBean {
        /**
         * p1 : https://img.500px.me/500px237115235.jpg!p1
         * p2 : https://img.500px.me/500px237115235.jpg!p2
         * baseUrl : https://img.500px.me/500px237115235.jpg
         * p3 : https://img.500px.me/500px237115235.jpg!p3
         * p4 : https://img.500px.me/500px237115235.jpg!p4
         * id : 500px237115235
         */

        private String p1;
        private String p2;
        private String baseUrl;
        private String p3;
        private String p4;
        private String id;

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getP3() {
            return p3;
        }

        public void setP3(String p3) {
            this.p3 = p3;
        }

        public String getP4() {
            return p4;
        }

        public void setP4(String p4) {
            this.p4 = p4;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
