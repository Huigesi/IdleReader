package com.example.administrator.idlereader.movie.model;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface IMoviesModel {
    void loadMovies(String total,Map<String, String> params,
                    IMoviesLoadListener iMoviesLoadListener);
}
