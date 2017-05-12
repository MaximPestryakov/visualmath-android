package ru.visualmath.android.lecture;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.Module;
import ru.visualmath.android.api.model.Page;
import ru.visualmath.android.api.model.Question;
import ru.visualmath.android.lecture.module.ModuleFragment;
import ru.visualmath.android.lecture.question.QuestionFragment;

class LecturePageAdapter extends FragmentStatePagerAdapter {

    private Lecture lecture;

    LecturePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Page page = lecture.mapping.get(position);
        if ("module".equals(page.type)) {
            Module module = lecture.modules.get(page.index);
            return ModuleFragment.newInstance(module.getName(), module.getContent());
        }
        if ("question".equals(page.type)) {
            Question question = lecture.questions.get(page.index);
            return QuestionFragment.newInstance("", question, true);
        }
        if ("questionBlock".equals(page.type)) {
            String questionBlockId = lecture.questionBlocks.get(page.index);
            // return QuestionBlockFragment.newInstance(questionBlockId);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        if (lecture == null || lecture.mapping == null) {
            return 0;
        }
        return lecture.mapping.size();
    }

    void setLecture(Lecture lecture) {
        this.lecture = lecture;
        notifyDataSetChanged();
    }
}
