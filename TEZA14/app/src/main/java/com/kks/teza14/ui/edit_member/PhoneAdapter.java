package com.kks.teza14.ui.edit_member;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kks.teza14.R;
import com.kks.teza14.common.BaseAdapter;
import com.kks.teza14.custom_control.MyanEditText;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.DummyPhoneModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/
public class PhoneAdapter extends BaseAdapter {

    public static final int VIEW_PHONE = 10;
    public static final int VIEW_ADD_PHONE = 20;

    private EditMemberDelegate delegate;

    public PhoneAdapter(EditMemberDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_PHONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_phone, parent, false);
            return new AddPhoneViewHolder(view);
        }
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_PHONE)
            ((ViewHolder) holder).bindPost((DummyPhoneModel) getItemsList().get(position), position);

        else if (getItemViewType(position) == VIEW_ADD_PHONE)
            ((AddPhoneViewHolder) holder).bindPost((DummyPhoneModel) getItemsList().get(position), position);

    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (((DummyPhoneModel) getItemsList().get(position)).getPhoneNumber() != null) {
            return VIEW_PHONE;
        } else if ((((DummyPhoneModel) getItemsList().get(position)).getPhoneNumber() == null)) {
            return VIEW_ADD_PHONE;
        }
        return super.getItemViewType(position);
    }

    class AddPhoneViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "AddPhoneViewHolder";

        @BindView(R.id.cv_delete)
        CardView cvDelete;

        @BindView(R.id.et_phone)
        MyanEditText etPhone;

        @BindView(R.id.ll_add)
        LinearLayout llAdd;

        private Context context;

        public AddPhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindPost(DummyPhoneModel model, int position) {

            cvDelete.setOnClickListener(v -> delegate.onDeletePhone(position));
            llAdd.setOnClickListener(v -> {

                if (etPhone.getText().length() == 0){
                    Toast.makeText(context,"Number cannot be empty",Toast.LENGTH_SHORT).show();
                } else {
                    if (isValidNumber(etPhone.getMyanmarText())) {
                        delegate.onAddPhone(etPhone.getMyanmarTextFor(MyanEditText.MyanType.ZG));
                    } else {
                        Toast.makeText(context,"Only english number is acceptable",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private boolean isValidNumber(String phone) {
            return phone.matches("[0-9]+");
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_remove)
        LinearLayout llRemove;

        @BindView(R.id.tv_phone)
        MyanTextView tvPhone;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindPost(DummyPhoneModel model, int position) {

            tvPhone.setMyanmarText(model.getPhoneNumber());

            llRemove.setOnClickListener(v -> delegate.onDeletePhone(position));
        }
    }
}
