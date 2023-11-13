package models;

import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Users {
    String name, lop, quequan, avatar;
    long mssv;
    public Users(){

    }
    public Users(String name, String lop, String quequan, String avatar, int mssv) {
        this.name = name;
        this.lop = lop;
        this.quequan = quequan;
        this.avatar = avatar;
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getMssv() {
        return mssv;
    }

    public void setMssv(long mssv) {
        this.mssv = mssv;
    }
}
