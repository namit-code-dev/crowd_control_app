package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.frontpage.BuildConfig;
import com.example.frontpage.databinding.ActivityGenaiBinding;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class genai extends AppCompatActivity {

    ImageButton btn, btn1, btn2, btn3, btn5;
    private ActivityGenaiBinding binding;
    private OkHttpClient client = new OkHttpClient();

    String API_KEY ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ✅ Buttons init AFTER setContentView
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn5 = findViewById(R.id.btn5);

        // ✅ Button click listeners (yahi pe hone chahiye)
        btn.setOnClickListener(v ->
                startActivity(new Intent(genai.this, MainActivity.class))
        );

        btn1.setOnClickListener(v ->
                startActivity(new Intent(genai.this, prasadbooking.class))
        );

        btn5.setOnClickListener(v ->
                startActivity(new Intent(genai.this, emergency.class))
        );

        btn3.setOnClickListener(v ->
                startActivity(new Intent(genai.this, ticketviewadded.class))
        );

        btn2.setOnClickListener(v ->
                startActivity(new Intent(genai.this, analysis.class))
        );

        // ✅ Chat button
        binding.btnSend.setOnClickListener(v -> {
            String query = binding.userInput.getText().toString().trim();

            if (!query.isEmpty()) {
                addMessage("You: " + query);
                callAPI(query);
                binding.userInput.setText("");
            } else {
                Toast.makeText(this, "Enter message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMessage(String message) {
        binding.chatResponse.append("\n\n" + message);
    }

    private void callAPI(String message) {

        addMessage("AI is typing...");

        String json = "{\n" +
                "  \"model\": \"openai/gpt-oss-20b\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"user\", \"content\": \"" + message + "\"}\n" +
                "  ]\n" +
                "}";

        Request request = new Request.Builder()
                .url("https://api.groq.com/openai/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String res = response.body().string();
                Log.d("FULL_RESPONSE", res);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.has("choices")) {

                        String reply = jsonObject
                                .getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");

                        runOnUiThread(() -> addMessage("AI: " + reply));

                    } else if (jsonObject.has("error")) {

                        String errorMsg = jsonObject
                                .getJSONObject("error")
                                .getString("message");

                        runOnUiThread(() -> addMessage("Error: " + errorMsg));

                    }

                } catch (Exception e) {
                    runOnUiThread(() -> addMessage("Parsing Error"));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> addMessage("Connection Error"));
            }
        });
    }
}