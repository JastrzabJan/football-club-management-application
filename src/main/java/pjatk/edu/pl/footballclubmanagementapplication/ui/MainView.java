package pjatk.edu.pl.footballclubmanagementapplication.ui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_ROOT;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_HOME;


/**
 * A Designer generated component for the main-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("main-view")
@Route(value = PAGE_ROOT, layout = MainLayout.class)
@PageTitle(value = TITLE_HOME)
public class MainView extends AppLayout {

    public MainView() {
    }

}
