package com.dnamedical.Retrofit;

import com.dnamedical.Models.Directors;
import com.dnamedical.Models.EditProfileResponse.EditProfileResponse;
import com.dnamedical.Models.Enter_Mobile.EmailByFBResponse;
import com.dnamedical.Models.Enter_Mobile.EnterMobileresponce;
import com.dnamedical.Models.LoginDetailForDemo;
import com.dnamedical.Models.PromoVideo;
import com.dnamedical.Models.QbankSubCat.QbankSubResponse;
import com.dnamedical.Models.QbankSubTest.QbankTestResponse;
import com.dnamedical.Models.QbannkReviewList.ReviewListResponse;
import com.dnamedical.Models.QustionDetails;
import com.dnamedical.Models.ResultData.ResultList;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.TestReviewList.TestReviewResponse;
import com.dnamedical.Models.UserUpdateResponse;
import com.dnamedical.Models.VerifyOtpResponse;
import com.dnamedical.Models.addressDetail.AddressDetailResponse;
import com.dnamedical.Models.answer.SubmitAnswer;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.Models.facebook.FacebookResponse;
import com.dnamedical.Models.facebookloginnew.FacebookLoginResponse;
import com.dnamedical.Models.faculties.FacultyDetail;
import com.dnamedical.Models.feedback.QbankfeedbackResponse;
import com.dnamedical.Models.forgetpassword.ForgetPasswordResponse;
import com.dnamedical.Models.franchies.FranchiesResponse;
import com.dnamedical.Models.getAddressDetail.GetDataAddressResponse;
import com.dnamedical.Models.get_Mobile_number.MobileResponse;
import com.dnamedical.Models.mailsent.ForgetMailSentResponse;
import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.paymentmodel.CreateOrderResponse;
import com.dnamedical.Models.qbank.QbankResponse;
import com.dnamedical.Models.qbankstart.QbankstartResponse;
import com.dnamedical.Models.saveOrder.SaveOrderResponse;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.updateAddress.UpdateAddressResponse;
import com.dnamedical.Models.updateplaystore.PlaystoreUpdateResponse;
import com.dnamedical.Models.verifyid.VerifyIdResponse;
import com.dnamedical.Models.video.VideoList;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.registration.CommonResponse;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @Multipart
    @POST("api/api.php?req=login")
    Call<loginResponse> loginUser(@Part("email_id") RequestBody email, @Part("password") RequestBody password);


    @Multipart
    @POST("api/api.php?req=registrationnew")
    Call<CommonResponse> registerUser(@Part("fb_id") RequestBody fb_id,
                                      @Part("name") RequestBody name,
                                      @Part("username") RequestBody username,
                                      @Part("email_id") RequestBody email,
                                      @Part("mobile") RequestBody phone,
                                      @Part("state") RequestBody state,
                                      @Part("password") RequestBody password,
                                      @Part("college") RequestBody college,
                                      @Part("address") RequestBody addressBody,
                                      @Part("city") RequestBody cityBody,
                                      @Part("country") RequestBody countryBody);


    @Multipart
    @POST("api/api.php?req=update_user")
    Call<UserUpdateResponse> updateUser(@Part("name") RequestBody name,
                                        @Part("id") RequestBody user_id,
                                        @Part("username") RequestBody username,
                                        @Part("mobile_no") RequestBody phone,
                                        @Part("state") RequestBody state,
                                        @Part("college") RequestBody college,
                                        @Part("address") RequestBody addressBody,
                                        @Part("city") RequestBody cityBody,
                                        @Part("country") RequestBody countryBody);




    @Multipart
    @POST("api/api.php?req=add_address")
    Call<AddressDetailResponse> addressDetail(@Part("user_id") RequestBody user_id,
                                              @Part("name") RequestBody name,
                                              @Part("mobile") RequestBody mobile,
                                              @Part("email") RequestBody email,
                                              @Part("address_line1") RequestBody address_line1,
                                              @Part("address_line2") RequestBody address_line2,
                                              @Part("state") RequestBody state,
                                              @Part("city") RequestBody city,
                                              @Part("pin_code") RequestBody pin_code);

    @Multipart
    @POST("api/api.php?req=update_address")
    Call<UpdateAddressResponse> updateDetail(@Part("a_id") RequestBody a_id,
                                             @Part("user_id") RequestBody user_id,
                                             @Part("name") RequestBody name,
                                             @Part("mobile") RequestBody mobile,
                                             @Part("email") RequestBody email,
                                             @Part("address_line1") RequestBody address_line1,
                                             @Part("address_line2") RequestBody address_line2,
                                             @Part("state") RequestBody state,
                                             @Part("city") RequestBody city,
                                             @Part("pin_code") RequestBody pin_code);






    @Multipart
    @POST("api/api.php?req=invoice_mail")
    Call<SaveOrderResponse> invoiceOrderDetail(@Part("user_id") RequestBody user_id,
                                            @Part("pramotoin") RequestBody pramotoin,
                                            @Part("addDiscount") RequestBody addDiscount,
                                            @Part("totalAmountBeforeTax") RequestBody totalAmountBeforeTax,
                                            @Part("tax") RequestBody tax,
                                            @Part("shippingCharges") RequestBody shippingCharges,
                                            @Part("grandTotal") RequestBody grandTotal,
                                             @Part("totalAmount") RequestBody totalAmount);

    @Multipart
    @POST("api/api.php?req=save_order")
    Call<SaveOrderResponse> saveOrderDetail(@Part("order_id") RequestBody order_id,
                                            @Part("sub_child_cat_id") RequestBody sub_child_cat_id,
                                            @Part("user_id") RequestBody user_id,
                                            @Part("product_id") RequestBody product_id,
                                            @Part("video_id") RequestBody video_id,
                                            @Part("test_id") RequestBody test_id,
                                            @Part("status") RequestBody status);

    @Multipart
    @POST("api/api.php?req=add_order")
    Call<SaveOrderResponse> addOrderDetail(@Part("order_id") RequestBody order_id,
                                            @Part("sub_child_cat_id") RequestBody sub_child_cat_id,
                                            @Part("user_id") RequestBody user_id,
                                            @Part("product_id") RequestBody product_id,
                                            @Part("video_id") RequestBody video_id,
                                            @Part("test_id") RequestBody test_id,
                                            @Part("status") RequestBody status);

    @Multipart
    @POST("/v1/index.php/api/ordersdetails/saveorder")
    Call<CreateOrderResponse> createOrderDetail(@Part("user_id") RequestBody user_id,
                                                @Part("amount") RequestBody amount,
                                                @Part("currency") RequestBody currency,
                                                @Part("product_id") RequestBody product_id,
                                                @Part("product_type") RequestBody product_type
                                            );

    @Multipart
    @POST("api/api.php?req=token_verify")
    Call<ForgetPasswordResponse> forgetPassword(@Part("email_id") RequestBody email_id,
                                                @Part("token") RequestBody token,
                                                @Part("new_password") RequestBody new_password,
                                                @Part("retype_password") RequestBody retype_password);


    @Multipart
    @POST("api/api.php?req=userquery")
    Call<FranchiesResponse> franchiRegister(@Part("username") RequestBody username,
                                            @Part("phoneno") RequestBody phoneno,
                                            @Part("usermail") RequestBody usermail,
                                            @Part("comment") RequestBody comment);

    @Multipart
    @POST("api/api.php?req=get_address")
    Call<GetDataAddressResponse> getAddressData(@Part("user_id") RequestBody user_id);



    @GET("api/api.php?req=add_discount")
    Call<ResponseBody> getAdditionalDiscount();

    @Multipart
    @POST("api/api.php?req=update_user")
    Call<EditProfileResponse> editProfile(@Part("id") RequestBody id,
                                          @Part("username") RequestBody username,
                                          @Part("mobile_no") RequestBody mobile_no,
                                          @Part("state") RequestBody state,
                                          @Part("college") RequestBody college);


    @Multipart
    @POST("api/api.php?req=facebook")
    Call<FacebookResponse> facebookRegister(@Part("name") RequestBody name,
                                            @Part("email_id") RequestBody emailId,
                                            @Part("fb_id") RequestBody facebookbId);


    @GET("api/api.php?req=category")
    Call<CategoryDetailData> getCourse();

    @GET("api/api.php?req=allfile")
    Call<VideoList> getVideos(@Query("sub_child_cat") String sub_child_cat, @Query("file_type") String fileType);


    @Multipart
    @POST("api/api.php?req=price_videos")
    Call<PaidVideoResponse> getPaidVedio(@Part("sub_child_cat") RequestBody sub_child_cat,
                                         @Part("user_id") RequestBody user_id,
                                         @Part("file_type") RequestBody file_type);


    @GET("http://13.234.161.7/api/api.php?req=getreleasedetail")
    Call<PlaystoreUpdateResponse> playstoreUpdate();


    @Multipart
    @POST("api/api.php?req=test")
    Call<TestQuestionData> getTest(@Part("user_id") RequestBody user_id);


    @Multipart
    @POST("api/api.php?req=demo_time")
    Call<ResponseBody> updateVideoPlayTime(@Part("user_id") RequestBody user_id,
                                               @Part("video_id") RequestBody video_id,
                                               @Part("time") RequestBody time
                                               );


    @Multipart
    @POST("api/api.php?req=forgot_password")
    Call<ForgetMailSentResponse> sentMail(@Part("email_id") RequestBody email_id);


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
                                  @Query("ssanswer") String ssanswer,
                                  @Query("test_finish_duration") String test_finish_duration);

    @Multipart
    @POST("api/api.php?req=result")
    Call<ResultList> resultList(@Part("user_id") RequestBody user_id,
                                @Part("test_id") RequestBody test_id);


    @Multipart
    @POST("api/api.php?req=showresult")
    Call<TestReviewResponse> reviewQuestionResult(@Part("user_id") RequestBody user_id,
                                                  @Part("test_id") RequestBody test_id);


    @Multipart
    @POST("api/api.php?req=mobilelogin")
    Call<CommonResponse> sendOtp(@Part("mobile") RequestBody phone);

    @POST("api/api.php?req=faculty")
    Call<FacultyDetail> facultyData();


    @POST("api/api.php?req=collegelist")
    Call<CollegeListResponse> collegeData();


    @POST("http://13.234.161.7/api/api.php?req=state")
    Call<StateListResponse> stateData();

    @Multipart
    @POST("api/api.php?req=qbank_cate")
    Call<QbankResponse> qbankDetail(@Part("user_id") RequestBody user_id);

    @Multipart
    @POST("api/api.php?req=qbankmodulereview")
    Call<ReviewListResponse> qbankReview(@Part("user_id") RequestBody user_id, @Part("qmodule_id") RequestBody qmodule_id);


    /*@Multipart
    @POST("api/api.php?req=qbank_subcate")
    Call<QbankstartResponse> qbanksubData(@Part("qcat_id") RequestBody qcat_id);

*/
    @POST("api/api.php?req=faculty_head")
    Call<Directors> knowMoreData();

    @Multipart
    @POST("api/api.php?req=qbank_subcate")
    Call<QbankSubResponse> qbanksubdata(@Part("qcat_id") RequestBody qcat_id, @Part("user_id") RequestBody user_id);


    @Multipart
    @POST("api/api.php?req=qbank_solve")
    Call<QbankstartResponse> qbankStart(@Part("qmodule_id") RequestBody qmodule_id,
                                        @Part("user_id") RequestBody user_id
            , @Part("is_paused") RequestBody is_paused);

    @POST("api/api.php?req=qbank_mcq_model_feedback")
    Call<QbankfeedbackResponse> qbankFeedback(@Query("user_id") String user_id,
                                              @Query("qmodule_id") String qmodule_id,
                                              @Query("rating") String rating,
                                              @Query("feedback") String feedback);


    @GET("api/api.php?req=qbank_mcq_atteped_answer")
    Call<SubmitAnswer> submitAnswer(@Query("quest_id") String quest_id,
                                    @Query("user_id") String user_id,
                                    @Query("is_completed") String is_completed,
                                    @Query("user_answer") String user_answer);


    @Multipart
    @POST("api/api.php?req=qbank_mcq")
    Call<QbankTestResponse> qbanksubTestData(@Part("qmodule_id") RequestBody qmodule_id);

    @Multipart
    @POST("api/api.php?req=mobileverify")
    Call<VerifyOtpResponse> verifyOTP(
            @Part("user_id") RequestBody user_id,
            @Part("code") RequestBody code
    );

    @GET("api/api.php?req=promotionvideo")
    Call<PromoVideo> getVideo();


    @Multipart
    @POST("api/api.php?req=id_verify")
    Call<VerifyIdResponse> verifyDetail(@Part("user_id") RequestBody user_id,
                                        @Part("v_title") RequestBody v_title,
                                        @Part MultipartBody.Part body);

    @Multipart
    @POST("api/api.php?req=get_mobile")
    Call<MobileResponse> MOBILE_RESPONSE_CALL(@Part("email_id") RequestBody email_id);


    @Multipart
    @POST("api/api.php?req=get_email")
    Call<EmailByFBResponse> getEmailByFBID(@Part("fb_id") RequestBody fb_id);

    @Multipart
    @POST("api/api.php?req=facebook_login")
    Call<FacebookLoginResponse> loginWithFacebook(@Part("fb_id") RequestBody fb_id);



    @Multipart
    @POST("/api/api.php?req=email_login")
    Call<LoginDetailForDemo> getUserByEmail(@Part("email_id") RequestBody email_id);


    @Multipart
    @POST("api/api.php?req=update_mobile")
    Call<EnterMobileresponce> enterMobileNumber(@Part("id") RequestBody id,
                                                @Part("mobile_no") RequestBody mobile_no);


    @Multipart
    @POST("api/api.php?req=is_real")
    Call<ResponseBody> updateLogin(@Part("id") RequestBody id,
                                                @Part("isreal") RequestBody isreal);

}