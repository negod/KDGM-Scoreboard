/*
 */
package se.backede.scoreboard.view;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("indexController")
@ApplicationScoped
public class IndexController {

    private boolean showPlayer = false;

    public void togglePlayer() {
        if (showPlayer) {
            showPlayer = false;
        } else {
            showPlayer = true;
        }
    }
    

}
