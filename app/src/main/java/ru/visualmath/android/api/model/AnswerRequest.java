package ru.visualmath.android.api.model;

import java.util.List;

public class AnswerRequest {

    private Stats stats;

    public Stats getStats() {
        return stats;
    }

    class Stats {

        private List<Integer> currentUserAnswer;

        private boolean didCurrentUserAnswer;

        public List<Integer> getCurrentUserAnswer() {
            return currentUserAnswer;
        }

        public boolean isDidCurrentUserAnswer() {
            return didCurrentUserAnswer;
        }
    }
}
