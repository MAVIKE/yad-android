package ru.m2d.yad_core.services.mock;

import ru.m2d.yad_core.core.RepositoryFactory;
import ru.m2d.yad_core.core.repos.LocationRepo;
import ru.m2d.yad_core.core.repos.UsersRepo;
import ru.m2d.yad_core.services.mock.repos.MockLocationRepo;
import ru.m2d.yad_core.services.mock.repos.MockUsersRepo;

public class MockRepositoryFactory implements RepositoryFactory {

    @Override
    public UsersRepo newUsersRepo() {
        return new MockUsersRepo();
    }

    @Override
    public LocationRepo newLocationRepo() {
        return new MockLocationRepo();
    }
}
