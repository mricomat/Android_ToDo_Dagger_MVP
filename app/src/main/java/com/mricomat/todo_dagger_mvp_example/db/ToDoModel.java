package com.mricomat.todo_dagger_mvp_example.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by mricomat on 08/03/2018.
 */

@Entity(tableName = "ToDo")
public class ToDoModel implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "text")
    private String mToDoText;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "color")
    private int mColor;

    @ColumnInfo(name = "reminder")
    private boolean mHasReminder;

    public ToDoModel() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String toDoText) {
        mToDoText = toDoText;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public boolean isHasReminder() {
        return mHasReminder;
    }

    public void setHasReminder(boolean hasReminder) {
        this.mHasReminder = hasReminder;
    }

    protected ToDoModel(Parcel in) {
        mId = in.readString();
        mToDoText = in.readString();
        mDate = in.readString();
        mColor = in.readInt();
        mHasReminder = in.readByte() != 0;
    }

    public static final Creator<ToDoModel> CREATOR = new Creator<ToDoModel>() {
        @Override
        public ToDoModel createFromParcel(Parcel in) {
            return new ToDoModel(in);
        }

        @Override
        public ToDoModel[] newArray(int size) {
            return new ToDoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mToDoText);
        parcel.writeString(mDate);
        parcel.writeInt(mColor);
        parcel.writeByte((byte) (mHasReminder ? 1 : 0));
    }
}