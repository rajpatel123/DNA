package edu.com.medicalapp.Retrofit;

import java.util.List;

import edu.com.medicalapp.Models.Course;
import edu.com.medicalapp.Models.LoginRequest;
import edu.com.medicalapp.Models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);



    @GET("api/course")
    Call<List<Course>> getCourse(@Header("token") String token);



}