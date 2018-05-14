package life.qbic.view.tabs;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

/**
 * @author fhanssen
 * 
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

        this.gridLayout.setHideEmptyRowsAndColumns(true);

    }

    public void addGridComponents(Panel component){

        component.setSizeFull();
        component.setHeight(150, Sizeable.Unit.PIXELS);
        gridLayout.addComponent(component);

    }

    public void addGridComponents(Accordion accordion){
        gridLayout.addComponent(accordion);
    }

    @Override
    public Component getComponent() {
        return gridLayout;
    }

    public void setRows(int rows){
        gridLayout.setRows(rows);
    }
}