package com.kks.teza14.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.kks.teza14.common.Pageable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by kaungkhantsoe on 03/06/2020.
 **/

@IgnoreExtraProperties
public class PostModel implements Serializable, Pageable {

    public String id;
    public String title;
    public String msg;
    public String profileUrl;
    public String editor;

    public PostModel() {
    }

    public PostModel(String id, String title, String msg, String profileUrl, String editor) {
        this.id = id;
        this.title = title;
        this.msg = msg;
        this.profileUrl = profileUrl;
        this.editor = editor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("msg", msg);
        result.put("profileUrl", profileUrl);
        result.put("editor", editor);
        return result;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
        if (!(o instanceof PostModel)) return false;
        PostModel postModel = (PostModel) o;
        return id.equals(postModel.id) &&
                Objects.equals(title, postModel.title) &&
                Objects.equals(msg, postModel.msg) &&
                Objects.equals(profileUrl, postModel.profileUrl) &&
                Objects.equals(editor, postModel.editor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, msg, profileUrl, editor);
    }
}
