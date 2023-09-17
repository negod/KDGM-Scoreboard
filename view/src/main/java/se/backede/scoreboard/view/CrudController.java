/*
 */
package se.backede.scoreboard.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import se.backede.scoreboard.view.commons.GenericDto;
import se.backede.scoreboard.view.commons.GenericRestClient;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <T>
 */
@Getter
@Setter
public abstract class CrudController<T extends GenericDto> {

    private T selectedItem;
    private List<T> allItems;
    private List<T> selectedItems;

    private GenericRestClient<T> restClient;

    public abstract void init();

    public void setupController(GenericRestClient restClient) {
        this.restClient = restClient;

        getRestClient().getAll().ifPresent(allGAmes -> {
            setAllItems(allGAmes);
        });

    }

    public String getDeleteButtonMessage() {
        if (hasSelectedItems()) {
            int size = getSelectedItems().size();
            return size > 1 ? size + " item selected" : "1 item selected";
        }

        return "Delete";
    }

    public boolean hasSelectedItems() {
        return getSelectedItems() != null && !getSelectedItems().isEmpty();
    }

    /**
     * Craete new instance of the selected Item
     */
    public abstract void openNew();

    private void createItem() {

        Optional<T> createdGame = getRestClient().create(getSelectedItem());

        if (createdGame.isPresent()) {
            this.getAllItems().add(createdGame.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Added"));
        } else {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("failed to add item"));
        }

    }

    private void updateItem() {
        Optional<T> updatedGame = getRestClient().update(getSelectedItem());

        if (updatedGame.isPresent()) {
            getAllItems().remove(getSelectedItem());
            getAllItems().add(updatedGame.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated Item"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-items");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to Update Game"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-items");
        }

    }

    public void saveItem() {
        Optional<String> itemId = Optional.ofNullable(getSelectedItem().getId());

        if (!itemId.isPresent()) {
            createItem();
        } else {
            updateItem();
        }

        PrimeFaces.current().executeScript("PF('manageGameDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-items");
    }

    public void deleteItem() {

        Optional<Boolean> deletedGame = getRestClient().delete(getSelectedItem().getId());

        if (deletedGame.isPresent()) {

            this.getAllItems().remove(getSelectedItem());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Deleted"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-items");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Item", getSelectedItem().getClass().getName()));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-items");
        }
    }

    public void deleteSelectedItem() {

        for (T selected : getSelectedItems()) {
            Optional<Boolean> deletedGame = getRestClient().delete(selected.getId());

            if (deletedGame.isPresent()) {

                if (deletedGame.get()) {
                    getAllItems().remove(selected);
                    getSelectedItems().remove(selected);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Removed"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-items");
                PrimeFaces.current().executeScript("PF('dtItems').clearFilters()");

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Game", selected.getClass().getName()));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-items");
            }
        }

    }

}
