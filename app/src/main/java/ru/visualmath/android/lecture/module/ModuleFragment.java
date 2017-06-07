package ru.visualmath.android.lecture.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.maximpestryakov.katexview.KatexView;
import ru.visualmath.android.R;
import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.model.Module;

public class ModuleFragment extends Fragment {

    public static final String TAG = "ModuleFragment";

    private static final String ARGUMENT_MODULE = "ARGUMENT_MODULE";
    @BindView(R.id.lecture_module_name)
    KatexView nameTextView;
    @BindView(R.id.lecture_module_content)
    KatexView contentTextView;
    @BindView(R.id.image)
    ImageView image;
    private Unbinder unbinder;
    private Module module;

    public static ModuleFragment newInstance(Module module) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_MODULE, module);

        ModuleFragment fragment = new ModuleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            module = (Module) args.getSerializable(ARGUMENT_MODULE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_module, container, false);
        unbinder = ButterKnife.bind(this, view);

        nameTextView.setText(module.getName());
        contentTextView.setText(module.getContent());
        if (module.getImages() != null && !module.getImages().isEmpty()) {
            image.setVisibility(View.VISIBLE);
            String url = "http://visualmath.ru" + module.getImages().get(0);
            VisualMathApi.getPicasso().load(url).into(image);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
