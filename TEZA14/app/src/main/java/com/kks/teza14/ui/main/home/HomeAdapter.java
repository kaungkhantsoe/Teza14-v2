package com.kks.teza14.ui.main.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseAdapter;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.ui.member_detail.MemberDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kks.teza14.util.AppConstants.memberPhotoArray;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class HomeAdapter extends BaseAdapter {

    private RequestManager requestManager;

    public HomeAdapter(RequestManager requestManager) {
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

            itemView.setOnClickListener(v -> context.startActivity(MemberDetailActivity.getMemberDetailActivity(context,position)));

        }
    }
}
