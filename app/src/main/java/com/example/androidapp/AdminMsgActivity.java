package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.os.Looper;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.androidapp.ViewHolder.AdapterChat;
import com.example.androidapp.ViewHolder.Message;
import com.example.androidapp.imageupload.Chat;
import com.example.androidapp.imageupload.RetrofitUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidapp.Values.CHAT_DELAY;

public class AdminMsgActivity extends AppCompatActivity {

    private EditText typedMessage;
    private Button sendMessage;
    private ImageView localImage;
    private ListView messagesList;
    private String username;
    Chat chat;
    private Uri uri;
    AdapterChat adapterChat;
    private boolean hasUploadedPicture = false;
    private String imagePath;
    int IMAGE_CHOOSER_INTENT = 10001;
    List<Message> messages = new ArrayList<>();
    Timer timer = new Timer();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
    boolean IS_RUN= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_msg);
        Map config = new HashMap();
        config.put("cloud_name", "dr6akagrl");
        try {
            MediaManager.init(this, config);
        } catch (IllegalStateException e) {

        }

        typedMessage = findViewById(R.id.typed_message);
        sendMessage = findViewById(R.id.send);

        Button loadImage = findViewById(R.id.load_image);
        localImage = findViewById(R.id.local_image);
        messagesList = findViewById(R.id.messages);

        chat = RetrofitUtils.getRetrofit().create(Chat.class);


        if (TextUtils.isEmpty(typedMessage.getText())) {
            sendMessage.setEnabled(false);
        } else {
            sendMessage.setEnabled(true);
        }

        username = getIntent().getExtras().getString("username");
        System.out.println(username);


        loadAdminMessages("admin", username);

        IS_RUN = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                System.out.println("sss");
                loadAdminMessages("admin", username);
            }
        }, 0, CHAT_DELAY);

        adapterChat = new AdapterChat(this, messages);
        messagesList.setAdapter(adapterChat);


        typedMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s) && !hasUploadedPicture) {

                    sendMessage.setEnabled(false);
                } else {
                    sendMessage.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (hasUploadedPicture) {
                    //unsigned upload
                    String requestId = MediaManager.get()
                            .upload(uri)
                            .unsigned("myPreset")
                            .option("resource_type", "image")
                            .callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {
                                    makeToast("Uploading...");
                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {

                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    makeToast("Upload finished");

                                    imagePath = MediaManager.get().url().generate(resultData.get("public_id").toString().concat(".jpg"));

                                    Message message = new Message();
                                    message.setMessage("");
                                    message.setMessageTime("");
                                    message.setFrom(false);
                                    message.setMessageType("IMAGE");
                                    message.setImage(imagePath);
                                    message.setUser("admin");
                                    message.setToUser(username);
                                    messages.add(message);
                                    uploadToPusher(message);
                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {

                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    makeToast("An error occurred.\n" + error.getDescription());
                                }


                            }).dispatch();
                } else {
                    Message message = new Message();
                    message.setMessage(typedMessage.getText().toString());
                    message.setMessageTime(getFormatDate());
                    message.setFrom(false);
                    message.setMessageType("TEXT");
                    message.setUser("admin");
                    message.setToUser(username);
                    messages.add(message);
                    uploadToPusher(message);
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messagesList.setSelection(messagesList.getAdapter().getCount() - 1);
                    }
                });

//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)‌​;
                sendMessage.clearFocus();
                sendMessage.setFocusable(false);
                typedMessage.setText("");
//                AdapterAdminChat adapterAdminChat = new AdapterAdminChat(AdminMsgActivity.this,messages);
//                adapterAdminChat.add(message);

            }
        });


        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent chooseImage = new Intent();
                chooseImage.setType("image/*");
                chooseImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(chooseImage, "Select Picture"), IMAGE_CHOOSER_INTENT);

            }
        });


    }

    private void loadAdminMessages(String admin, String username) {

        chat.getAll(admin, username).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                int size = messages.size();
                int size1 = response.body().size();
                messages =response.body();
                if (adapterChat != null) {
                    adapterChat.add(messages);
                }

                if(size<size1){
                   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messagesList.setSelection(messagesList.getAdapter().getCount() - 1);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CHOOSER_INTENT && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                uri = data.getData();
                hasUploadedPicture = true;
                String localImagePath = getRealPathFromURI(uri);
                Bitmap bitmap;
                try {
                    InputStream stream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(stream);
                    localImage.setVisibility(View.VISIBLE);
                    localImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imagePath = MediaManager.get().url().generate(getFileName(uri));
                typedMessage.setText(localImagePath);
            }
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, projection, null, null, null);
            @SuppressWarnings("ConstantConditions")
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            //noinspection TryFinallyCanBeTryWithResources
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                //noinspection ConstantConditions
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void uploadToPusher(Message message) {
        localImage.setVisibility(View.GONE);
        hasUploadedPicture = false;

        chat.message(message).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


    }

    @Override
    public void onResume() {
        if(!IS_RUN){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    loadAdminMessages("admin", username);
                }
            }, 0, CHAT_DELAY);
        }

        super.onResume();
    }


    @Override
    protected void onStop() {
        IS_RUN = false;
        timer.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        IS_RUN = false;
        timer.cancel();
        super.onDestroy();
    }

    private String getFormatDate(){

        return simpleDateFormat.format(new Date());
    }
}