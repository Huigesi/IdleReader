package com.example.administrator.idlereader.http;

import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaBBSComment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSLightComment;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.hupu.NbaZhuanti;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.TodayBean;
import com.example.administrator.idlereader.bean.VideoUrlBean;
import com.example.administrator.idlereader.bean.WeatherBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface RetrofitService {
    /*
     * http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
     * */
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<NewsBean> getNews(@Path("type") String type,
                                 @Path("id") String id,
                                 @Path("startPage") int startPage);

    /*
     * https://api.douban.com/v2/movie/in_theaters
     * */
    @GET("/v2/movie/{total}")
    Observable<MoviesBean> getMovie(@Path("total") String total,
                                    @Query("city") String city,
                                    @Query("start") int start,
                                    @Query("count") int count);

    /*
     * http://is.snssdk.com/api/news/feed/v51/?category=video
     * */
    @GET("news/feed/v51/")
    Observable<TodayBean> getToday(@Query("category") String category);

    /*
     * http://ib.365yg.com/video/urls/v/1/toutiao/mp4/v02004f00000bbpbk3l2v325q7lmkds0?r=6781281688452415&s=2734808831
     * */
    @GET
    Observable<VideoUrlBean> getVideoUrl(@Url String url);

    //Api:http://wthrcdn.etouch.cn/weather_mini?citykey=101010100
    @GET("weather_mini")
    Observable<WeatherBean> getWeather(@Query("citykey") int cityKey);

    /*http://games.mobileapi.hupu.com/1/7.2.5/nba/getNews?&client=866493031799950*/
    @GET("nba/getNews")
    Observable<HupuNews> getHupuNews(@Query("client") String client,
                                     @Query("nid") String nid,
                                     @Query("count") int count);

    //http://games.mobileapi.hupu.com/1/7.2.5/nba/getNewsDetailSchema?&nid=2334107&client=866493031799950
    @GET("nba/getNewsDetailSchema")
    Observable<NbaDetailNews> getNbaNewsDetail(@Query("nid") String nid,
                                               @Query("client") String client);

    //http://games.mobileapi.hupu.com/1/7.2.5/news/getCommentH5?nid=2334431&client=866493031799950&ncid=51801640&create_time=1534484055%20HTTP/1.1
    @GET("news/getCommentH5")
    Observable<NbaNewsComment> getNewsComment(@Query("nid") String nid,
                                              @Query("client") String client,
                                              @Query("ncid") String ncid,
                                              @Query("create_time") String create_time);

    //http://games.mobileapi.hupu.com/1/7.2.5/nba/getSubjectNewsDetail?nid=226381
    @GET("nba/getSubjectNewsDetail")
    Observable<NbaZhuanti> getNewsZhuanTi(@Query("nid") String nid,
                                          @Query("client") String client);

    /*_2A252cRzBDeRxGeNH61cX8yvNyT6IHXVTJxcJrDV6PUJbkdAKLUfykWpNSvDZShbJn5J7L7wv7ZqcP0d-KAnwRoKc
     * http://api.weibo.cn/2/statuses/friends_timeline?since_id=4272884404727884&s=606388e6&gsid=_2A252dj4mDeRxGeNH61cX8yvNyT6IHXVTIjburDV6PUJbkdAKLUfykWpNSvDZSoix4-U-J81fM0h-0Wgync7r9M9c&c=weicoabroad&page=1%20HTTP/1.1
     *since_id决定加载数
     * s 校验参数
     * gsid 登录用户参数
     * page 不写会为空
     * c	 ？weicoabroad
     * */
    @GET("statuses/friends_timeline")
    Observable<WeiBoNews> getWeiBoNews(@Query("since_id") String sinceId,
                                       @Query("s") String s,
                                       @Query("gsid") String gsid,
                                       @Query("page") int page,
                                       @Query("c") String c,
                                       @Query("from") String from,
                                       @Query("wm") String wm,
                                       @Query("source") String source,
                                       @Query("advance_enable") String advance_enable);

    //http://api.weibo.cn/2/statuses/show?s=606388e6&c=weicoabroad&id=4275531484257587&gsid=_2A252f6O1DeRxGeNH61cX8yvNyT6IHXVTLLB9rDV6PUJbkdAKLUfykWpNSvDZSiGWa7gnoPuUTJwYjvaIpOJpb1B-&%20HTTP/1.1
   /* @GET("statuses/show")
    Observable<WeiBoDetail> getWeiBoDetail(@Query("s") String s,
                                           @Query("c") String c,
                                           @Query("id") String id,
                                           @Query("gsid") String gsid);*/
    //http://api.weibo.cn/2/comments/build_comments?s=606388e6&gsid=_2A252f6O1DeRxGeNH61cX8yvNyT6IHXVTLLB9rDV6PUJbkdAKLUfykWpNSvDZSiGWa7gnoPuUTJwYjvaIpOJpb1B-&c=weicoabroad&id=4275839388884486&is_show_bulletin=2%20HTTP/1.1
    //http://api.weibo.cn/2/comments/build_comments?s=606388e6&source=4215535043&max_id_type=0&wm=2468_1001&gsid=_2A252f6O1DeRxGeNH61cX8yvNyT6IHXVTLLB9rDV6PUJbkdAKLUfykWpNSvDZSiGWa7gnoPuUTJwYjvaIpOJpb1B-&count=20&from=1073095010&c=weicoabroad&id=4275833457776921&lang=zh_CN&is_show_bulletin=2&flow=0&v_p=48&max_id=142540421497615%20HTTP/1.1
    @GET("comments/build_comments")
    Observable<WeiBoDetail> getWeiBoDetail(@Query("s") String s,
                                           @Query("c") String c,
                                           @Query("id") String id,
                                           @Query("gsid") String gsid,
                                           @Query("max_id") long max_id,
                                           @Query("is_show_bulletin") int is_show_bulletin);

    //http://bbs.mobileapi.hupu.com/1/7.2.5/threads/getThreadDetailInfoH5?tid=23331153
    @GET("threads/getsThreadLightReplyList")
    Observable<NbaBBSLightComment> getThreadLightReplyList(@QueryMap Map<String, String> params);

    //http://bbs.mobileapi.hupu.com/1/7.0.8/threads/getsThreadLightReplyList?page=1&tid=23387013
    @GET("threads/getsThreadPostList")
    Observable<NbaBBSComment> getsThreadReplyList(@QueryMap Map<String, String> params);

    //http://api.weibo.cn/2/statuses/user_timeline?since_id=0&s=606388e6&source=4215535043&gsid=_2A252evSNDeRxGeNH61cX8yvNyT6IHXVTLg9FrDV6PUJbkdAKLUfykWpNSvDZShYMkVspdJ9M8k0Zb7-bXokiidm6&from=1055095010&c=weicoabroad&uid=1219022557&page=1&%20HTTP/1.1
    @GET("statuses/user_timeline")
    Observable<WeiBoNews> getWeiBoUser(@Query("since_id") String sinceId,
                                       @Query("s") String s,
                                       @Query("gsid") String gsid,
                                       @Query("page") int page,
                                       @Query("c") String c,
                                       @Query("from") String from,
                                       @Query("wm") String wm,
                                       @Query("source") String source,
                                       @Query("uid") String uid);

    //http://api.weibo.cn/2/users/show?s=606388e6&source=4215535043&c=weicoabroad&wm=2468_1001&gsid=_2A252evSNDeRxGeNH61cX8yvNyT6IHXVTLg9FrDV6PUJbkdAKLUfykWpNSvDZShYMkVspdJ9M8k0Zb7-bXokiidm6&lang=zh_CN&uid=1219022557&from=1055095010%20HTTP/1.1
    @GET("users/show")
    Observable<WeiBoSpaceUser> getWeiBoUserHead(@Query("since_id") String sinceId,
                                                @Query("s") String s,
                                                @Query("gsid") String gsid,
                                                @Query("c") String c,
                                                @Query("from") String from,
                                                @Query("wm") String wm,
                                                @Query("source") String source,
                                                @Query("uid") String uid);
    @GET("users/show")
    Observable<WeiBoSpaceUser> getWeiBoUserShowHead(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("account/login")
    Observable<WeiBoUserInfo> login(@Field("c") String c,
                                    @Field("s") String s,
                                    @Field("u") String user,
                                    @Field("p") String password);
}
