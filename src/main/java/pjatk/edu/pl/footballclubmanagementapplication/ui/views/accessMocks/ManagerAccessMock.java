package pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks;


import org.springframework.security.access.annotation.Secured;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;

@Secured(value = {MANAGER_ROLE, ADMIN_ROLE})
public class ManagerAccessMock {
}
