package edu.com.medicalapp.Retrofit;

import edu.com.medicalapp.Models.QustionDetails;
import edu.com.medicalapp.Models.facebook.FacebookResponse;
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
    Call<ResponseBody> submitTest(@Query("user_id") String user_id, @Query("test_id") String test_id,
                                  @Query("tquestion") String tquestion, @Query("canswer") String canswer,
                                  @Query("wanswer") String wanswer, @Query("sanswer") String sanswer);
}