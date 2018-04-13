# Statistics View #

This portlet visualizes QBiC related data and statistics on the landing page.


## How to add a new Graph ##

In order to add a new chart, the core task is to add a new AChartPresenter in the presenter.charts package. However,
depending if your chart type already exists more steps may be required. They are marked in the code with TODOs.

Prereq: Your data already exists in the yaml config file. If not then please implement it in https://github.com/qbicsoftware/statistics-data-retrieval

1. Ensure that the submodule classes are up-to-date:
    
    ``git submodule foreach git pull origin master``

    The submodule classes are now updated automatically. 
    Otherwise, your submodule will point to the time-point in history, it was updated last.
    
2. Check if your plot type already exists in the model.charts package (e.g. Barplot, Lineplot, etc.). 
    If your data requires a new plot type, create one following the same schema as the others. Your model class
    needs to extend AChartModel.
    https://demo.vaadin.com/charts/ is a comprehensive guide on existing vaadin charts.
    
    If your chart requires more model fields or methods, add them.
    
    If you do not want to visualize charts, but something else, such as a grid, create a new class
    implementing AModel, which visualizes objects extending AComponent. 

3. If you had to create a new model, you have to create a new plot type in the view.tabs.charts or  view.tabs
    package. Create it similar to the other classes in that sub-package by extending AView/AChartView.
    
4. Create a new MyPlotPresenter, which implements the AChartPresenter interface, similar to the other
    presenters in the presenter.charts package. 
    
    Example: SuperKingdomCountPresenter with the sub charts of GenusSpeciesCountPresenter
    
5. Call your new ChartPresenter from the MainPresenter by creating a new 'addMyPlot' with the following tasks:
    1) Get your ChartConfigs 
    2) Create a new AChartPresenter object of type MyPlotPresenter
    3) Set a new Tab 
    4) Add Button and SubChartsListener 
    5) Add Tab to mainView

    The title in the mainView.addTabView method is later shown to the user as tab title. When accessing the chart data from the config file,
    it is absolutely necessary, that you use the correct name (First layer below 'charts', shows all data divided by plots).
    If you followed step 1, it should appear in the ChartNames. 
    
6. Add logging




 



    