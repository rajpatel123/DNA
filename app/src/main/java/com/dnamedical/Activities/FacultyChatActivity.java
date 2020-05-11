package com.dnamedical.Activities;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dnamedical.Models.delete_chat_message.DeletechatmessageResp;
import com.dnamedical.Models.get_chat_history.Chat;
import com.dnamedical.Models.get_chat_history.GetChatHistoryResp;
import com.dnamedical.Models.updte_chat_status.UpdteChatstatusRes;
import com.dnamedical.R;
import com.dnamedical.Retrofit.RestClient;
import com.dnamedical.livemodule.ChatListAdapter;
import com.dnamedical.popup.FullScreenImagePopup;
import com.dnamedical.popup.UploadFileDialog;
import com.dnamedical.utils.Constants;
import com.dnamedical.utils.DnaPrefs;
import com.dnamedical.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class FacultyChatActivity extends AppCompatActivity implements UploadFileDialog.onUploadFileDialog, FullScreenImagePopup.onFullScreenImagePopup {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewChat;
    @BindView(R.id.tv_title)
    TextView tvtitle;
    Context ctx;
    @BindView(R.id.iv_back)
    ImageView ivback;
    @BindView(R.id.toggle)
    Switch toggle;

    @BindView(R.id.btnImage)
    android.support.v7.widget.AppCompatImageView btnImage;


    @BindView(R.id.btn_send)
    android.support.v7.widget.AppCompatImageButton btnSend;
    @BindView(R.id.message)
    EditText message;
    ChatListAdapter chatListAdapter;
    private ArrayList<Chat> messageArrayList = new ArrayList();
    String channelID;
    String f_id, userId;
    Boolean condition = false;
    private int GALLERY = 1, CAMERA = 2;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private List<String> imagePathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultychatactivity);
        ButterKnife.bind(this);
        channelID = getIntent().getStringExtra("channelID");

        f_id = DnaPrefs.getString(getApplicationContext(), Constants.f_id);
        if (DnaPrefs.getBoolean(getApplicationContext(), "isFacebook")) {
            userId = String.valueOf(DnaPrefs.getInt(getApplicationContext(), "fB_ID", 0));
        } else {
            userId = DnaPrefs.getString(getApplicationContext(), Constants.LOGIN_ID);
        }
        isStoragePermissionGranted();
        tvtitle.setText("Chat");
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        ctx = this;
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new UploadFileDialog(ctx, FacultyChatActivity.this).show();
            }
        });
        starthandler();
        getonlineoffline("0", channelID);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    getonlineoffline("1", channelID);
                    stophandler(true);
                    getChatList();

                } else {
                    getonlineoffline("0", channelID);
                    stophandler(false);

                }
            }
        });
        messageArrayList.clear();
        getChatList();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getApplicationContext())) {
                    if (!message.getText().toString().equalsIgnoreCase("")) {


                        if (condition) {
                            sendMessage();
                        } else {
                            Toast.makeText(getApplicationContext(), "Offline mode", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please type something..!", Toast.LENGTH_SHORT).show();
                    }

                }
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


    public void starthandler() {
        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                if (condition) {
                    //   getChatList();
                }

                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(r, 5000);

    }

    public void stophandler(Boolean status) {
        condition = status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {


        final String inputmessage = this.message.getText().toString().trim();


        Chat inputMessage = new com.dnamedical.Models.get_chat_history.Chat();
        inputMessage.setMessage(inputmessage);
        inputMessage.setUserId(f_id);
        inputMessage.setUsername("");
        inputMessage.setTime("");
        inputMessage.setDoctorImage("");
        messageArrayList.add(inputMessage);
        onsetdapter();
      //  chatListAdapter.notifyDataSetChanged();
        message.setText("");
        setChatMessagesendText(inputmessage);

    }

    private void onsetdapter() {


        chatListAdapter = new ChatListAdapter(FacultyChatActivity.this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChat.setAdapter(chatListAdapter);

    }

    private void getonlineoffline(String status, String channelID11) {


        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.updte_chat_status("updte_chat_status", channelID11, f_id, status, new Callback<UpdteChatstatusRes>() {
                @Override
                public void onResponse(Call<UpdteChatstatusRes> call, Response<UpdteChatstatusRes> response) {
                    if (response.code() == 200) {
                        // Utils.dismissProgressDialog();

                        UpdteChatstatusRes updteChatstatusRes = response.body();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Log.e("getonlineoffline() Resp", gson.toJson(updteChatstatusRes));
                        //  Toast.makeText(getApplicationContext(), updteChatstatusRes.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<UpdteChatstatusRes> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            Utils.dismissProgressDialog();

            //Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }


    private void getChatList() {
        if (Utils.isInternetConnected(this)) {
            // Utils.showProgressDialog(this);
            RestClient.getchathistory("get_chat_historys", channelID, userId, f_id, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //  Utils.dismissProgressDialog();

                        try {


                            GetChatHistoryResp getChatHistory = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("liveVideoId Resp", gson.toJson(getChatHistory));

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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<GetChatHistoryResp> call, Throwable t) {
                    //   Utils.dismissProgressDialog();

                }
            });


        } else {
            // Utils.dismissProgressDialog();

           // Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
    }

    private void setChatMessagesendText(String message11) {


        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), f_id);
        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), channelID);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);
        RequestBody faculty_id = RequestBody.create(MediaType.parse("text/plain"), f_id);


        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_messageText(channelId, userId12, message, faculty_id, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //   Utils.dismissProgressDialog();
                        try {
                            GetChatHistoryResp getChatHistory = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("f_idChat Resp", gson.toJson(getChatHistory));
                         /*   messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());

                            if (getChatHistory.getStatus().equalsIgnoreCase("1")) {

                                //   onsetdapter();
                                recyclerViewChat.setVisibility(View.VISIBLE);
                            } else {

                                recyclerViewChat.setVisibility(View.GONE);
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
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

        RequestBody userId12 = RequestBody.create(MediaType.parse("text/plain"), f_id);
        RequestBody channelId = RequestBody.create(MediaType.parse("text/plain"), channelID);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message11);
        RequestBody faculty_id = RequestBody.create(MediaType.parse("text/plain"), f_id);


        if (Utils.isInternetConnected(this)) {
            //  Utils.showProgressDialog(this);
            RestClient.send_chat_message(body, channelId, userId12, message, faculty_id, new Callback<GetChatHistoryResp>() {
                @Override
                public void onResponse(Call<GetChatHistoryResp> call, Response<GetChatHistoryResp> response) {
                    if (response.code() == 200) {
                        //   Utils.dismissProgressDialog();
                        try {
                            GetChatHistoryResp getChatHistory = response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("f_idChat Resp", gson.toJson(getChatHistory));
                            messageArrayList.clear();
                            messageArrayList.addAll(getChatHistory.getChat());

                            if (getChatHistory.getStatus().equalsIgnoreCase("1")) {

                                onsetdapter();
                                recyclerViewChat.setVisibility(View.VISIBLE);
                            } else {

                                recyclerViewChat.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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

           // Toast.makeText(this, "Connected Internet Connection!!!", Toast.LENGTH_SHORT).show();


        }
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


    @Override
    public void onResume() {
        super.onResume();
       /* Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                getChatList();
                handler.postDelayed(this, 5000);
            }
        };



        handler.postDelayed(r, 5000);*/
      /*  IntentFilter filter = new IntentFilter("com.dnamedical.Activities.FacultyChatActivity.class");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String value = intent.getExtras().getString("value");

                Log.e("Testfdsafasfs", "::" + value);

            }
        };
        registerReceiver(receiver, filter);*/


    }

    Handler handler = new Handler();

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stophandler(false);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

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
                            if (condition) {
                                setChatMessage("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Offline mode", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else if (data.getData() != null) {

                        Uri imgUri = data.getData();

                        getImageFilePath(imgUri);
                        if (condition) {
                            setChatMessage("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Offline mode", Toast.LENGTH_SHORT).show();
                        }
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
            if (condition) {
                setChatMessage("");
            } else {
                Toast.makeText(getApplicationContext(), "Offline mode", Toast.LENGTH_SHORT).show();
            }

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


        new FullScreenImagePopup(url, ctx, FacultyChatActivity.this).show();

    }


}
