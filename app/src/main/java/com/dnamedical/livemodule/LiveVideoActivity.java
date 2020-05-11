package com.dnamedical.livemodule;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.widget.Toast;

import com.dnamedical.Adapters.ChatListAdapterLatest;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveVideoActivity extends AppCompatActivity implements UploadFileDialog.onUploadFileDialog, FullScreenImagePopup.onFullScreenImagePopup {
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
    String channel = "C6CjT3ndhN0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_video);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        ctx = this;
        liveVideoId = getIntent().getStringExtra("id");
        isStoragePermissionGranted();
        channel = getIntent().getStringExtra("contentId");
        f_id = DnaPrefs.getString(getApplicationContext(), Constants.f_id);
        Log.e("Channel", "::" + channel);

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
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");

            String time = intent.getStringExtra("time");

            String user_id = intent.getStringExtra("user_id");

            String user_name = intent.getStringExtra("user_name");

            String doctor_image = intent.getStringExtra("doctor_image");


            Chat inputMessage = new com.dnamedical.Models.get_chat_history.Chat();
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


        Chat inputMessage = new com.dnamedical.Models.get_chat_history.Chat();
        inputMessage.setMessage(inputmessage);
        inputMessage.setUserId(userId);
        inputMessage.setUsername("");
        inputMessage.setTime("");
        inputMessage.setDoctorImage("");
        messageArrayList.add(inputMessage);
        onsetdapter();
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
                    youTubePlayer.loadVideo(channel, 0f);
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

        starthandler();
      /*  Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                getChatList();
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);*/
        stophandler(true);
    }

    Handler handler = new Handler();

    @Override
    protected void onPause() {
        super.onPause();
        stophandler(false);
    }

    public void starthandler() {
        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                if (condition) {
                   //  getChatList();
                }

                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);

    }

    public void stophandler(Boolean status) {
        condition = status;
    }

    Boolean condition = true;




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

    public void openChatPopUp(String url){


        new FullScreenImagePopup(url,ctx, LiveVideoActivity.this).show();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stophandler(false);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }
}
