package com.dnamedical.Retrofit;

import com.dnamedical.Models.Directors;
import com.dnamedical.Models.EditProfileResponse.EditProfileResponse;
import com.dnamedical.Models.Enter_Mobile.EmailByFBResponse;
import com.dnamedical.Models.Enter_Mobile.EnterMobileresponce;
import com.dnamedical.Models.LoginDetailForDemo;
import com.dnamedical.Models.PromoVideo;
import com.dnamedical.Models.QbankSubCat.QbankSubResponse;
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
import retrofit2.Callback;

public class RestClient {
    private static final String TAG = "RestClient";

/*

    public static void loginUser(LoginRequest loginRequest, Callback<LoginResponse> callback) {
        RetrofitClient.getClient().loginUser(loginRequest).enqueue(callback);
    }
*/


    public static void loginUser(RequestBody email, RequestBody password, RequestBody deviceId, Callback<loginResponse> callback) {
        RetrofitClient.getClient().loginUser(email, password, deviceId).enqueue(callback);
    }

    public static void getInstituteDetail(RequestBody userId, RequestBody instituteId, Callback<InstituteDetails> callback) {
        RetrofitClient.getClient().getInstituteDetail(userId, instituteId).enqueue(callback);
    }


    public static void getAdditionalDiscount(Callback<ResponseBody> callback) {
        RetrofitClient.getClient().getAdditionalDiscount().enqueue(callback);
    }

    public static void registerUser(RequestBody fb_id, RequestBody name, RequestBody username, RequestBody email_id, RequestBody phone, RequestBody statetxt, RequestBody password, RequestBody college, RequestBody addressBody, RequestBody cityBody, RequestBody countryBody, RequestBody platform, RequestBody acaademicYear_id, RequestBody courseSlectedBody, RequestBody boardname,MultipartBody.Part vFile, Callback<CommonResponse> callback) {
        RetrofitClient.getClient().registerUser(fb_id, name, username, email_id, phone, statetxt, password, college, addressBody, cityBody, countryBody, platform, acaademicYear_id,courseSlectedBody,boardname).enqueue(callback);
    }

    public static void updateUser(RequestBody name, RequestBody user_id, RequestBody password, RequestBody username, RequestBody phone, RequestBody statetxt, RequestBody college, RequestBody address, RequestBody city, RequestBody country, RequestBody acaademicYear_id, Callback<UserUpdateResponse> callback) {
        RetrofitClient.getClient().updateUser(name, user_id, password, username, phone, statetxt, college, address, city, country, acaademicYear_id).enqueue(callback);
    }

    public static void addressDetail(RequestBody user_id, RequestBody name, RequestBody mobile, RequestBody email, RequestBody address_line1, RequestBody address_line2, RequestBody state, RequestBody city, RequestBody pin_code, Callback<AddressDetailResponse> callback) {
        RetrofitClient.getClient().addressDetail(user_id, name, mobile, email, address_line1, address_line2, state, city, pin_code).enqueue(callback);
    }

    public static void updateDetail(RequestBody a_id, RequestBody user_id, RequestBody name, RequestBody mobile, RequestBody email, RequestBody address_line1, RequestBody address_line2, RequestBody state, RequestBody city, RequestBody pin_code, Callback<UpdateAddressResponse> callback) {
        RetrofitClient.getClient().updateDetail(a_id, user_id, name, mobile, email, address_line1, address_line2, state, city, pin_code).enqueue(callback);
    }


    public static void editProfile(RequestBody id, RequestBody username, RequestBody mobile_no, RequestBody state, RequestBody college, Callback<EditProfileResponse> callback) {
        RetrofitClient.getClient().editProfile(id, username, mobile_no, state, college).enqueue(callback);
    }

    public static void franchiesRegister(RequestBody username, RequestBody usermail, RequestBody phoneno, RequestBody whatsppNumber,
                                         RequestBody pCity, RequestBody pState, RequestBody pAddress, RequestBody pLandmark, RequestBody pPincode,
                                         RequestBody collegaeFrenchise, RequestBody cMedicalCollegae, RequestBody sMedicalCollege, RequestBody pinMedicalCollege,
                                         RequestBody comment, RequestBody amount,RequestBody canCall,Callback<FranchiesResponse> callback) {
        RetrofitClient.getClient().franchiRegister(username, usermail, phoneno, whatsppNumber, pCity, pState, pAddress, pLandmark, pPincode, collegaeFrenchise,
                                                   cMedicalCollegae, sMedicalCollege, pinMedicalCollege, comment,amount,canCall).enqueue(callback);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void facebookRegister(RequestBody name, RequestBody email_id, RequestBody fb_id, Callback<FacebookResponse> callback) {
        RetrofitClient.getClient().facebookRegister(name, email_id, fb_id).enqueue(callback);
    }

    public static void getCourses(Callback<CategoryDetailData> callback) {
        RetrofitClient.getClient().getCourse().enqueue(callback);
    }


    public static void getAllModulesForCategory(RequestBody requestBody, Callback<CatModuleResponse> callback) {
        RetrofitClient.getClient().getAllModulesForCategory(requestBody).enqueue(callback);
    }

    public static void getCollege(Callback<CollegeListResponse> callback) {
        RetrofitClient.getClient().collegeData().enqueue(callback);
    }

    public static void getState(Callback<StateListResponse> callback) {
        RetrofitClient.getClient().stateData().enqueue(callback);
    }


    public static void getAllAcademicYears(Callback<Academic> callback) {
        RetrofitClient.getClient().getAllAcademicYears().enqueue(callback);
    }


    public static void getAllCourse(Callback<CourseResponse> callback) {
        RetrofitClient.getClient().getCourseList().enqueue(callback);
    }


    public static void playstoreUpdate(Callback<PlaystoreUpdateResponse> playstoreUpdateResponseCallback) {
        RetrofitClient.getClient().playstoreUpdate().enqueue(playstoreUpdateResponseCallback);
    }


    public static void submitAnswer(RequestBody u_id, RequestBody q_id, RequestBody module_id, RequestBody userAnswer, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().submitAnswer(u_id, q_id, module_id, userAnswer).enqueue(callback);
    }


    public static void updateQBankStatus(RequestBody u_id, RequestBody module_id, Callback<ModuleResponse> callback) {
        RetrofitClient.getClient().updateQBankStatus(u_id, module_id).enqueue(callback);
    }


    public static void getMCQResult(RequestBody u_id, RequestBody module_id, Callback<QBankResultResponse> callback) {
        RetrofitClient.getClient().getMCQResult(u_id, module_id).enqueue(callback);
    }

    public static void submitQuestionTestAnswer(RequestBody userId, RequestBody testID, RequestBody qID, RequestBody answerID, RequestBody guesStatus, RequestBody edit, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().submitTestQuestionAnswer(userId, testID, qID, answerID, guesStatus, edit).enqueue(callback);
    }


    public static void submitTestAnswer(RequestBody userId, RequestBody testID, RequestBody qID, RequestBody answerID, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().submitTestAnswer(userId, testID, qID, answerID).enqueue(callback);
    }


    public static void getVideos(RequestBody sub_child_cat, RequestBody fileType, RequestBody user_id, Callback<VideoList> callback) {
        RetrofitClient.getClient().getVideos(sub_child_cat, fileType, user_id).enqueue(callback);
    }



    public static void getnewPlansForSSUG(Callback<PlanResponseForSSAndUG> callback) {
        RetrofitClient.getClient().getnewPlansForSSUG().enqueue(callback);
    }


    public static void getPaidvedio(RequestBody sub_child_cat, RequestBody user_id, RequestBody file_type, Callback<PaidVideoResponse> callback) {
        RetrofitClient.getClient().getPaidVedio(sub_child_cat, user_id, file_type).enqueue(callback);
    }

    public static void getQuestion(String user_id, String test_id, Callback<QustionDetails> callback) {
        RetrofitClient.getClient().getQuestion(user_id, test_id).enqueue(callback);
    }

    public static void qbankReview(RequestBody user_id, RequestBody qmodule_id, Callback<ReviewListResponse> callback) {
        RetrofitClient.getClient().qbankReview(user_id, qmodule_id).enqueue(callback);
    }


    public static void getTest(RequestBody user_id, Callback<TestQuestionData> callback) {
        RetrofitClient.getClient().getTest(user_id).enqueue(callback);
    }

    public static void getPlanById(RequestBody subs_id, Callback<PlanDetailResponse> callback) {
        RetrofitClient.getClient().getPlanById(subs_id).enqueue(callback);
    }


    public static void getSubscriptionPlans(Callback<PlanResponse> callback) {
        RetrofitClient.getClient().getSubscriptionPlans().enqueue(callback);
    }

    public static void updateVideoPlayTime(RequestBody user_id, RequestBody video_id, RequestBody time, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().updateVideoPlayTime(user_id, video_id, time).enqueue(callback);
    }


    public static void updateVideoProgress(RequestBody user_id, RequestBody video_id, RequestBody time, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().updateVideoProgress(user_id, video_id, time).enqueue(callback);
    }

    public static void sentMail(RequestBody email_id, Callback<ForgetMailSentResponse> callback) {
        RetrofitClient.getClient().sentMail(email_id).enqueue(callback);
    }


    public static void startTest(RequestBody userId, RequestBody testID, RequestBody isSubmit, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().startTest(userId, testID, isSubmit).enqueue(callback);

    }


    public static void endTest(RequestBody userId, RequestBody testID, RequestBody time, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().endTest(userId, testID, time).enqueue(callback);

    }


    public static void submitTest(RequestBody userId, RequestBody testID, RequestBody time, Callback<TestResult> callback) {
        RetrofitClient.getClient().submitTest(userId, testID, time).enqueue(callback);

    }

    public static void getResultRemark(String testID, Callback<RankResultRemarks> callback) {
        RetrofitClient.getClient().getResultRemark(testID).enqueue(callback);

    }

    public static void getStudentRank(RequestBody userId, RequestBody testID, Callback<RankResult> callback) {
        RetrofitClient.getClient().getStudentRank(userId, testID).enqueue(callback);

    }

    public static void resultList(RequestBody user_id, RequestBody test_id, Callback<ResultList> callback) {
        RetrofitClient.getClient().resultList(user_id, test_id).enqueue(callback);

    }

    public static void reviewQuestionResult(RequestBody user_id, RequestBody test_id, Callback<TestReviewResponse> callback) {
        RetrofitClient.getClient().reviewQuestionResult(user_id, test_id).enqueue(callback);
    }

    public static void facultyData(Callback<FacultyDetail> callback) {
        RetrofitClient.getClient().facultyData().enqueue(callback);


    }

    public static void knowMoreData(Callback<Directors> callback) {
        RetrofitClient.getClient().knowMoreData().enqueue(callback);


    }


    public static void qbankDetail(RequestBody userId, RequestBody catId, Callback<ModuleListResponse> callback) {
        RetrofitClient.getClient().qbankDetail(userId, catId).enqueue(callback);
    }


    public static void getAllChapterByModuleId(RequestBody user_id, RequestBody module_id, Callback<ChaptersModuleResponse> callback) {
        RetrofitClient.getClient().getAllChapterByModuleId(user_id, module_id).enqueue(callback);
    }

    public static void qbankStart(RequestBody qmodule_id, RequestBody user_id, RequestBody is_paused, Callback<QbankstartResponse> callback) {
        RetrofitClient.getClient().qbankStart(qmodule_id, user_id, is_paused).enqueue(callback);
    }

    public static void qbanksubdata(RequestBody qcat_id, RequestBody user_id, Callback<QbankSubResponse> callback) {
        RetrofitClient.getClient().qbanksubdata(qcat_id, user_id).enqueue(callback);
    }

    public static void qbanksubTestData(RequestBody user_id, RequestBody qmodule_id, Callback<MCQQuestionList> callback) {
        RetrofitClient.getClient().getAllMCQQuestions(user_id, qmodule_id).enqueue(callback);
    }

    public static void qbankFeedback(RequestBody user_id, RequestBody qmodule_id, RequestBody rating, RequestBody feedback, RequestBody remark, Callback<QbankfeedbackResponse> callback) {
        RetrofitClient.getClient().qbankFeedback(user_id, qmodule_id, rating, feedback, remark).enqueue(callback);
    }

    public static void sendOtp(RequestBody phone, Callback<CommonResponse> callback) {
        RetrofitClient.getClient().sendOtp(phone).enqueue(callback);
    }

    public static void verifyOtp(RequestBody userid, RequestBody code, Callback<VerifyOtpResponse> callback) {
        RetrofitClient.getClient().verifyOTP(userid, code).enqueue(callback);

    }

    public static void getVideo(Callback<PromoVideo> responseBodyCallback) {
        RetrofitClient.getClient().getVideo().enqueue(responseBodyCallback);
    }

    public static void verifyDetail(RequestBody user_id, RequestBody v_title, MultipartBody.Part v_image, Callback<VerifyIdResponse> callback) {
        RetrofitClient.getClient().verifyDetail(user_id, v_title, v_image);
    }

    public static void getAddressData(RequestBody user_id, Callback<GetDataAddressResponse> callback) {
        RetrofitClient.getClient().getAddressData(user_id).enqueue(callback);
    }

    public static void saveOrderDetail(RequestBody order_id, RequestBody sub_child_cat_id, RequestBody user_id, RequestBody product_id, RequestBody video_id, RequestBody test_id, RequestBody status, Callback<SaveOrderResponse> callback) {
        RetrofitClient.getClient().saveOrderDetail(order_id, sub_child_cat_id, user_id, product_id, video_id, test_id, status).enqueue(callback);
    }

    public static void addOrderDetail(RequestBody order_id, RequestBody sub_child_cat_id,
                                      RequestBody user_id, RequestBody product_id,
                                      RequestBody video_id, RequestBody test_id,
                                      RequestBody status, RequestBody cat_id,
                                      RequestBody sub_cat_id,
                                      Callback<SaveOrderResponse> callback) {
        RetrofitClient.getClient().addOrderDetail(order_id, sub_child_cat_id, user_id, product_id,
                video_id, test_id, status).enqueue(callback);
    }

//    public static void addOrderDetail(RequestBody order_id, RequestBody sub_child_cat_id,RequestBody cat_id,RequestBody sub_cat_id, RequestBody user_id, RequestBody product_id, RequestBody video_id, RequestBody test_id, RequestBody status, Callback<SaveOrderResponse> callback) {
//        RetrofitClient.getClient().addOrderDetail(order_id, sub_child_cat_id,cat_id,sub_cat_id, user_id, product_id, video_id, test_id, status).enqueue(callback);
//    }


    public static void addOrderForSubsDetail(RequestBody user_id, RequestBody order_id,
                                             RequestBody planId, RequestBody subscriptioId,
                                             RequestBody packKey, RequestBody month,
                                             RequestBody status, RequestBody price, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().addOrderForSubsDetail(user_id, order_id, planId, subscriptioId, packKey, month, status, price).enqueue(callback);
    }


    public static void createOrderDetail(RequestBody user_id, RequestBody amount, RequestBody currency,
                                         RequestBody video_id, RequestBody product_type,
                                         Callback<CreateOrderResponse> callback) {
        RetrofitClient.getClient().createOrderDetail(user_id, amount, currency, video_id, product_type).enqueue(callback);
    }


    public static void invoiceOrderDetail(RequestBody user_id, RequestBody pramotion, RequestBody additional, RequestBody totalAmountBeforeTax, RequestBody tax, RequestBody shippingCharges, RequestBody grandTotal, RequestBody totalAmount, RequestBody orderID, Callback<SaveOrderResponse> callback) {
        RetrofitClient.getClient().invoiceOrderDetail(user_id, pramotion, additional, totalAmountBeforeTax, tax, shippingCharges, grandTotal, totalAmount, orderID).enqueue(callback);
    }


    public static void forgetPassword(RequestBody email_id, RequestBody token, RequestBody new_password, RequestBody retype_password, Callback<ForgetPasswordResponse> callback) {
        RetrofitClient.getClient().forgetPassword(email_id, token, new_password, retype_password).enqueue(callback);
    }

    public static void getMobile(RequestBody email_id, Callback<MobileResponse> callback) {
        RetrofitClient.getClient().MOBILE_RESPONSE_CALL(email_id).enqueue(callback);

    }

    public static void getEmail(RequestBody fb_id, Callback<EmailByFBResponse> callback) {
        RetrofitClient.getClient().getEmailByFBID(fb_id).enqueue(callback);

    }

    public static void getUserByEmail(RequestBody email_id, Callback<LoginDetailForDemo> callback) {
        RetrofitClient.getClient().getUserByEmail(email_id).enqueue(callback);

    }

    public static void loginWithFacebook(RequestBody fb_id, RequestBody deviceRequestBody, Callback<FacebookLoginResponse> callback) {
        RetrofitClient.getClient().loginWithFacebook(fb_id, deviceRequestBody).enqueue(callback);

    }


    public static void enterMobileNumberToServer(RequestBody id, RequestBody mobile_no, Callback
            <EnterMobileresponce> callback) {
        RetrofitClient.getClient().enterMobileNumber(id, mobile_no).enqueue(callback);

    }

    public static void updateLogin(RequestBody id, RequestBody isReal, Callback
            <ResponseBody> callback) {
        RetrofitClient.getClient().updateLogin(id, isReal).enqueue(callback);

    }


    public static void checkuserExist(RequestBody email, Callback
            <ResponseBody> callback) {
        RetrofitClient.getClient().checkuserExist(email).enqueue(callback);

    }

    public static void deleteAddress(RequestBody add_id, Callback
            <ResponseBody> callback) {
        RetrofitClient.getClient().deleteAddress(add_id).enqueue(callback);

    }

    public static void getAllTestData(String id, String institute_id, String catId, Callback
            <TestDataResponse> callback) {
        RetrofitClient.getClient().getAllTestData(id, institute_id, catId).enqueue(callback);

    }


    public static void submit_timeLog(RequestBody user_id, RequestBody timespend, RequestBody event, RequestBody subevent, RequestBody test_id, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().submit_timeLog(user_id, timespend, event, subevent, test_id).enqueue(callback);

    }
    // new Api changes Here

    public static void getTestReviewListData(String test_id, String user_id, String level, String subject, String answer, String filter_bookmark, Callback<TestReviewListResponse> callback) {
        RetrofitClient.getClient().getTestReviewListData(test_id, user_id, level, answer, subject, filter_bookmark).enqueue(callback);
    }


    public static void getQBankReviewListData(String test_id, String user_id, String level, String subject, String answer, String filter_bookmark, Callback<TestReviewListResponse> callback) {
        RetrofitClient.getClient().getQBankReviewListData(test_id, user_id, level, answer, subject, filter_bookmark).enqueue(callback);
    }


    public static void bookMarkQuestion(RequestBody user_id, RequestBody test_id, RequestBody question_id, RequestBody bookmark, RequestBody type, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().bookMarkQuestion(user_id, test_id, question_id, bookmark, type).enqueue(callback);

    }


    public static void completeMCQ(RequestBody user_id, RequestBody moduleId, RequestBody complete, RequestBody subject, RequestBody chapter, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().completeMCQ(user_id, moduleId, complete, subject, chapter).enqueue(callback);

    }


    public static void invoiceOrderDetailForSubscription(RequestBody user_id, RequestBody orderId_forInvoice,
                                                         RequestBody pramotoin, RequestBody addDiscount, RequestBody totalAmountBeforeTax,
                                                         RequestBody tax, RequestBody shippingCharges, RequestBody totalAmount, RequestBody paymethod,
                                                         RequestBody discount, RequestBody grandTotal, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().invoiceOrderDetailForSubscription(user_id, orderId_forInvoice, pramotoin, addDiscount,
                totalAmountBeforeTax, tax, shippingCharges, totalAmount, paymethod, discount, grandTotal).enqueue(callback);
    }

    // Change phone Number
    public static void changePhoneNumber(RequestBody user_id, RequestBody phoneNo, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().changePhoneNumber(user_id, phoneNo).enqueue(callback);
    }

    // change phone no otp Response

    public static void changePhoneNoOTPVerification(RequestBody user_id, RequestBody mobile, RequestBody otp, Callback<ChangePhoneNumberOtpResponse> callback) {
        RetrofitClient.getClient().phoneOtpVerified(user_id, mobile, otp).enqueue(callback);
    }


    public static void getAllInstitute(RequestBody user_id, Callback<AllInstituteResponseModel> callback) {
        RetrofitClient.getClient().getAllInstitute(user_id).enqueue(callback);
    }


    public static void logOut(RequestBody userId, Callback<LogOutResponse> callback) {
        RetrofitClient.getClient().LOG_OUT_RESPONSE_CALL(userId).enqueue(callback);
    }

}
