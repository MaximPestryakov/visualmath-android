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
        Page page = lecture.getMapping().get(position);
        if ("module".equals(page.getType())) {
            Module module = lecture.getModules().get(page.getIndex());
            return ModuleFragment.newInstance(module);
        }
        if ("question".equals(page.getType())) {
            Question question = lecture.getQuestions().get(page.getIndex());
            return QuestionFragment.newInstance("", question, true);
        }
        if ("questionBlock".equals(page.getType())) {
            String questionBlockId = lecture.getQuestionBlocks().get(page.getIndex());
            // return QuestionBlockFragment.newInstance(questionBlockId);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        if (lecture == null || lecture.getMapping() == null) {
            return 0;
        }
        return lecture.getMapping().size();
    }

    void setLecture(Lecture lecture) {
        this.lecture = lecture;
        notifyDataSetChanged();
    }
}
