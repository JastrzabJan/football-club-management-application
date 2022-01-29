package pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks;


import org.springframework.security.access.annotation.Secured;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PLAYER_ROLE;

@Secured(value = {PLAYER_ROLE})
public class PlayerAccessOnly {
}
