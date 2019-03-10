package edu.com.medicalapp.Retrofit;

import edu.com.medicalapp.Models.QbankSubCat.QbankSubResponse;
import edu.com.medicalapp.Models.QbankSubTest.QbankTestResponse;
import edu.com.medicalapp.Models.QustionDetails;
import edu.com.medicalapp.Models.ResultData.ResultList;
import edu.com.medicalapp.Models.ReviewResult.ReviewResult;
import edu.com.medicalapp.Models.VerifyOtpResponse;
import edu.com.medicalapp.Models.facebook.FacebookResponse;
import edu.com.medicalapp.Models.faculties.FacultyDetail;
import edu.com.medicalapp.Models.qbank.QbankResponse;
import edu.com.medicalapp.Models.test.TestQuestionData;
import edu.com.medicalapp.Models.video.VideoList;
import edu.com.medicalapp.Models.login.loginResponse;
import edu.com.medicalapp.Models.maincat.CategoryDetailData;
import edu.com.medicalapp.Models.registration.CommonResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


   /* @POST("api/api.php?req=login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
*/


    @Multipart
    @POST("api/api.php?req=login")
    Call<loginResponse> loginUser(@Part("email_id") RequestBody email, @Part("password") RequestBody password);


    @Multipart
    @POST("api/api.php?req=registration")
    Call<CommonResponse> registerUser(@Part("name") RequestBody name,
                                      @Part("username") RequestBody username,
                                      @Part("email_id") RequestBody email,
                                      @Part("mobile") RequestBody phone,
                                      @Part("state") RequestBody state,
                                      @Part("password") RequestBody password);

    @Multipart
    @POST("api/api.php?req=facebook")
    Call<FacebookResponse> facebookRegister(@Part("name") RequestBody name,
                                            @Part("email_id") RequestBody emailId,
                                             @Part("fb_id") RequestBody facebookbId);


    @GET("api/api.php?req=category")
    Call<CategoryDetailData> getCourse();

    @GET("api/api.php?req=allfile")
    Call<VideoList> getVideos(@Query("sub_child_cat") String sub_child_cat, @Query("file_type") String fileType);

    @GET("api/api.php?req=test")
    Call<TestQuestionData> getTest();


    @GET("api/api.php?req=question")
    Call<QustionDetails> getQuestion(@Query("test_id") String test_id);

    @POST("api/api.php?req=final_test")
    Call<ResponseBody> submitTest(@Query("user_id") String user_id,
                                  @Query("test_id") String test_id,
                                  @Query("tquestion") String tquestion,
                                  @Query("ttquestion") String ttquestion,
                                  @Query("canswer") String canswer,
                                  @Query("ccanswer") String ccanswer,
                                  @Query("wanswer") String wanswer,
                                  @Query("wwanswer") String wwanswer,
                                  @Query("sanswer") String sanswer,
                                  @Query("ssanswer") String ssanswer);

    @Multipart
    @POST("api/api.php?req=result")
    Call<ResultList> resultList(@Part("user_id") RequestBody user_id,
                            @Part("test_id") RequestBody test_id);


    @Multipart
    @POST("api/api.php?req=showresult")
    Call<ReviewResult> reviewQuestionResult(@Part("user_id") RequestBody user_id,
                                            @Part("test_id") RequestBody test_id);

    @Multipart
    @POST("api/api.php?req=mobilelogin")
    Call<CommonResponse> sendOtp(@Part("mobile") RequestBody phone);

    @POST("api/api.php?req=faculty")
    Call<FacultyDetail> facultyData();


    @POST("api/api.php?req=qbank_cate")
    Call<QbankResponse> qbankDetail();


    /*@Multipart
    @POST("api/api.php?req=qbank_subcate")
    Call<QbankSubResponse> qbanksubData(@Part("qcat_id") RequestBody qcat_id);

*/

    @Multipart
     @POST("api/api.php?req=qbank_subcate")
    Call<QbankSubResponse> qbanksubdata(@Part("qcat_id") RequestBody qcat_id);




    @Multipart
    @POST("api/api.php?req=qbank_mcq")
    Call<QbankTestResponse> qbanksubTestData(@Part("qmodule_id") RequestBody qmodule_id);

    @Multipart
    @POST("api/api.php?req=mobileverify")
    Call<VerifyOtpResponse> verifyOTP(
            @Part("user_id") RequestBody user_id,
            @Part("code") RequestBody code
            );
}