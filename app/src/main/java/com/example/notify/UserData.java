package com.example.notify;

import com.google.firebase.database.ServerValue;

public class UserData {

    private String userKey;
    private boolean isTeacher;
    private String userEmail;
    private String userName;
    private String userPhoto;
    private String faculty;
    private String department;
    private String designation;
    private String section;
    private String batch;

    private Object timeStamp ;


    public UserData(String userEmail, String userName, boolean isTeacher,String designation,String faculty,String department,String batch, String section) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.isTeacher = isTeacher;
        this.designation = designation;
        this.faculty = faculty;
        this.department = department;
        this.batch = batch;
        this.section = section;

        this.timeStamp = ServerValue.TIMESTAMP;
    }

    // make sure to have an empty constructor inside ur model class
    public UserData() {
    }


    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserName() {
        return userName;
    }

    private boolean getIsTeacher()
    {
        return isTeacher;
    }
    public String getDesignation() {
        return designation;
    }
    public String getUserEmail()
    {
        return userEmail;
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

    public String getUserPhoto() {
        return userPhoto;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

}
