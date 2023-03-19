package com.example.resumecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewResume extends AppCompatActivity {

    WebView webView;
    Profile profile;
    String resume_id ;
    int template;
    ImageView download_cv;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resume);

        download_cv = findViewById(R.id.download_cv);
        webView = findViewById(R.id.preview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        resume_id = getIntent().getStringExtra("resume_id");
        template = Integer.parseInt(getIntent().getStringExtra("template"));

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile = snapshot.getValue(Profile.class);
                String HtmlContent = Templates.build(template+1 , profile);
                webView.loadDataWithBaseURL(null , HtmlContent , "text/html" , "utf-8" , null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
        download_cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                String jobName = getString(R.string.app_name) + " Document";
                // Get a print adapter instance
                PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
                // Create a print job with name and adapter instance
                PrintAttributes.Builder builder = new PrintAttributes.Builder();
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
                PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());
                // Save the job object for later status checking
//        printJobs.add(printJob);
            }
        });

    }
}