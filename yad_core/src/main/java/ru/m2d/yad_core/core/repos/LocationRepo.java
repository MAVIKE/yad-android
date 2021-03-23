package ru.m2d.yad_core.core.repos;

import androidx.lifecycle.LiveData;

import ru.m2d.yad_core.core.models.Location;

public interface LocationRepo {
    LiveData<Location> getLocation();
}
