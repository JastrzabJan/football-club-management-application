package pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks;

import org.springframework.security.access.annotation.Secured;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.*;

@Secured(value = {COACH_ROLE, ADMIN_ROLE, MANAGER_ROLE})
public class CoachAccessMock {
}
