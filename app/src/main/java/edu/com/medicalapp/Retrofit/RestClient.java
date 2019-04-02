package edu.com.medicalapp.Retrofit;

import edu.com.medicalapp.Models.QbankSubCat.QbankSubResponse;
import edu.com.medicalapp.Models.QbankSubTest.QbankTestResponse;
import edu.com.medicalapp.Models.QustionDetails;
import edu.com.medicalapp.Models.ResultData.ResultList;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.Models.VerifyOtpResponse;
import edu.com.medicalapp.Models.answer.SubmitAnswer;
import edu.com.medicalapp.Models.collegelist.CollegeListResponse;
import edu.com.medicalapp.Models.facebook.FacebookResponse;
import edu.com.medicalapp.Models.faculties.FacultyDetail;
import edu.com.medicalapp.Models.feedback.QbankfeedbackResponse;
import edu.com.medicalapp.Models.qbank.QbankResponse;
import edu.com.medicalapp.Models.qbankstart.QbankstartResponse;
import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.Models.video.VideoList;
import edu.com.medicalapp.Models.login.loginResponse;
import edu.com.medicalapp.Models.maincat.CategoryDetailData;
import edu.com.medicalapp.Models.registration.CommonResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    public static void registerUser(RequestBody name, RequestBody username, RequestBody email_id, RequestBody phone, RequestBody statetxt, RequestBody password, RequestBody college, MultipartBody.Part vFile, Callback<CommonResponse> callback) {
        RetrofitClient.getClient().registerUser(name, username, email_id, phone, statetxt, password, college).enqueue(callback);
    }

    public static void facebookRegister(RequestBody name, RequestBody email_id, RequestBody fb_id, Callback<FacebookResponse> callback) {
        RetrofitClient.getClient().facebookRegister(name, email_id, fb_id).enqueue(callback);
    }

    public static void getCourses(Callback<CategoryDetailData> callback) {
        RetrofitClient.getClient().getCourse().enqueue(callback);
    }

    public static void getCollege(Callback<CollegeListResponse> callback) {
        RetrofitClient.getClient().collegeData().enqueue(callback);
    }


    public static void submitAnswer(String q_id, String u_id, String is_completed, String userAnswer, Callback<SubmitAnswer> callback) {
        RetrofitClient.getClient().submitAnswer(q_id, u_id, is_completed, userAnswer).enqueue(callback);
    }


    public static void getVideos(String sub_child_cat, String fileType, Callback<VideoList> callback) {
        RetrofitClient.getClient().getVideos(sub_child_cat, fileType).enqueue(callback);
    }

    public static void getQuestion(String test_id, Callback<QustionDetails> callback) {
        RetrofitClient.getClient().getQuestion(test_id).enqueue(callback);
    }

    public static void getTest(Callback<TestQuestionData> callback) {
        RetrofitClient.getClient().getTest().enqueue(callback);
    }


    public static void submitTest(String user_id, String test_id,
                                  String tquestion, String ttquestion, String canswer, String ccanswer, String wanswer, String wwanswer, String sanswer, String ssanswer, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().submitTest(user_id, test_id, tquestion, ttquestion, canswer, ccanswer, wanswer, wwanswer, sanswer, ssanswer).enqueue(callback);

    }

    public static void resultList(RequestBody user_id, RequestBody test_id, Callback<ResultList> callback) {
        RetrofitClient.getClient().resultList(user_id, test_id).enqueue(callback);

    }

    public static void reviewQuestionResult(RequestBody user_id, RequestBody test_id, Callback<ReviewResult> callback) {
        RetrofitClient.getClient().reviewQuestionResult(user_id, test_id).enqueue(callback);
    }

    public static void facultyData(Callback<FacultyDetail> callback) {
        RetrofitClient.getClient().facultyData().enqueue(callback);


    }

    public static void knowMoreData(Callback<FacultyDetail> callback) {
        RetrofitClient.getClient().knowMoreData().enqueue(callback);


    }


    public static void qbankDetail(RequestBody user_id, Callback<QbankResponse> callback) {
        RetrofitClient.getClient().qbankDetail(user_id).enqueue(callback);
    }

    public static void qbankStart(RequestBody qmodule_id, RequestBody user_id, RequestBody is_paused, Callback<QbankstartResponse> callback) {
        RetrofitClient.getClient().qbankStart(qmodule_id, user_id, is_paused).enqueue(callback);
    }

    public static void qbanksubdata(RequestBody qcat_id, RequestBody user_id, Callback<QbankSubResponse> callback) {
        RetrofitClient.getClient().qbanksubdata(qcat_id, user_id).enqueue(callback);
    }

    public static void qbanksubTestData(RequestBody qmodule_id, Callback<QbankTestResponse> callback) {
        RetrofitClient.getClient().qbanksubTestData(qmodule_id).enqueue(callback);
    }

    public static void qbankFeedback(String user_id, String qmodule_id, String rating, String feedback, Callback<QbankfeedbackResponse> callback) {
        RetrofitClient.getClient().qbankFeedback(user_id, qmodule_id, rating, feedback).enqueue(callback);
    }

    public static void sendOtp(RequestBody phone, Callback<CommonResponse> callback) {
        RetrofitClient.getClient().sendOtp(phone).enqueue(callback);
    }

    public static void verifyOtp(RequestBody userid, RequestBody code, Callback<VerifyOtpResponse> callback) {
        RetrofitClient.getClient().verifyOTP(userid, code).enqueue(callback);

    }
}