package kin.kinoverflow.network;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AwardServiceMock {

    public static final String URL_GET_KIN = "http://kin-faucet.rounds.video/send?public_address=";
    private ExecutorService executorService;
    private OkHttpClient okHttpClient;

    public AwardServiceMock(){
        executorService = Executors.newSingleThreadExecutor();
        okHttpClient = new OkHttpClient();
    }

    public void awardKin (final String publicAddress) throws IOException {
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                    .url(URL_GET_KIN + publicAddress)
                    .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Log.d("AwardService", "Award kin response: " + response.body().string());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}