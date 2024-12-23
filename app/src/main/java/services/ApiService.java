package services;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {

    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();

    public <T> Single<T> get(String url, Type typeOfT) {
        return Single.<T>create(emitter -> {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    emitter.onError(e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();

                        try {
                            JsonAdapter<T> adapter = moshi.adapter(typeOfT);
                            T parsedResponse = adapter.fromJson(result);
                            if (parsedResponse != null) {
                                emitter.onSuccess(parsedResponse);
                            } else {
                                emitter.onError(new IOException("Failed to parse response"));
                            }
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    } else {
                        emitter.onError(new IOException("Request failed with code: " + response.code()));
                    }


                }
            });
        }).subscribeOn(Schedulers.io());
    }
}
