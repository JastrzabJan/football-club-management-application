package pjatk.edu.pl.footballclubmanagementapplication.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.MainView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.CoachesView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.LeaguesView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.MatchesView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.PlayersView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.SeasonsView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.TeamsView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.TrainingsView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.UsersView;

import java.util.ArrayList;
import java.util.List;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_COACHES;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_HOME;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_LEAGUES;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_LOGOUT;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_MATCHES;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_PLAYERS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_SEASONS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_TEAMS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_TRAININGS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.TITLE_USERS;

public class MainLayout extends VerticalLayout implements RouterLayout {

    public MainLayout() {

        Tabs tabs = createMenuTabs();
        add(tabs);
        add(new H1("Football Management Application"));

    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>(4);
        tabs.add(createTab(VaadinIcon.HOME, TITLE_HOME,
                MainView.class));

        if (SecurityUtils.isAccessGranted(PlayersView.class)) {
            tabs.add(createTab(VaadinIcon.USER, TITLE_PLAYERS, PlayersView.class));
        }
        if (SecurityUtils.isAccessGranted(TeamsView.class)) {
            tabs.add(createTab(VaadinIcon.USER, TITLE_TEAMS, TeamsView.class));
        }
        if (SecurityUtils.isAccessGranted(SeasonsView.class)) {
            tabs.add(createTab(VaadinIcon.BOOKMARK, TITLE_SEASONS, SeasonsView.class));
        }
        if (SecurityUtils.isAccessGranted(LeaguesView.class)) {
            tabs.add(createTab(VaadinIcon.USER, TITLE_LEAGUES, LeaguesView.class));
        }
        if (SecurityUtils.isAccessGranted(TrainingsView.class)) {
            tabs.add(createTab(VaadinIcon.USER, TITLE_TRAININGS, TrainingsView.class));
        }
        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            tabs.add(createTab(VaadinIcon.USER, TITLE_USERS, UsersView.class));
        }
        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            tabs.add(createTab(VaadinIcon.USER, TITLE_COACHES, CoachesView.class));
        }
        if (SecurityUtils.isAccessGranted(MatchesView.class)) {
            tabs.add(createTab(VaadinIcon.GAMEPAD, TITLE_MATCHES, MatchesView.class));
        }

        final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
        final Tab logoutTab = createTab(createLogoutLink(contextPath));
        tabs.add(logoutTab);
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static Anchor createLogoutLink(String contextPath) {
        final Anchor a = populateLink(new Anchor(), VaadinIcon.ARROW_RIGHT, TITLE_LOGOUT);
        a.setHref(contextPath + "/logout");
        return a;
    }

    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }
}

