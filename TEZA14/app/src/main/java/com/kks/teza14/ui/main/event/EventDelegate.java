package com.kks.teza14.ui.main.event;

import com.kks.teza14.models.PostModel;

/**
 * Created by kaungkhantsoe on 04/06/2020.
 **/
public interface EventDelegate {

    void onDeletePost(int position, String postId);
    void onEditPost(int position, PostModel postModel);
}
