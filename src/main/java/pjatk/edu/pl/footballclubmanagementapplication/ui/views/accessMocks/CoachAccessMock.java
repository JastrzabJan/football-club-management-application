package pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks;

import org.springframework.security.access.annotation.Secured;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;

@Secured({COACH_ROLE})
public class CoachAccessMock {
}
