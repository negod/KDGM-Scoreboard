/*
 */
package se.backede.scoreboard.admin.controller.helper;

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

    public IndexHelper(int maxIndex, int minIndex) {
        this.maxIndex = maxIndex;
        this.minIndex = minIndex;
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
