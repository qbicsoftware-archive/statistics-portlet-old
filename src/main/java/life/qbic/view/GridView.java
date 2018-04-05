package life.qbic.view;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

public class GridView extends AView {

    private final GridLayout gridLayout;

    public GridView(){
        super();
        this.gridLayout = new GridLayout();
        this.gridLayout.setColumns(3);
        this.gridLayout.setMargin(true);
        this.gridLayout.setSpacing(true);
        this.gridLayout.setSizeFull();
        gridLayout.addStyleName("example-gridlayout");

    }

    public void addGridComponents(Panel component){

        gridLayout.addComponent(component);
        gridLayout.setColumnExpandRatio(gridLayout.getCursorX(), 0.0f);
        gridLayout.setRowExpandRatio(gridLayout.getCursorY(), 0.0f);

    }

    public GridLayout getGridLayout() {
        return gridLayout;
    }
}
