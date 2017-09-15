package vn.nano.hackernewsdemo.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import vn.nano.core_library.utils.ApiErrorUtils;
import vn.nano.hackernewsdemo.data.model.TopStory;

/**
 * Created by alex on 9/14/17.
 */

public interface HackerNewsService {

    String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    @GET("topstories.json")
    Observable<List<Integer>> getTopStoryIds();

    @GET("item/{story_id}.json")
    Observable<TopStory> getTopStory(@Path("story_id") int storyId);

    class Factory {
        public static HackerNewsService create() {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new StethoInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(HackerNewsService.class);
        }
    }

    class ApiError extends ApiErrorUtils.ApiError {

        @SerializedName("error")
        @Expose
        private String error;

        @Override
        protected String getErrorMessage() {
            return error;
        }

    }

}
