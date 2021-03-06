package com.example.administrator.idlereader.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.example.administrator.idlereader.MyFragmentAdapter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.news.nba.HupuNBAFragment;
import com.example.administrator.idlereader.news.weibo.WeiBoFragment;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class FgNewsFragment extends Fragment {

    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_JOKES = 2;
    public static final int NEWS_TYPE_GAME = 3;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();
    private TabLayout tl_news;
    private ViewPager vp_news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_news, null);
    }

    public static FgNewsFragment getInstance() {
        FgNewsFragment fragment = new FgNewsFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tl_news = (TabLayout) view.findViewById(R.id.tl_news);
        vp_news = (ViewPager) view.findViewById(R.id.vp_news);
        setViewPager();
        //预加载界面数
        vp_news.setOffscreenPageLimit(3);
        tl_news.setupWithViewPager(vp_news);
        int marge = Resolution.dipToPx(getContext(), 25);
        UIUtils.setUpIndicatorWidth(tl_news, marge, marge);
    }

    private void setViewPager() {
        fragments.add(FgNewsListFragment.newInstance(NEWS_TYPE_TOP));
        fragments.add(HupuNBAFragment.getInstance());
        fragments.add(WeiBoFragment.getInstance());
        fragments.add(FgNewsListFragment.newInstance(NEWS_TYPE_GAME));
        fragmentTitles.add("头条");
        fragmentTitles.add("NBA");
        fragmentTitles.add("微博");
        fragmentTitles.add("游戏");
        MyFragmentAdapter adapter = new MyFragmentAdapter(getChildFragmentManager(),
                fragments, fragmentTitles);
        vp_news.setAdapter(adapter);

    }

}
