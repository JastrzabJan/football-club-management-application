package pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks;


import org.springframework.security.access.annotation.Secured;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;

@Secured(value = {ADMIN_ROLE})
public class AdminAccessMock {
}
