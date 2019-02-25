package com.example.medico.Fragments;

import com.example.medico.Notifications.MyResponse;
import com.example.medico.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:applicaation/json",
                    "Authorization:Key=AAAAyEKQpHQ:APA91bF-FMOEnjqBSj1nl-csaN01jgrq71JVztZykboFPQA6X1H9qO9YU54miQx4THQuUItWOaVfCCTI7j7n1HaKsx9SxW63t9z8Pg9lm6NLXS1T82MHICvfWR0CR89OhhdBQWr-62s3"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
