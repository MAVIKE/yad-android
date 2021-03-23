package ru.m2d.yad_core.services.mock.repos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.m2d.yad_core.core.models.Location;
import ru.m2d.yad_core.core.repos.LocationRepo;


public class MockLocationRepo implements LocationRepo {

    private final MutableLiveData<Location> data = new MutableLiveData<>();

    public MockLocationRepo() {
        Thread locationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateLocation();
            }
        });
        locationThread.start();
    }

    @Override
    public LiveData<Location> getLocation() {
        return data;
    }

    private Location findLocation() {
        Location location = data.getValue();
        if (location != null) {
            // сдвиг приблизительно на 100 метров
            location.longitude += 0.001;
            location.latitude += 0.001;
        }
        else {
            location = get_msk_coord();
        }
        return location;
    }

    // координаты центра Москвы
    private Location get_msk_coord() {
        Location location = new Location();
        location.latitude = 55.753215;
        location.longitude = 37.622504;
        return location;
    }

    private void updateLocation() {
        data.postValue(findLocation());
        try {
            Thread.sleep(3000);
            updateLocation();
        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
    }

}
