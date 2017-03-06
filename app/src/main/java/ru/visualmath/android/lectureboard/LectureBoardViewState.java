package ru.visualmath.android.lectureboard;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.List;

import ru.visualmath.android.api.model.Lecture;

class LectureBoardViewState implements ViewState<LectureBoardView> {

    private LectureState state;
    private Object data;

    @Override
    public void apply(LectureBoardView view, boolean retained) {
        switch (state) {
            case SHOW_LOADING:
                view.showLoading();
                break;

            case SHOW_LECTURE_LIST:
                view.showLectureList(null, (List<Lecture>) data);
                break;

            case SHOW_ERROR:
                view.showError((String) data);
                break;

            case LOGOUT:
                view.logout();
                break;
        }
    }

    void setState(LectureState state) {
        this.state = state;
    }

    void setState(LectureState state, Object data) {
        this.state = state;
        this.data = data;
    }

    enum LectureState {
        SHOW_LOADING, SHOW_LECTURE_LIST, SHOW_ERROR, LOGOUT
    }
}