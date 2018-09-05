package com.example.administrator.idlereader.movie.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface IMoviesPresenter {
    void loadMovies(String total,Map<String, String> params);
}
