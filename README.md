# Statistics View #

This portlet visualizes QBiC related data and statistics on the landing page.

## Ideas ##
Please see the Trello card on Statistics View

## How to add a new Graph ##

0. Assume data already exists in the yaml config file.

1. Check if your plot type already exists in the model.charts package (e.g. Barplot, Lineplot, etc.). 
    If your data requires a new plot type , create one following the same schema as the others. 
    (https://demo.vaadin.com/charts/ is a comprehensive guide on existing vaadin charts).
    If your chart type requires more model options, add the required methods.

2. If you had to create a new model, you most likely have to create a new plot type in the view.charts package.
    Create it similar to the other classes in that sub-package.
    
3. Create a new MyPlotPresenter, which implements the ChartPresenter interface, similar to the other
    presenters in the presenter.charts package.

4. In the MainPresenter add a methode 'addMyPlot'. Create a new MyPlotPresenter object and add it to the mainView object.
    The title in the mainView.addMyPlotType method is later shown to the user as tab title. When accessing the chart data from the config file,
    it is absolutely necessary, that you use the correct name (First layer below 'charts', shows all data divided by plots).




 



    