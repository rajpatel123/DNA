package com.dnamedical.livemodule;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Adapters.ChatListAdapterLatest;
import com.dnamedical.DNAApplication;
import com.dnamedical.Models.chat_users_history.ChatUsersHistoryResp;
import com.dnamedical.Models.delete_chat_message.DeletechatmessageResp;
import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.popup.FullScreenImagePopup;
import com.dnamedical.popup.UploadFileDialog;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveVideoActivity extends AppCompatActivity implements UploadFileDialog.onUploadFileDialog, FullScreenImagePopup.onFullScreenImagePopup,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    GetChatHistoryResp getChatHistory;
    private String liveVideoId = "C6CjT3ndhN0";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewChat;



    @BindView(R.id.btn_send)
    android.support.v7.widget.AppCompatImageButton btnSend;
    @BindView(R.id.message)
    EditText message;
    ChatListAdapterLatest chatListAdapter;
    private ArrayList<Chat> messageArrayList = new ArrayList();
    @BindView(R.id.btnImage)
    android.support.v7.widget.AppCompatImageView btnImage;

    String f_id = "";
    private int GALLERY = 1, CAMERA = 2;
    private List<String> imagePathList = new ArrayList<>();
    Context ctx;
    String channelKey = "";
    String lati, longi;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private long joiningTime;
    private Chanel chanel;
    String ChannelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_video);
        ButterKnife.bind(this);


        if (getIntent().hasExtra("chanel")){
            chanel= getIntent().getParcelableExtra("chanel");

        }

        joiningTime = System.currentTimeMillis();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                    != getPackageManager().PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[1])
                            != getPackageManager().PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        mPermission, REQUEST_CODE_PERMISSION);

                // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
            }

//            Intent i = new
//                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableLoc();


        ctx = this;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        liveVideoId = getIntent().getStringExtra("id");
        isStoragePermissionGranted();
        channelKey = chanel.getChannelId();
      //  String channelName = getIntent().getStringExtra("channelName");
        f_id = DnaPrefs.getString(getApplicationContext(), Constants.f_id);
        Log.e("Channel", "::" + channelKey);

        initYouTubePlayerView();

        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }
        getChatList();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getApplicationContext())) {
                    if (!message.getText().toString().equalsIgnoreCase("")) {
                        sendMessage();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please type something..!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new UploadFileDialog(ctx, LiveVideoActivity.this).show();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));



    }

    private void userUpdateTime(String status,  String state,String city, String country){

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        userVerify(ts,status,state, city,country);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");

            String time = intent.getStringExtra("time");

            String user_id = intent.getStringExtra("user_id");

            String user_name = intent.getStringExtra("user_name");

            String doctor_image = intent.getStringExtra("doctor_image");


            Chat inputMessage = new Chat();
            inputMessage.setMessage(message);
            inputMessage.setUserId(user_id);
            inputMessage.setUsername(user_name);
            inputMessage.setTime(time);
            inputMessage.setDoctorImage(doctor_image);
            messageArrayList.add(inputMessage);
            onsetdapter();


            Log.e("Faculty", "Got message: " + message);
        }
    };

    String userId;

    private void sendMessage() {


        final String inputmessage = this.message.getText().toString().trim();


        Chat inputMessage = new Chat();
        inputMessage.setMessage(inputmessage);
        inputMessage.setUserId(userId);
        inputMessage.setUsername("");
        inputMessage.setTime("");
        inputMessage.setDoctorImage("");
        messageArrayList.add(inputMessage);
       // onsetdapter();
        setChatMessageText(inputmessage);
        message.setText("");


    }

    private void initYouTubePlayerView() {
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.getPlayerUIController().showFullscreenButton(true);
        youTubePlayerView.getPlayerUIController().enableLiveVideoUI(true);
        youTubePlayerView.getPlayerUIController().showDuration(true);
        youTubePlayerView.getPlayerUIController().setCustomMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.getPlayerUIController().showUI(true);
            }
        });
        youTubePlayerView.getPlayerUIController().showPlayPauseButton(true);
        youTubePlayerView.getPlayerUIController().showYouTubeButton(false);


        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.initialize(youTubePlayer -> {

            youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {
                    youTubePlayer.loadVideo(channelKey, 0f);
                }
            });
        }, true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    private void onsetdapter() {


        chatListAdapter = new ChatListAdapterLatest(LiveVideoActivity.this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(chatListAdapter);
        //  message.setText("");
    }


    public void getDeleteChatMessage(String id) {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            RestClient.delete_chat_message("delete_chat_message", id, new Callback<DeletechatmessageResp>() {
                @Override
                public void onResponse(Call<DeletechatmessageResp> call, Response<DeletechatmessageResp> response) {
                    if (response.code() == 200) {
                        //  Utils.dismissProgressDialog();

                        try {


                            DeletechatmessageResp deletechatmessageResp = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("Deletecha Resp", gson.toJson(deletechatmessageResp));

                            if (deletechatmessageResp.getStatus().equalsIgnoreCase("1")) {
                                //stophandler(true);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<DeletechatmessageResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            // Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void getChatList() {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            RestClient.getchathistory("get_chat_historys", liveVideoId, userId, "", new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //     Utils.dismissProgressDialog();

                        GetChatHistoryResp getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("liveVideoId Resp", gson.toJson(getChatHistory));


                        if (getChatHistory.getStatus().equalsIgnoreCase("1")) {
                            if (messageArrayList.size() != getChatHistory.getChat().size()) {

                                messageArrayList.clear();
                                messageArrayList.addAll(getChatHistory.getChat());
                                if (messageArrayList != null && messageArrayList.size() > 0) {

                                    onsetdapter();
                                    recyclerViewChat.setVisibility(View.VISIBLE);
                                } else {

                                    recyclerViewChat.setVisibility(View.GONE);
                                }
                            } else {


                            }
                        }


                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    //  Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void setChatMessageText(String message11) {


        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), liveVideoId);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);
        RequestBody facultyID = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_messageText(channelId, userId12, message, facultyID, new Callback<GetChatHistoryResp>() {

                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //   Utils.dismissProgressDialog();

                        GetChatHistoryResp getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("liveVideoId Resp", gson.toJson(getChatHistory));


                        if (getChatHistory.getStatus().equalsIgnoreCase("1")) {
                            messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());
                            onsetdapter();
                            recyclerViewChat.setVisibility(View.VISIBLE);
                        } else {

                            recyclerViewChat.setVisibility(View.GONE);
                        }


                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    private void setChatMessage(String message11) {
        Log.e("imagePathList", "::" + imagePathList.size());

        File file = new File(imagePathList.get(0));


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", getCompressedImageFile(file, getApplicationContext()).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), getCompressedImageFile(file, getApplicationContext()).getAbsoluteFile()));

        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), liveVideoId);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);
        RequestBody facultyID = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_message(body, channelId, userId12, message, facultyID, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //   Utils.dismissProgressDialog();

                        GetChatHistoryResp getChatHistory = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("liveVideoId Resp", gson.toJson(getChatHistory));


                        if (getChatHistory.getStatus().equalsIgnoreCase("1")) {
                            messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());
                            onsetdapter();
                            recyclerViewChat.setVisibility(View.VISIBLE);
                        } else {

                            recyclerViewChat.setVisibility(View.GONE);
                        }


                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

    }

    Handler handler = new Handler();

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        userUpdateTime("0",state,city,country);
    }


    private static final int PICK_FROM_GALLERY = 100;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY) {
            if (data != null) {

                try {


                    if (data.getClipData() != null) {

                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {

                            Uri imageUri = data.getClipData().getItemAt(i).getUri();


                            getImageFilePath(imageUri);
                            setChatMessage("");
                        }
                    } else if (data.getData() != null) {

                        Uri imgUri = data.getData();

                        getImageFilePath(imgUri);
                        setChatMessage("");
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

        } else if (requestCode == CAMERA && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");


            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            imagePathList.clear();

            imagePathList.add(getRealPathFromURI(tempUri));
            setChatMessage("");

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            //    System.out.println(mImageCaptureUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }


                break;
        }
    }


    public File getCompressedImageFile(File file, Context mContext) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            if (getFileExt(file.getName()).equals("png") || getFileExt(file.getName()).equals("PNG")) {
                o.inSampleSize = 6;
            } else {
                o.inSampleSize = 6;
            }

            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);

            ExifInterface ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotateImage(selectedBitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotateImage(selectedBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotateImage(selectedBitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:

                default:
                    break;
            }
            inputStream.close();


            // here i override the original image file

            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/imgs/";
            File folder = new File(/*Environment.getExternalStorageDirectory() + "/FolderName"*/rootPath);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                File newFile = new File(new File(folder.getAbsolutePath()), file.getName());
                if (newFile.exists()) {
                    newFile.delete();
                }
                FileOutputStream outputStream = new FileOutputStream(newFile);

                if (getFileExt(file.getName()).equals("png") || getFileExt(file.getName()).equals("PNG")) {
                    selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                } else {
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                }

                return newFile;
            } else {
                return null;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public void getImageFilePath(Uri uri) {


        imagePathList.clear();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        imagePathList.add(path);
        cursor.close();


    }

    public void choosePhotoFromGallary() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, GALLERY);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted");
                return true;
            } else {

                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onUploadFile(String type) {
        if (type.equalsIgnoreCase("camera")) {
            takePhotoFromCamera();

        } else if (type.equalsIgnoreCase("Gallery")) {
            choosePhotoFromGallary();

        }
    }

    private void takePhotoFromCamera() {
        try {
            captureImage();

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void captureImage() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        CAMERA);
                return;
            } else {
                /*  PrefUtils.saveToPrefs(getApplicationContext(), PrefUtils.permission, "True");*/
                return;
            }
        } else {

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public void onImage(String type) {


    }

    public void openChatPopUp(String url) {


        new FullScreenImagePopup(url, ctx, LiveVideoActivity.this).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }


    public void userVerify(String time,String status12,String state,String city, String country) {
        if (Utils.isInternetConnected(this)) {


            Log.e("cID","::"+chanel.getId());
            Log.e("cID","::"+chanel.getLiveStartedTime());
            Log.e("cID","::"+joiningTime);
            Log.e("cID","::"+System.currentTimeMillis());
            Log.e("userId","::"+userId);
            Log.e("getFaculty_id","::"+chanel.getFaculty_id());
            Log.e("getDoctorName","::"+chanel.getDoctorName());
            Log.e("state","::"+state);
            Log.e("city","::"+city);
            Log.e("country","::"+country);
            Log.e("status12","::"+status12);
            Log.e("mobile","::"+DNAApplication.getInstance().getUserData().getData().getMobileNo());
            Log.e("email","::"+DNAApplication.getInstance().getUserData().getData().getEmailId());

            RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), chanel.getId());
            RequestBody date = RequestBody.create(MediaType.parse("text/plain"), ""+chanel.getLiveStartedTime());
            RequestBody join_time = RequestBody.create(MediaType.parse("text/plain"), ""+joiningTime);
            RequestBody leaving_Time = RequestBody.create(MediaType.parse("text/plain"), ""+System.currentTimeMillis());
            RequestBody batch = RequestBody.create(MediaType.parse("text/plain"),chanel.getBatchname() );
            RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), userId);
            RequestBody educator = RequestBody.create(MediaType.parse("text/plain"), chanel.getFaculty_id());
            RequestBody educatorName = RequestBody.create(MediaType.parse("text/plain"), chanel.getDoctorName());
            RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), DNAApplication.getInstance().getUserData().getData().getEmailId());
            RequestBody mobileBody = RequestBody.create(MediaType.parse("text/plain"), DNAApplication.getInstance().getUserData().getData().getMobileNo() );
            RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), city);
            RequestBody stateBody = RequestBody.create(MediaType.parse("text/plain"), state);
            RequestBody countryBody = RequestBody.create(MediaType.parse("text/plain"), country);
            RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"), status12);
            RequestBody chaneleName = RequestBody.create(MediaType.parse("text/plain"), chanel.getCategoryname());




            // Utils.showProgressDialog(this);
            RestClient.chat_users_history(channelId,date,join_time, userId12, leaving_Time,
                    batch, educator, educatorName, emailBody,mobileBody,cityBody,stateBody,countryBody,statusBody,chaneleName, new Callback<ChatUsersHistoryResp>() {
                @Override
                public void onResponse(Call<ChatUsersHistoryResp> call, Response<ChatUsersHistoryResp> response) {
                    if (response.code() == 200) {
                        //  Utils.dismissProgressDialog();

                        try {


                            ChatUsersHistoryResp chatUsersHistoryResp = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("chatUsersHistoryRes", gson.toJson(chatUsersHistoryResp));




                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<ChatUsersHistoryResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            // Utils.dismissProgressDialog();

            Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        try {


            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                lati = "" + currentLatitude;
                longi = "" + currentLongitude;




               Log.e("Get address",""+getAddressFromLatLng(getApplicationContext(), location.getLatitude(),location.getLongitude()));

                userUpdateTime("1",state,city,country);
               // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   String state="",city="",country="";
    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

      //  Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(LiveVideoActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(LiveVideoActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                }
            }
        });
    }
    public String getAddressFromLatLng(Context context, Double latitude,Double longitude ) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            city =addresses.get(0).getLocality();

            state =addresses.get(0).getAdminArea();

            country =addresses.get(0).getCountryName();

            return addresses.get(0).getAddressLine(0)+", "/*+addresses.get(0).getLocality()+", " + addresses.get(0).getSubLocality()+" "+addresses.get(0).getAdminArea()+", "+addresses.get(0).getCountryName()*/;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
