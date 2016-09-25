package pl.marchuck.protobuffersexample;

import pl.marchuck.protobuffersexample.model.UserProtos;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Lukasz Marczak
 * @since 25.09.16.
 */
public class ProtocolBufferAPI {


    public API init() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.36:8080")
                .addConverterFactory(ProtoConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(API.class);
    }

    interface API {
        @GET("/customers/{id}")
        rx.Observable<UserProtos.User> getUser(@Path("id") int id);
    }
}
