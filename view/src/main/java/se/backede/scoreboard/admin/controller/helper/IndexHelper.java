/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class IndexHelper {

    private int activeIndex = 0;
    private int maxIndex = 0;
    private int minIndex = 0;

    Map<Integer, String> indexGameId = new HashMap<>();

    public IndexHelper(int maxIndex, int minIndex, Map<Integer, String> indexGameId) {
        this.indexGameId = indexGameId;
        this.maxIndex = maxIndex;
        this.minIndex = minIndex;
    }

    public String activeIndexGameId() {
        return indexGameId.get(getActiveIndex());
    }

    public void nextStep() {
        if (activeIndex < maxIndex) {
            activeIndex++;
        }
    }

    public void prevStep() {
        if (activeIndex > minIndex) {
            activeIndex--;
        }
    }

    public boolean disabled() {
        return activeIndex == maxIndex;
    }
}
