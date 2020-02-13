package linkcollection.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ResponseUtil {

    public static final ScheduledExecutorService SERVICE = Executors.newScheduledThreadPool(5);

    public static ScheduledFuture<String> response(Call<ResponseBody> call){
        ScheduledFuture<String> future = SERVICE.schedule(() -> {
            try {
                ResponseBody body = call.execute().body();
                return body == null ? null : body.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }, 0, TimeUnit.MILLISECONDS);
        return future;
    }
}
