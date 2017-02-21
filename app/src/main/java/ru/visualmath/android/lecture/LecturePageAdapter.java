package ru.visualmath.android.lecture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Lecture;

public class LecturePageAdapter extends FragmentStatePagerAdapter {

    private Lecture lecture;

    public LecturePageAdapter(FragmentManager fm, Lecture lecture) {
        super(fm);
        Log.d("MyTag", "LecturePageAdapter()");
        this.lecture = lecture;
    }

    @Override
    public Fragment getItem(int position) {
        Lecture.Page page = lecture.mapping.get(position);
        if ("module".equals(page.type)) {
            Lecture.Module module = lecture.modules.get(page.index);
            return LectureModuleFragment.newInstance(module.name, module.content, module.images);
        }
        if ("question".equals(page.type)) {
            Lecture.Question question = lecture.questions.get(page.index);
            return LectureQuestionFragment.newInstance(question.question, question.answers, question.multiple);
        }
        if ("questionBlock".equals(page.type)) {
            String questionBlockId = lecture.questionBlocks.get(page.index);
            return LectureQuestionBlockFragment.newInstance(questionBlockId);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return lecture.mapping.size();
    }


    public static class LecturePageFragment extends Fragment {
        @BindView(R.id.lecture_text)
        TextView lectureText;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.lecture_page, container, false);
            ButterKnife.bind(this, rootView);

            String type = getArguments().getString("type");
            lectureText.setText(type);

            return rootView;
        }
    }
}
