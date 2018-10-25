package com.nomad.rxjavaandretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomad.domain.MovieBean;
import com.nomad.network.MovieRetrofit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lv_main) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Observable<MovieBean> movieBeanObservable =
                MovieRetrofit.getInstance().getApi().listTop250(0, 200);

        movieBeanObservable.subscribeOn(Schedulers.io())
                .map(new Function<MovieBean, List<String>>() {
                    @Override
                    public List<String> apply(MovieBean movieBean) throws Exception {
                        List<String> array = new ArrayList<>();
                        for (int i = 0; i < movieBean.getSubjects().size(); i++) {
                            String title = movieBean.getSubjects().get(i).getTitle();

                            array.add(title);
                        }
                        return array;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        ArrayAdapter<String> arrayAdapter =
                                new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1
                                        , strings);
                        mListView.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
