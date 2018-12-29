package edu.com.medicalapp.Retrofit;

import java.util.List;

import edu.com.medicalapp.Models.Course;
import edu.com.medicalapp.Models.LoginRequest;
import edu.com.medicalapp.Models.LoginResponse;
import retrofit2.Callback;

public class RestClient {
    private static final String TAG = "RestClient";


    public static void loginUser(LoginRequest loginRequest, Callback<LoginResponse> callback) {
        RetrofitClient.getClient().loginUser(loginRequest).enqueue(callback);
    }




    public static void getCourses(String token, Callback<List<Course>> callback) {
        RetrofitClient.getClient().getCourse(token).enqueue(callback);
    }

}