package com.kks.teza14.ui.main.event;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseAdapter;
import com.kks.teza14.custom_control.MyanBoldTextView;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.PostModel;
import com.kks.teza14.util.MyDateFormat;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kaungkhantsoe on 03/06/2020.
 **/
public class EventAdapter extends BaseAdapter {

    private RequestManager requestManager;
    private EventDelegate delegate;

    public EventAdapter(RequestManager requestManager, EventDelegate delegate) {
        this.requestManager = requestManager;
        this.delegate = delegate;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindPost((PostModel) getItemsList().get(position), position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_msg)
        MyanTextView tvMsg;

        @BindView(R.id.tv_name)
        MyanTextView tvName;

        @BindView(R.id.tv_time)
        MyanTextView tvTime;

        @BindView(R.id.tv_title)
        MyanBoldTextView tvTitle;

        @BindView(R.id.iv_profile)
        ImageView ivProfile;

        @BindView(R.id.iv_more)
        ImageView ivMore;

        private MyDateFormat myDateFormat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            myDateFormat = new MyDateFormat();

        }

        public void bindPost(PostModel model, int position) {
            tvMsg.setMyanmarText(model.getMsg());

            tvTitle.setMyanmarText(model.getTitle());

            tvName.setMyanmarText(model.getEditor());

            String time = getStringBetween(model.getId(), "Post_", "_");

            try {
                tvTime.setText(myDateFormat.DATE_POST_TO_SHOW.format(
                        myDateFormat.DATE_POST.parse(time)
                ));

            } catch (ParseException e) {
                e.printStackTrace();
                tvTime.setText(model.getId());
            }

            ivMore.setOnClickListener(v -> showPopupMenu(ivMore, position, model));

            requestManager.load(model.getProfileUrl())
                    .into(ivProfile);
        }

        private void showPopupMenu(View view, int position, PostModel postModel) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.post_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener(view.getContext(), position, postModel));
            popup.show();
        }

        public String getStringBetween(String original, String from, String to) {

            int pFrom = original.indexOf(from) + from.length();
            int pTo = to.indexOf(to);

            return original.substring(pFrom, original.length() - 2);
        }
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private static final String TAG = "MyMenuItemClickListener";

        private int position;
        private PostModel postModel;
        private Context context;

        public MyMenuItemClickListener(Context context, int positon, PostModel postModel) {
            this.position = positon;
            this.postModel = postModel;
            this.context = context;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.menu_delete:
                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure you want to delete this post?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delegate.onDeletePost(position, postModel.getId());
                                }
                            })
                            .setNegativeButton("Cancel", null).show();

                    return true;
                case R.id.menu_edit:
                    delegate.onEditPost(position, postModel);
                    return true;
                default:
            }
            return false;
        }
    }
}
