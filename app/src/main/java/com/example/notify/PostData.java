package com.example.notify;

import com.google.firebase.database.ServerValue;

public class PostData {

    private String postKey;
    private String title;
    private String description;
    private String picture;
    private String userId;
    private String userName;
    private String userPhoto;
    private String faculty;
    private String department;
    private String section;
    private String batch;
    private String subject;
    private String deadlineDay;
    private String deadlineMonth;
    private String deadlineYear;
    private String deadlineHour;
    private String deadlineMinute;


    private Object timeStamp ;


    public PostData(String description, String picture, String userName, String userPhoto,String subject, String day,String month,String year,String hour,String minute, String faculty,String department,String batch, String section) {
        String post;
        //this.title = title;
        this.description = description;
        this.picture = picture;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.faculty = faculty;
        this.department = department;
        this.batch = batch;
        this.section = section;
        this.deadlineDay = day;
        this.deadlineMonth = month;
        this.deadlineYear = year;
        this.deadlineHour = hour;
        this.deadlineMinute = minute;
        this.subject = subject;

        this.timeStamp = ServerValue.TIMESTAMP;
    }

    // make sure to have an empty constructor inside ur model class
    public PostData() {
    }


    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDeadlineDay() {
        return deadlineDay;
    }
    public String getDeadlineMonth() {
        return deadlineMonth;
    }
    public String getDeadlineYear() {
        return deadlineYear;
    }
    public String getDeadlineHour() {
        return deadlineHour;
    }
    public String getDeadlineMinute() {
        return deadlineMinute;
    }
    public String getSubject() {
        return subject;
    }
    public String getFaculty() {
        return faculty;
    }
    public String getDepartment() {
        return department;
    }
    public String getBatch() {
        return batch;
    }
    public String getSection() {
        return section;
    }

    public void setDay(String day) {
        this.deadlineDay = day;
    }




    public String getUserPhoto() {
        return userPhoto;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

}
