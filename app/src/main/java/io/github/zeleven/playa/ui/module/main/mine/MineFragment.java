package io.github.zeleven.playa.ui.module.main.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.zeleven.playa.R;
import io.github.zeleven.playa.data.model.User;
import io.github.zeleven.playa.ui.base.BaseFragment;
import io.github.zeleven.playa.ui.module.account.AccountActivity;
import io.github.zeleven.playa.ui.module.settings.SettingsActivity;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.login_card) CardView loginCard;
    @BindView(R.id.user_info_card) CardView userInfoCard;
    @BindView(R.id.username) TextView username;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentComponent.inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onFragmentViewCreated() {
        super.onFragmentViewCreated();
        if (presenter == null) {
            return;
        }
        presenter.attachView(this);
        setLoggedInUserInfo();
    }

    @OnClick(R.id.login_item)
    public void openLoginScreen() {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @OnClick(R.id.feedback_item)
    public void sendFeedbackEmail() {

    }

    @OnClick(R.id.settings_item)
    public void openSettingsScreen() {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    public void setLoggedInUserInfo() {
        if (presenter.isLogin()) {
            loginCard.setVisibility(View.GONE);
            userInfoCard.setVisibility(View.VISIBLE);
            User user = presenter.getLoggedInUser();
            username.setText(user.getUsername());
        } else {
            loginCard.setVisibility(View.VISIBLE);
            userInfoCard.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.user_info_card)
    public void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.logout_alert_message);
        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.logout();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
