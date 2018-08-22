package com.example.administrator.idlereader.http;

import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.TodayBean;
import com.example.administrator.idlereader.bean.VideoUrlBean;
import com.example.administrator.idlereader.bean.WeatherBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    /*
    *crt	1534158890303
    night	0
    channel	hupucom
    sign	7c1538b4ca3924cd846a153cab76abad
    client	866493031799950
    _ssid	IkxpZUJhb1dpRmk1NzEi
    time_zone	Asia/Shanghai
    android_id	8e204243037d4aa1
    entrance	-1
    preload	1

    crt	1534158910428
    night	0
    channel	hupucom
    nid	2333899
    sign	a3cc4366cacc61ffd9783bd907591eb5
    _ssid	IkxpZUJhb1dpRmk1NzEi
    time_zone	Asia/Shanghai
    client	866493031799950
    direc	next
    android_id	8e204243037d4aa1
    entrance	-1
    pre_count	20
    */
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
    @GET("comments/build_comments")
    Observable<WeiBoDetail> getWeiBoDetail(@Query("s") String s,
                                           @Query("c") String c,
                                           @Query("id") String id,
                                           @Query("gsid") String gsid,
                                           @Query("is_show_bulletin") int is_show_bulletin);
}
