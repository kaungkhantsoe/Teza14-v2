package com.kks.teza14.ui.edit_member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.custom_control.MyanEditText;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.DummyPhoneModel;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_MEMBER;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/
public class EditMemberActivity extends BaseActivity implements EditMemberDelegate {

    private static final String TAG = "EditMemberActivity";

    @BindView(R.id.et_address)
    MyanEditText etAddress;

    @BindView(R.id.et_job)
    MyanEditText etJob;

    @BindView(R.id.et_kids)
    MyanEditText etKids;

    @BindView(R.id.et_note)
    MyanEditText etNote;

    @BindView(R.id.et_spouse)
    MyanEditText etSpouse;

    @BindView(R.id.tv_save)
    MyanTextView tvSave;

    @BindView(R.id.rv_phone)
    RecyclerView rvPhone;

    @BindView(R.id.iv_add_phone)
    ImageView ivAddPhone;

    @Inject
    ViewModelProviderFactory providerFactory;

    private PhoneAdapter adapter;

    private EditMemberViewModel viewModel;

    private static final String IE_MODEL = "model";
    private static final String IE_POSITION = "position";

    private boolean isAdding = false;

    @Inject
    @Named(NAME_MEMBER)
    DatabaseReference userRef;

    public static Intent getEditMemberIntent(Context context, MemberModel memberModel, int position) {
        Intent intent = new Intent(context, EditMemberActivity.class);
        intent.putExtra(IE_MODEL, memberModel);
        intent.putExtra(IE_POSITION, position);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_member;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbarColored(true);
        setupToolbarText(getString(R.string.edit_member));

        init();
    }

    private void init() {

        viewModel = new ViewModelProvider(this, providerFactory).get(EditMemberViewModel.class);

//        userRef.keepSynced(true);

        viewModel.setMemberModel((MemberModel) getIntent().getSerializableExtra(IE_MODEL));
        viewModel.setPosition(getIntent().getIntExtra(IE_POSITION, 0));

        tvSave.setOnClickListener(v -> saveData());
        ivAddPhone.setOnClickListener(v -> addAnotherPhone());

        setupRecycler();
        setupData(viewModel.getMemberModel());

    }

    private void addAnotherPhone() {
        if (!isAdding) {
            isAdding = true;
            adapter.add(new DummyPhoneModel(null));
            refreshRecycler();
        }
    }

    private void setupRecycler() {
        adapter = new PhoneAdapter(this);

        rvPhone.setHasFixedSize(true);
        rvPhone.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvPhone.setAdapter(adapter);
    }

    private void setupData(MemberModel model) {

        etAddress.setMyanmarText(model.getAddress());
        etJob.setMyanmarText(model.getJob());
        etKids.setMyanmarText(model.getFamily());
        etNote.setMyanmarText(model.getNote());
        etSpouse.setMyanmarText(model.getSpouseName());

        for (String number : model.getPhone().split(",")) {
            adapter.add(new DummyPhoneModel(number.trim()));
        }

        refreshRecycler();
    }

    private void refreshRecycler() {
        rvPhone.setVisibility(View.GONE);
        rvPhone.setVisibility(View.VISIBLE);
    }

    private void saveData() {

        if (isAdding) {
            isAdding = false;
            adapter.remove(adapter.getItemCount()-1);
        }

        StringBuilder phoneBuilder = new StringBuilder();

        if (adapter.getItemCount() > 0) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (i == adapter.getItemCount() - 1)
                    phoneBuilder.append(((DummyPhoneModel) adapter.getItemsList().get(i)).getPhoneNumber().trim());
                else
                    phoneBuilder.append(((DummyPhoneModel) adapter.getItemsList().get(i)).getPhoneNumber().trim() + ", ");
            }
        }

        MemberModel memberModel = new MemberModel(
                viewModel.getMemberModel().getId(),
                viewModel.getMemberModel().getName(),
                viewModel.getMemberModel().getCommissionNumber(),
                viewModel.getMemberModel().getOwnNumber(),
                viewModel.getMemberModel().getCadetNumber(),
                etSpouse.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                etKids.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                etAddress.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                etJob.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                phoneBuilder.toString(),
                etNote.getMyanmarTextFor(MyanEditText.MyanType.ZG),
                viewModel.getMemberModel().isDeceased(),
                viewModel.getMemberModel().getFamilyUrl(),
                viewModel.getMemberModel().userUrl
        );

        /*
        ref to update
         */
        DatabaseReference updateRef = userRef.child(String.valueOf(1721 + viewModel.getPosition()));

        Map<String, Object> postValues = memberModel.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("address",memberModel.getAddress());
        childUpdates.put("cadetNumber",memberModel.getCadetNumber());
        childUpdates.put("commissionNumber",memberModel.getCommissionNumber());
        childUpdates.put("deceased",memberModel.isDeceased());
        childUpdates.put("family",memberModel.getFamily());
        childUpdates.put("familyUrl",memberModel.getFamilyUrl());
        childUpdates.put("id",memberModel.getId());
        childUpdates.put("job",memberModel.getJob());
        childUpdates.put("name",memberModel.getName());
        childUpdates.put("note",memberModel.getNote());
        childUpdates.put("ownNumber",memberModel.getOwnNumber());
        childUpdates.put("phone",memberModel.getPhone());
        childUpdates.put("spouseName",memberModel.getSpouseName());
        childUpdates.put("userUrl",memberModel.getUserUrl());

        updateRef.updateChildren(childUpdates);

        Toast.makeText(EditMemberActivity.this,"Updated",Toast.LENGTH_SHORT);

        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onDeletePhone(int position) {
        adapter.remove(position);
        isAdding = false;
        refreshRecycler();
    }

    @Override
    public void onAddPhone(String phoneNumber) {
        isAdding = false;
        adapter.remove(adapter.getItemCount()-1);

        adapter.add(new DummyPhoneModel(phoneNumber));
        refreshRecycler();
    }
}
