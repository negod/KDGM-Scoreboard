/*
 */
package se.backede.scoreboard.admin.commons;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <T>
 */
@Getter
@Setter
public abstract class PickListController<T extends GenericDto> {

    private List<T> allItems;
    private DualListModel<T> items;

    private GenericRestClient<T> restClient;

    public void setupController(GenericRestClient<T> restClient) {
        this.restClient = restClient;

        getRestClient().getAll().ifPresent(allItems -> {
            setAllItems(allItems);
        });

        List<T> targetList = new ArrayList<>();

        items = new DualListModel<>(allItems, targetList);

    }

    public abstract void init();

    public List<T> getSelectedItems() {
        return items.getTarget();
    }

    public abstract void onTransfer(TransferEvent event);

    public abstract void onSelect(SelectEvent<T> event);

    public abstract void onUnselect(UnselectEvent<T> event);

    public abstract void onReorder();

}
