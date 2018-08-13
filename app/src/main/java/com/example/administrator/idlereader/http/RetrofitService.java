package com.example.administrator.idlereader.http;

import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.TodayBean;
import com.example.administrator.idlereader.bean.VideoUrlBean;
import com.example.administrator.idlereader.bean.WeatherBean;

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
}
