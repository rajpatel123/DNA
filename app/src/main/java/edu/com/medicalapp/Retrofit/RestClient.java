package edu.com.medicalapp.Retrofit;

import edu.com.medicalapp.Models.QustionDetails;
import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.Models.video.VideoList;
import edu.com.medicalapp.Models.login.loginResponse;
import edu.com.medicalapp.Models.maincat.CategoryDetailData;
import edu.com.medicalapp.Models.registration.CommonResponse;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class RestClient {
    private static final String TAG = "RestClient";

/*

    public static void loginUser(LoginRequest loginRequest, Callback<LoginResponse> callback) {
        RetrofitClient.getClient().loginUser(loginRequest).enqueue(callback);
    }
*/


    public static void loginUser(RequestBody email, RequestBody password, Callback<loginResponse> callback) {
        RetrofitClient.getClient().loginUser(email, password).enqueue(callback);
    }

    public static void registerUser(RequestBody name, RequestBody username, RequestBody email_id, RequestBody password, Callback<CommonResponse> callback) {
        RetrofitClient.getClient().registerUser(name, username, email_id, password).enqueue(callback);
    }


    public static void getCourses(Callback<CategoryDetailData> callback) {
        RetrofitClient.getClient().getCourse().enqueue(callback);
    }


    public static void getVideos(String sub_child_cat, String fileType, Callback<VideoList> callback) {
        RetrofitClient.getClient().getVideos(sub_child_cat, fileType).enqueue(callback);
    }

    public static void getQuestion(String test_id,Callback<QustionDetails> callback) {
        RetrofitClient.getClient().getQuestion(test_id).enqueue(callback);
    }

    public static void getTest( Callback<TestQuestionData> callback) {
        RetrofitClient.getClient().getTest().enqueue(callback);
    }

}