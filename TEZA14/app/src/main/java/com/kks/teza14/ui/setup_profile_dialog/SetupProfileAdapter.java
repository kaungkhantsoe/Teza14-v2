package com.kks.teza14.ui.setup_profile_dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseAdapter;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.MemberModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Binds;

import static com.kks.teza14.util.AppConstants.memberPhotoArray;

/**
 * Created by kaungkhantsoe on 03/06/2020.
 **/
public class SetupProfileAdapter extends BaseAdapter {

    private RequestManager requestManager;


    public SetupProfileAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindPost((MemberModel) getItemsList().get(position), position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        MyanTextView tvName;

        @BindView(R.id.tv_cadet)
        MyanTextView tvCadet;

        @BindView(R.id.tv_commission)
        MyanTextView tvCommission;

        @BindView(R.id.tv_own)
        MyanTextView tvOwn;

        @BindView(R.id.iv_user)
        ImageView ivUser;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();

        }

        public void bindPost(MemberModel model, int position) {

            tvName.setMyanmarText(model.getName());
            tvCadet.setMyanmarText(model.getCadetNumber());
            tvCommission.setMyanmarText(model.getCommissionNumber());
            tvOwn.setMyanmarText(model.getOwnNumber());

            if (memberPhotoArray.get(position) != null)
                requestManager.load(memberPhotoArray.get(position))
                        .into(ivUser);
            else
                requestManager.load(R.drawable.placeholder)
                        .into(ivUser);

            resizeItem();

        }

        private void resizeItem() {
            //resize item view of brand list
            Display display = ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            try {
                display.getRealSize(size);
            } catch (NoSuchMethodError err) {
                display.getSize(size);
            }
            int width = size.x;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(3 * (width / 4),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            int marginSmall = Math.round(context.getResources().getDimension(R.dimen.margin_small));
            int marginNormal = Math.round(context.getResources().getDimension(R.dimen.margin_normal));

//            lp.setMargins(marginNormal, marginSmall, marginNormal, marginSmall);
            itemView.setLayoutParams(lp);

        }

    }
}
