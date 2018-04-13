package life.qbic.view.tabs;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

/**
 * @author fhanssen
 */
public class GridView extends AView {

    private final GridLayout gridLayout;

    public GridView(int columns, int rows, boolean margin, boolean spacing){
        super();

        //Number of columns and rows must be at least 1
        columns = columns <= 0 ? 1 : columns;
        rows = rows <= 0 ? 1 : rows;

        this.gridLayout = new GridLayout(columns,rows);
        this.gridLayout.setMargin(margin);
        this.gridLayout.setSpacing(spacing);
        this.gridLayout.setSizeFull();
        this.gridLayout.setStyleName("workflow");

        this.gridLayout.setHideEmptyRowsAndColumns(true);

    }

    public void addGridComponents(Panel component){

        gridLayout.addComponent(component);
        gridLayout.setColumnExpandRatio(gridLayout.getCursorX(), 0.0f);
        gridLayout.setRowExpandRatio(gridLayout.getCursorY(), 0.0f);

    }

    @Override
    public Component getComponent() {
        return gridLayout;
    }
}
