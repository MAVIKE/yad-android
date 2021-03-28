package ru.m2d.yad_core.core;

import ru.m2d.yad_core.core.repos.UsersRepo;
import ru.m2d.yad_core.services.mock.repos.MockUsersRepo;

public class RepositoryProvider {
    private static UsersRepo usersRepo = new MockUsersRepo();

    UsersRepo getUsersRepo() {
        return usersRepo;
    }
}
