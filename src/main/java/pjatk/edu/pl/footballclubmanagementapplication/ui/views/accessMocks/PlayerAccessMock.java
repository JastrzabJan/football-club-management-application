package pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks;

import org.springframework.security.access.annotation.Secured;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.*;

@Secured(value = {PLAYER_ROLE, COACH_ROLE, MANAGER_ROLE, ADMIN_ROLE})
public class PlayerAccessMock {
}
