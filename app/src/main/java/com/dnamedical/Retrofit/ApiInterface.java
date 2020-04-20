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
import com.dnamedical.Models.RankResult;
import com.dnamedical.Models.ResultData.ResultList;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.TestReviewList.TestReviewResponse;
import com.dnamedical.Models.UserUpdateResponse;
import com.dnamedical.Models.VerifyOtpResponse;
import com.dnamedical.Models.acadamic.Academic;
import com.dnamedical.Models.acadamic.CourseResponse;
import com.dnamedical.Models.addressDetail.AddressDetailResponse;
import com.dnamedical.Models.allinstitutes.AllInstituteResponseModel;
import com.dnamedical.Models.changePhoneNumber.ChangePhoneNumberOtpResponse;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.Models.facebook.FacebookResponse;
import com.dnamedical.Models.facebookloginnew.FacebookLoginResponse;
import com.dnamedical.Models.faculties.FacultyDetail;
import com.dnamedical.Models.feedback.QbankfeedbackResponse;
import com.dnamedical.Models.forgetpassword.ForgetPasswordResponse;
import com.dnamedical.Models.franchies.FranchiesResponse;
import com.dnamedical.Models.getAddressDetail.GetDataAddressResponse;
import com.dnamedical.Models.get_Mobile_number.MobileResponse;
import com.dnamedical.Models.log_out.LogOutResponse;
import com.dnamedical.Models.login.loginResponse;
import com.dnamedical.Models.mailsent.ForgetMailSentResponse;
import com.dnamedical.Models.maincat.CategoryDetailData;
import com.dnamedical.Models.modulesforcat.CatModuleResponse;
import com.dnamedical.Models.newqbankmodule.ChaptersModuleResponse;
import com.dnamedical.Models.newqbankmodule.MCQQuestionList;
import com.dnamedical.Models.newqbankmodule.ModuleListResponse;
import com.dnamedical.Models.newqbankmodule.ModuleResponse;
import com.dnamedical.Models.newqbankmodule.QBankResultResponse;
import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.paymentmodel.CreateOrderResponse;
import com.dnamedical.Models.qbankstart.QbankstartResponse;
import com.dnamedical.Models.registration.CommonResponse;
import com.dnamedical.Models.saveOrder.SaveOrderResponse;
import com.dnamedical.Models.subs.PlanDetailResponse;
import com.dnamedical.Models.subs.PlanResponse;
import com.dnamedical.Models.subs.ssugplans.PlanResponseForSSAndUG;
import com.dnamedical.Models.test.RankResultRemarks;
import com.dnamedical.Models.test.TestQuestionData;
import com.dnamedical.Models.test.testp.QustionDetails;
import com.dnamedical.Models.test.testp.TestDataResponse;
import com.dnamedical.Models.test.testresult.TestResult;
import com.dnamedical.Models.testReviewlistnew.TestReviewListResponse;
import com.dnamedical.Models.updateAddress.UpdateAddressResponse;
import com.dnamedical.Models.updateplaystore.PlaystoreUpdateResponse;
import com.dnamedical.Models.verifyid.VerifyIdResponse;
import com.dnamedical.Models.video.VideoList;
import com.dnamedical.institute.InstituteDetails;

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
    @POST("api/api.php?req=customer_login")
    Call<loginResponse> loginUser(@Part("email_id") RequestBody email,
                                  @Part("password") RequestBody password,
                                  @Part("device_id") RequestBody deviceId);


    @Multipart
    @POST("api/api.php?req=institute_detail")
    Call<InstituteDetails> getInstituteDetail(@Part("user_id") RequestBody user_id,
                                              @Part("institute_id") RequestBody institute_id);


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
                                      @Part("country") RequestBody countryBody,
                                      @Part("platform") RequestBody plateform,
                                      @Part("academic_year_id") RequestBody acaademicYear_id,
                                      @Part("course_type") RequestBody courseSlectedBody,
                                      @Part("board_name") RequestBody boardname
    );


    @Multipart
    @POST("api/api.php?req=update_user")
    Call<UserUpdateResponse> updateUser(@Part("name") RequestBody name,
                                        @Part("id") RequestBody user_id,
                                        @Part("password") RequestBody password,
                                        @Part("username") RequestBody username,
                                        @Part("mobile_no") RequestBody phone,
                                        @Part("state") RequestBody state,
                                        @Part("college") RequestBody college,
                                        @Part("address") RequestBody addressBody,
                                        @Part("city") RequestBody cityBody,
                                        @Part("country") RequestBody countryBody,
                                        @Part("academic_year_id") RequestBody acaademicYear_id
    );


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
                                               @Part("totalAmount") RequestBody totalAmount,
                                               @Part("order_id") RequestBody order_id
                                               );

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
                                           @Part("status") RequestBody status
//                                           @Part("cat_id") RequestBody cat_id,
//                                           @Part("sub_cat_id") RequestBody sub_cat_id
    );

    @Multipart
    @POST("api/api.php?req=order_subscription")
    Call<ResponseBody> addOrderForSubsDetail(@Part("user_id") RequestBody userid,
                                             @Part("order_id") RequestBody orderid,
                                             @Part("plan_id") RequestBody planid,
                                             @Part("subscription_id") RequestBody subsid,
                                             @Part("pack_key") RequestBody pack,
                                             @Part("months") RequestBody month,
                                             @Part("status") RequestBody status,
                                             @Part("price") RequestBody price);


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
    @POST("api/api.php?req=franchise_query")
    Call<FranchiesResponse> franchiRegister(@Part("name") RequestBody username,
                                            @Part("email") RequestBody usermail,
                                            @Part("mobile") RequestBody phoneno,
                                            @Part("whatsapp_no") RequestBody whatsppNumber,
                                            @Part("city") RequestBody pCity,
                                            @Part("state") RequestBody pState,
                                            @Part("address") RequestBody pAddress,
                                            @Part("landmark") RequestBody pLandmark,
                                            @Part("pincode") RequestBody pPincode,
                                            @Part("college") RequestBody collegaeFrenchise,
                                            @Part("city_of_college") RequestBody cMedicalCollegae,
                                            @Part("state_of_college") RequestBody sMedicalCollege,
                                            @Part("pincode_of_college") RequestBody pinMedicalCollege,
                                            @Part("comment") RequestBody comment,
                                            @Part("invested_ammount") RequestBody amount,
                                            @Part("is_recievecall") RequestBody canCall

    );




    @Multipart
    @POST("api/api.php?req=get_address")
    Call<GetDataAddressResponse> getAddressData(@Part("user_id") RequestBody user_id);


    @GET("api/api.php?req=add_discount")
    Call<ResponseBody> getAdditionalDiscount();


    @GET("api/api.php?req=course")
    Call<CourseResponse> getCourseList();

    @Multipart
    @POST("api/api.php?req=update_user")
    Call<EditProfileResponse> editProfile(@Part("id") RequestBody id,
                                          @Part("username") RequestBody username,
                                          @Part("mobile_no") RequestBody mobile_no,
                                          @Part("state") RequestBody state,
                                          @Part("college") RequestBody college);

    /////////////////////////////////////////
    @Multipart
    @POST("api/api.php?req=facebook")
    Call<FacebookResponse> facebookRegister(@Part("name") RequestBody name,
                                            @Part("email_id") RequestBody emailId,
                                            @Part("fb_id") RequestBody facebookbId);


    @GET("api/api.php?req=category")
    Call<CategoryDetailData> getCourse();

    @Multipart
    @POST("api/api.php?req=allfile")
    Call<VideoList> getVideos(
            @Part("sub_child_cat") RequestBody sub_child_cat,
            @Part("file_type") RequestBody fileType,
            @Part("user_id") RequestBody user_id
    );


    @Multipart
    @POST("api/api.php?req=price_videoscopy")
    Call<PaidVideoResponse> getPaidVedio(@Part("sub_child_cat") RequestBody sub_child_cat,
                                         @Part("user_id") RequestBody user_id,
                                         @Part("file_type") RequestBody file_type);


    @GET("api/api.php?req=getreleasedetail")
    Call<PlaystoreUpdateResponse> playstoreUpdate();



    @GET("api/api.php?req=get_customsubs")
    Call<PlanResponseForSSAndUG> getnewPlansForSSUG();


    @GET("api/api.php?req=get_subscription")
    Call<PlanResponse> getSubscriptionPlans();


    @Multipart
    @POST("api/api.php?req=get_plans")
    Call<PlanDetailResponse> getPlanById(@Part("subscription_id") RequestBody subscription_id);


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
    @POST("api/api.php?req=add_progress")
    Call<ResponseBody> updateVideoProgress(@Part("user_id") RequestBody user_id,
                                           @Part("video_id") RequestBody video_id,
                                           @Part("time") RequestBody time
    );


    @Multipart
    @POST("api/api.php?req=forgot_password")
    Call<ForgetMailSentResponse> sentMail(@Part("email_id") RequestBody email_id);


    @GET("v1/index.php/api/test/testquestions")
    Call<QustionDetails> getQuestion(@Query("user_id") String user_id,
                                     @Query("test_id") String test_id);

    @Multipart
    @POST("v1/index.php/api/test/testresult")
    Call<TestResult> submitTest(@Part("user_id") RequestBody userId,
                                @Part("test_id") RequestBody testID,
                                @Part("is_submit") RequestBody isSubmit);


    @Multipart
    @POST("api/api.php?req=add_testtime")
    Call<ResponseBody> startTest(@Part("user_id") RequestBody userId,
                                 @Part("test_id") RequestBody testID,
                                 @Part("start_time") RequestBody startTime);


    @Multipart
    @POST("api/api.php?req=add_endtime")
    Call<ResponseBody> endTest(@Part("user_id") RequestBody userId,
                               @Part("test_id") RequestBody testID,
                               @Part("end_time") RequestBody endTime);


    @GET("api/api.php?req=get_remark")
    Call<RankResultRemarks> getResultRemark(@Query("test_id") String testID);


    @Multipart
    @POST("api/api.php?req=get_testrank")
    Call<RankResult> getStudentRank(@Part("user_id") RequestBody userId,
                                    @Part("test_id") RequestBody testID
    );

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


    @POST("api/api.php?req=state")
    Call<StateListResponse> stateData();


    @POST("api/api.php?req=getall_academics")
    Call<Academic> getAllAcademicYears();

    @Multipart
    @POST("api/api.php?req=qbank_subjects")
    Call<ModuleListResponse> qbankDetail(@Part("user_id") RequestBody user_id,
                                         @Part("cat_id") RequestBody catid);

    @Multipart
    @POST("api/api.php?req=getall_modulescopy")
    Call<ChaptersModuleResponse> getAllChapterByModuleId(@Part("user_id") RequestBody user_id, @Part("subject_id") RequestBody module_id);


    @Multipart
    @POST("api/api.php?req=getall_mcq")
    Call<MCQQuestionList> getAllMCQQuestions(@Part("user_id") RequestBody user_id, @Part("module_id") RequestBody module_id);


    @Multipart
    @POST("api/api.php?req=submit_module")
    Call<ResponseBody> completeMCQ(@Part("user_id") RequestBody user_id,
                                   @Part("module_id") RequestBody module_id,
                                   @Part("complete_status") RequestBody complete_status,
                                   @Part("subject_id") RequestBody subject_id,
                                   @Part("chapter_id") RequestBody chapter_id
    );


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
                                        @Part("user_id") RequestBody user_id,
                                        @Part("is_paused") RequestBody is_paused);

    @Multipart
    @POST("api/api.php?req=add_feedback")
    Call<QbankfeedbackResponse> qbankFeedback(@Part("user_id") RequestBody user_id,
                                              @Part("module_id") RequestBody qmodule_id,
                                              @Part("rating") RequestBody rating,
                                              @Part("feedback") RequestBody feedback,
                                              @Part("remark") RequestBody remark);


    @Multipart
    @POST("api/api.php?req=submit_mcqanswer")
    Call<ResponseBody> submitAnswer(@Part("user_id") RequestBody user_id,
                                    @Part("mcq_id") RequestBody quest_id,
                                    @Part("module_id") RequestBody module_id,
                                    @Part("given_answer") RequestBody user_answer);

    @Multipart
    @POST("api/api.php?req=get_modulebyid")
    Call<ModuleResponse> updateQBankStatus(@Part("user_id") RequestBody user_id,
                                           @Part("module_id") RequestBody module_id);


    @Multipart
    @POST("api/api.php?req=get_score")
    Call<QBankResultResponse> getMCQResult(@Part("user_id") RequestBody user_id,
                                           @Part("module_id") RequestBody module_id);

    @Multipart
    @POST("v1/index.php/api/test/submitanswer")
    Call<ResponseBody> submitTestQuestionAnswer(@Part("user_id") RequestBody userId,
                                                @Part("test_id") RequestBody testID,
                                                @Part("question_id") RequestBody qID,
                                                @Part("answer") RequestBody answerID,
                                                @Part("is_guess") RequestBody guesStatus,
                                                @Part("is_edit") RequestBody edit);

    @Multipart
    @POST("v1/index.php/api/test/submitselectedoption")
    Call<ResponseBody> submitTestAnswer(@Part("user_id") RequestBody userId,
                                        @Part("test_id") RequestBody testID,
                                        @Part("question_id") RequestBody qID,
                                        @Part("answer") RequestBody answerID);


    @Multipart
    @POST("api/api.php?req=qbank_mcq")
    Call<QbankTestResponse> qbanksubTestData(@Part("qmodule_id") RequestBody qmodule_id);

    ////////////////////////////////////
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
    @POST("api/api.php?req=fb_login")
    Call<FacebookLoginResponse> loginWithFacebook(@Part("fb_id") RequestBody fb_id,
                                                  @Part("device_id") RequestBody deviceRequestBody);


    @Multipart
    @POST("/api/api.php?req=email_login")
    Call<LoginDetailForDemo> getUserByEmail(@Part("email_id") RequestBody email_id);


    @Multipart
    @POST("api/api.php?req=update_mobile")
    Call<EnterMobileresponce> enterMobileNumber(@Part("id") RequestBody id,
                                                @Part("mobile_no") RequestBody mobile_no);


    @Multipart
    @POST("api/api.php?req=is_real1")
    Call<ResponseBody> updateLogin(@Part("id") RequestBody id,
                                   @Part("isreal") RequestBody isreal);


    @Multipart
    @POST("api/api.php?req=checkuserdeleted")
    Call<ResponseBody> checkuserExist(@Part("email_id") RequestBody id);


    @Multipart
    @POST("api/api.php?req=deleteAddress")
    Call<ResponseBody> deleteAddress(@Part("address_id") RequestBody id);

    @GET("v1/index.php/api/test/list")
    Call<TestDataResponse> getAllTestData(@Query("user_id") String id, @Query("institute_id") String institute_id,
                                          @Query("cat_id") String catId);

    @Multipart
    @POST("v1/index.php/api/test/bookmark")
    Call<ResponseBody> bookMarkQuestion(@Part("user_id") RequestBody user_id,
                                        @Part("test_id") RequestBody test_id,
                                        @Part("question_id") RequestBody question_id,
                                        @Part("remove_bookmark") RequestBody remove_bookmark,
                                        @Part("type") RequestBody type);

    @Multipart
    @POST("v1/index.php/api/test/timelogs")
    Call<ResponseBody> submit_timeLog(@Part("user_id") RequestBody user_id,
                                      @Part("timespend") RequestBody timespend,
                                      @Part("event") RequestBody event,
                                      @Part("subevent") RequestBody subevent,
                                      @Part("product_id") RequestBody test_id);

    // New Api's Integrate
    @GET("v1/index.php/api/test/reviewquestionlist")
    Call<TestReviewListResponse> getTestReviewListData(
            @Query("test_id") String test_id,
            @Query("user_id") String user_id,
            @Query("filter_level") String filter_level,
            @Query("filter_answer") String filter_answer,
            @Query("filter_category") String filter_category,
            @Query("filter_bookmark") String filter_bookmark);


    // New Api's Integrate
    @GET("v1/index.php/api/test/reviewqbankquestionlist")
    Call<TestReviewListResponse> getQBankReviewListData(
            @Query("module_id") String test_id,
            @Query("user_id") String user_id,
            @Query("filter_level") String filter_level,
            @Query("filter_answer") String filter_answer,
            @Query("filter_category") String filter_category,
            @Query("filter_bookmark") String filter_bookmark);


    @Multipart
    @POST("api/api.php?req=subscription_invoice")
    Call<ResponseBody> invoiceOrderDetailForSubscription(@Part("user_id") RequestBody user_id,
                                                         @Part("order_id") RequestBody orderId_forInvoice,
                                                         @Part("pramotion") RequestBody pramotoin,
                                                         @Part("additional") RequestBody addDiscount,
                                                         @Part("totalAmountBeforTax") RequestBody totalAmountBeforeTax,
                                                         @Part("tax") RequestBody tax,
                                                         @Part("shippingCharges") RequestBody shipping,
                                                         @Part("totalAmount") RequestBody shippingCharges,
                                                         @Part("payment_method") RequestBody paymethod,
                                                         @Part("discount") RequestBody discount,
                                                         @Part("grandTotal") RequestBody grandTotal);


//    // New Api's Integrate
//    @GET("v1/index.php/api/test/reviewquestionlist")
//    Call<TestReviewListResponse> getTestReviewListFilteredData(
//                                                       @Query("test_id") String test_id,
//                                                       @Query("user_id") String user_id);


    // Change phone No
    @Multipart
    @POST("api/api.php?req=send_otp")
    Call<ResponseBody> changePhoneNumber(@Part("user_id") RequestBody name,
                                         @Part("mobile") RequestBody phoneNo);


    // Change phone No
    @Multipart
    @POST("api/api.php?req=get_catsubmodules")
    Call<CatModuleResponse> getAllModulesForCategory(@Part("cat_id") RequestBody catId);

    // change phone no verified otp

    @Multipart
    @POST("api/api.php?req=update_mobile")
    Call<ChangePhoneNumberOtpResponse> phoneOtpVerified(@Part("user_id") RequestBody user_id,
                                                        @Part("mobile") RequestBody mobile,
                                                        @Part("otp") RequestBody otp);


    @Multipart
    @POST("api/api.php?req=get_institute")
    Call<AllInstituteResponseModel> getAllInstitute(@Part("user_id") RequestBody user_id);

    @Multipart
    @POST("api/api.php?req=logout")
    Call<LogOutResponse> LOG_OUT_RESPONSE_CALL (@Part("userId") RequestBody userId);


}

