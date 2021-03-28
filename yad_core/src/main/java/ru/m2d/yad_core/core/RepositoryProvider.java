package ru.m2d.yad_core.core;

import ru.m2d.yad_core.core.repos.UsersRepo;

public interface RepositoryProvider {
    UsersRepo getUsersRepo();
}
