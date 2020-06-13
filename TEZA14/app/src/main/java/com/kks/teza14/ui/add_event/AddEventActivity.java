package com.kks.teza14.ui.add_event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.custom_control.MyanEditText;
import com.kks.teza14.custom_control.MyanTextView;

import com.kks.teza14.events.AppEvents;
import com.kks.teza14.models.PostModel;
import com.kks.teza14.util.SharePreferenceHelper;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_POSTS;

/**
 * Created by kaungkhantsoe on 04/06/2020.
 **/
public class AddEventActivity extends BaseActivity {

    @BindView(R.id.tv_post)
    MyanTextView tvPost;

    @BindView(R.id.et_title)
    MyanEditText etTitle;

    @BindView(R.id.et_body)
    MyanEditText etBody;

    @Inject
    @Named(NAME_POSTS)
    DatabaseReference ref;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    private PostModel postModel;

    public static final String IE_POST = "IE_POST";

    public static Intent getAddEventIntent(Context context, PostModel postModel) {
        Intent intent = new Intent(context, AddEventActivity.class);
        intent.putExtra(IE_POST, postModel);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_event;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setupToolbarColored(true);
        setupToolbarText("Add Event");

        init();
    }

    private void init() {

        postModel = (PostModel) getIntent().getSerializableExtra(IE_POST);

        if (postModel.getId() != null)
            restoreData();

        tvPost.setOnClickListener(v -> {
            checkValidation();
        });
    }

    private void restoreData() {
        etBody.setMyanmarText(postModel.getMsg());
        etTitle.setMyanmarText(postModel.getTitle());
    }

    private void checkValidation() {

        if (etBody.getText() != null)
            if (etBody.getText().length() == 0) {
                etBody.setError(getString(R.string.body_cannot_be_empty));
            }

        if (etTitle.getText() != null)
            if (etTitle.getText().length() == 0) {
                etTitle.setError(getString(R.string.title_cannot_be_empty));
            }

        if (etTitle.getText() != null && etBody.getText() != null)
            if (etTitle.getText().length() != 0 && etBody.getText().length() != 0) {

                String postId;

                if (postModel.getId() == null) {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    postId = "Post_" + timeStamp + "_";
                } else {
                    postId = postModel.getId();
                }

                PostModel postModel = new PostModel(postId,
                        etTitle.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                        etBody.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                        sharePreferenceHelper.getUserProfileUrl(),
                        sharePreferenceHelper.getUserName());

                if (postModel.getId() == null)
                    ref.child(postId).setValue(postModel);
                else
                    ref.child(postId).updateChildren(postModel.toMap());

                showToastMsg(getString(R.string.posted));

                finish();

            }
    }
}
