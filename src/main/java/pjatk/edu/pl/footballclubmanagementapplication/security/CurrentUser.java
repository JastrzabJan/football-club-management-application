package pjatk.edu.pl.footballclubmanagementapplication.security;

import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

    User getUser();
}
