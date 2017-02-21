package ru.visualmath.android.lectureboard;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.List;

import ru.visualmath.android.api.model.Lecture;

public class LectureBoardViewState implements ViewState<LectureBoardView> {

    public enum LectureState {
        SHOW_LOADING, SHOW_LECTURE_LIST, SHOW_ERROR, LOGOUT
    }

    private LectureState state;
    private Object data;

    @Override
    public void apply(LectureBoardView view, boolean retained) {
        switch (state) {
            case SHOW_LOADING:
                view.showLoading();
                break;

            case SHOW_LECTURE_LIST:
                view.showLectureList((List<Lecture>) data);
                break;

            case SHOW_ERROR:
                view.showError((String) data);
                break;

            case LOGOUT:
                view.logout();
                break;
        }
    }

    public void setState(LectureState state) {
        this.state = state;
    }

    public void setState(LectureState state, Object data) {
        this.state = state;
        this.data = data;
    }
}
