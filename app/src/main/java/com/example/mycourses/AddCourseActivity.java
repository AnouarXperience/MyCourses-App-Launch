package com.example.mycourses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private TextInputEditText courseNameEdt,coursePriceEdt,courseSuitedForEdt,courseImgEdt,courseLinkEdt,courseDescEdt;
    private Button addCourseBtn;
    private ProgressBar LoadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        courseSuitedForEdt = findViewById(R.id.idEdtCourseSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        courseDescEdt = findViewById(R.id.idEdtCourseDesc);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        LoadingPB =findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("MyCourses");

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingPB.setVisibility(View.VISIBLE);
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String SuitedFor = courseSuitedForEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                String courseDes = courseDescEdt.getText().toString();
                courseID = courseName;
                CourseRVModal courseRVModal = new CourseRVModal(courseName,courseDes,coursePrice,SuitedFor,courseImg,courseLink,courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LoadingPB.setVisibility(View.GONE);
                        databaseReference.child(courseID).setValue(courseRVModal);
                        Toast.makeText(AddCourseActivity.this, "Course Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourseActivity.this,MainActivity.class));
                        
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCourseActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





    }
}