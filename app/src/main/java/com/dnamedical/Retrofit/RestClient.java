package com.dnamedical.Retrofit;

import com.dnamedical.Models.Directors;
import com.dnamedical.Models.EditProfileResponse.EditProfileResponse;
import com.dnamedical.Models.Enter_Mobile.EmailByFBResponse;
import com.dnamedical.Models.Enter_Mobile.EnterMobileresponce;
import com.dnamedical.Models.PromoVideo;
import com.dnamedical.Models.QbankSubCat.QbankSubResponse;
import com.dnamedical.Models.QbankSubTest.QbankTestResponse;
import com.dnamedical.Models.QbannkReviewList.ReviewListResponse;
import com.dnamedical.Models.QustionDetails;
import com.dnamedical.Models.ResultData.ResultList;
import com.dnamedical.Models.ReviewResult.ReviewResult;
import com.dnamedical.Models.StateList.StateListResponse;
import com.dnamedical.Models.TestReviewList.TestReviewResponse;
import com.dnamedical.Models.VerifyOtpResponse;
import com.dnamedical.Models.addressDetail.AddressDetailResponse;
import com.dnamedical.Models.answer.SubmitAnswer;
import com.dnamedical.Models.collegelist.CollegeListResponse;
import com.dnamedical.Models.facebook.FacebookResponse;
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
import com.dnamedical.R;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Part;

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

    public static void getAdditionalDiscount(Callback<ResponseBody> callback) {
        RetrofitClient.getClient().getAdditionalDiscount().enqueue(callback);
    }

    public static void registerUser(RequestBody name, RequestBody username, RequestBody email_id, RequestBody phone, RequestBody statetxt, RequestBody password, RequestBody college, MultipartBody.Part vFile, Callback<CommonResponse> callback) {
        RetrofitClient.getClient().registerUser(name, username, email_id, phone, statetxt, password, college).enqueue(callback);
    }

    public static void addressDetail(RequestBody user_id, RequestBody name, RequestBody mobile, RequestBody email, RequestBody address_line1, RequestBody address_line2, RequestBody state, RequestBody city, RequestBody pin_code, Callback<AddressDetailResponse> callback) {
        RetrofitClient.getClient().addressDetail(user_id, name, mobile, email, address_line1, address_line2, state, city, pin_code).enqueue(callback);
    }
    public static void updateDetail(RequestBody a_id,RequestBody user_id, RequestBody name, RequestBody mobile, RequestBody email, RequestBody address_line1, RequestBody address_line2, RequestBody state, RequestBody city, RequestBody pin_code, Callback<UpdateAddressResponse> callback) {
        RetrofitClient.getClient().updateDetail(a_id,user_id, name, mobile, email, address_line1, address_line2, state, city, pin_code).enqueue(callback);
    }


    public static void editProfile(RequestBody id, RequestBody username, RequestBody mobile_no, RequestBody state, RequestBody college, Callback<EditProfileResponse> callback) {
        RetrofitClient.getClient().editProfile(id, username, mobile_no, state, college).enqueue(callback);
    }

    public static void franchiesRegister(RequestBody username, RequestBody phoneno, RequestBody usermail, RequestBody comment, Callback<FranchiesResponse> callback) {
        RetrofitClient.getClient().franchiRegister(username, phoneno, usermail, comment).enqueue(callback);
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

    public static void getState(Callback<StateListResponse> callback) {
        RetrofitClient.getClient().stateData().enqueue(callback);
    }


    public static void playstoreUpdate(Callback<PlaystoreUpdateResponse> playstoreUpdateResponseCallback) {
        RetrofitClient.getClient().playstoreUpdate().enqueue(playstoreUpdateResponseCallback);
    }


    public static void submitAnswer(String q_id, String u_id, String is_completed, String userAnswer, Callback<SubmitAnswer> callback) {
        RetrofitClient.getClient().submitAnswer(q_id, u_id, is_completed, userAnswer).enqueue(callback);
    }


    public static void getVideos(String sub_child_cat, String fileType, Callback<VideoList> callback) {
        RetrofitClient.getClient().getVideos(sub_child_cat, fileType).enqueue(callback);
    }


    public static void getPaidvedio(RequestBody sub_child_cat, RequestBody user_id, RequestBody file_type, Callback<PaidVideoResponse> callback) {
        RetrofitClient.getClient().getPaidVedio(sub_child_cat, user_id, file_type).enqueue(callback);
    }

    public static void getQuestion(String test_id, Callback<QustionDetails> callback) {
        RetrofitClient.getClient().getQuestion(test_id).enqueue(callback);
    }

    public static void qbankReview(RequestBody user_id, RequestBody qmodule_id, Callback<ReviewListResponse> callback) {
        RetrofitClient.getClient().qbankReview(user_id, qmodule_id).enqueue(callback);
    }


    public static void getTest(RequestBody user_id, Callback<TestQuestionData> callback) {
        RetrofitClient.getClient().getTest(user_id).enqueue(callback);
    }

    public static void updateVideoPlayTime(RequestBody user_id,RequestBody video_id,RequestBody time, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().updateVideoPlayTime(user_id,video_id,time).enqueue(callback);
    }

    public static void sentMail(RequestBody email_id, Callback<ForgetMailSentResponse> callback) {
        RetrofitClient.getClient().sentMail(email_id).enqueue(callback);
    }


    public static void submitTest(String user_id, String test_id,
                                  String tquestion, String ttquestion, String canswer, String ccanswer, String wanswer, String wwanswer, String sanswer, String ssanswer, String test_finish_duration, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().submitTest(user_id, test_id, tquestion, ttquestion, canswer, ccanswer, wanswer, wwanswer, sanswer, ssanswer, test_finish_duration).enqueue(callback);

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

    public static void getVideo(Callback<PromoVideo> responseBodyCallback) {
        RetrofitClient.getClient().getVideo().enqueue(responseBodyCallback);
    }

    public static void verifyDetail(RequestBody user_id, RequestBody v_title, MultipartBody.Part v_image, Callback<VerifyIdResponse> callback) {
        RetrofitClient.getClient().verifyDetail(user_id, v_title, v_image);
    }

    public static void getAddressData(RequestBody user_id, Callback<GetDataAddressResponse> callback) {
        RetrofitClient.getClient().getAddressData(user_id).enqueue(callback);
    }

    public static void saveOrderDetail(RequestBody order_id,RequestBody sub_child_cat_id, RequestBody user_id, RequestBody product_id, RequestBody video_id, RequestBody test_id, RequestBody status, Callback<SaveOrderResponse> callback) {
        RetrofitClient.getClient().saveOrderDetail(order_id, sub_child_cat_id,user_id, product_id, video_id, test_id, status).enqueue(callback);
    }


 public static void createOrderDetail(RequestBody user_id,RequestBody amount, RequestBody currency, RequestBody video_id, RequestBody product_type, Callback<CreateOrderResponse> callback) {
        RetrofitClient.getClient().createOrderDetail(user_id, amount, currency, video_id, product_type).enqueue(callback);
    }


    public static void invoiceOrderDetail(RequestBody user_id,RequestBody pramotion, RequestBody additional, RequestBody totalAmountBeforeTax, RequestBody tax, RequestBody shippingCharges, RequestBody grandTotal,RequestBody totalAmount, Callback<SaveOrderResponse> callback) {
        RetrofitClient.getClient().invoiceOrderDetail(user_id, pramotion, additional, totalAmountBeforeTax, tax, shippingCharges,grandTotal,totalAmount).enqueue(callback);
    }


    public static void forgetPassword(RequestBody email_id, RequestBody token, RequestBody new_password, RequestBody retype_password, Callback<ForgetPasswordResponse> callback) {
        RetrofitClient.getClient().forgetPassword(email_id, token, new_password, retype_password).enqueue(callback);
    }

    public static void getMobile(RequestBody email_id, Callback<MobileResponse> callback){
        RetrofitClient.getClient().MOBILE_RESPONSE_CALL(email_id).enqueue(callback);

    }

    public static void getEmail(RequestBody fb_id, Callback<EmailByFBResponse> callback){
        RetrofitClient.getClient().getEmailByFBID(fb_id).enqueue(callback);

    }

    public static void  enterMobileNumberToServer(RequestBody id,RequestBody mobile_no, Callback
            <EnterMobileresponce> callback){
        RetrofitClient.getClient().enterMobileNumber(id,mobile_no).enqueue(callback);

    }


}