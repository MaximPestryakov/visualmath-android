package ru.visualmath.android.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;

public class MessageFragment extends Fragment {

    public static final String TAG = "ru.visualmath.android.message.MessageFragment";

    private static final String ARGUMENT_MESSAGE_ID = "ARGUMENT_MESSAGE_ID";
    @BindView(R.id.message)
    TextView message;
    @StringRes
    private int messageId;

    public static MessageFragment newInstance(@StringRes int messageId) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_MESSAGE_ID, messageId);

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            messageId = args.getInt(ARGUMENT_MESSAGE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        message.setText(messageId);
    }
}
