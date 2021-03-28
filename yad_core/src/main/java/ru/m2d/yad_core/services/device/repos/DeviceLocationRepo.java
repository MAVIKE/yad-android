package ru.m2d.yad_core.services.device.repos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import ru.m2d.yad_core.core.models.Location;
import ru.m2d.yad_core.core.repos.LocationRepo;


public class DeviceLocationRepo implements LocationRepo {
    private final MutableLiveData<Location> data = new MutableLiveData<>();

    // запушен ли startLoopUpdateLocation
    private boolean isLocationUpdating = false;

    private final FusedLocationProviderClient fusedLocationClient;

    // указатель на activity, для которой будет получаться геолокация
    private final Activity activity;
    // время обновления геолокации в ms
    private int timeUpdateLocation;

    // колбэк, вызывающийся в случае обновления локации (метод updatingLocation)
    // обновляет liveData
    private final LocationCallback locationUpdatingCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NotNull LocationResult locationResult) {
            android.location.Location location = locationResult.getLastLocation();
            Location loc = new Location();
            loc.latitude = location.getLatitude();
            loc.longitude = location.getLongitude();
            data.postValue(loc);
        }
    };

    public DeviceLocationRepo(Activity activity) {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        this.activity = activity;
    }

    // возвращает самую актуальную геолокацию
    @Override
    public LiveData<Location> getLocation() {
        // передаем requestLocation в качестве параметра в checkLocationRequesting
        Runnable task = new Runnable() {
            public void run() {
                requestLocation();
            }
        };
        // проверка, есть ли права доступа у activity и включена ли геолокация
        // если нет, то не обновляем data
        checkLocationRequesting(task);
        return data;
    }

    // начать обновлять геолокацию устройства
    public LiveData<Location> startUpdateLocation(int timeUpdateLocation) {
        // если время обновления > 0 и цикл обновления геолокации еще не запущен
        if (!this.isLocationUpdating && timeUpdateLocation > 0) {
            this.timeUpdateLocation = timeUpdateLocation;
            Runnable task = new Runnable() {
                public void run() {
                    updatingLocation();
                }
            };
            checkLocationRequesting(task);
        }
        return data;
    }

    // прекратить обновлять значение геолокации
    public void stopUpdateLocation() {
        if (this.isLocationUpdating) {
            fusedLocationClient.removeLocationUpdates(this.locationUpdatingCallback);
            this.isLocationUpdating = false;
        }
    }

    // обновление геолокации
    // (проверка прав не нужна, поскольку метод вызывается из
    // checkLocationRequesting, который осуществляет проверку)
    @SuppressLint("MissingPermission")
    private void updatingLocation() {
        // (в документации рекомендуют вначале вызвать хотя бы один раз геолокацию,
        // а уже потом обновлять ее)
        CancellationTokenSource cts = new CancellationTokenSource();
        this.fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(this.activity, new OnSuccessListener<android.location.Location>() {
                    // в случае неудачи ничего не делаем
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {
                            // обновляем liveData
                            Location loc = new Location();
                            loc.latitude = location.getLatitude();
                            loc.longitude = location.getLongitude();
                            data.postValue(loc);

                            // запускаем цикл обновления локации
                            isLocationUpdating = true;
                            LocationRequest locationRequest = LocationRequest.create();
                            // задаем время обновления геолокации
                            locationRequest.setInterval(timeUpdateLocation);
                            // выставляем точность рассчета геолокации
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            fusedLocationClient.requestLocationUpdates(locationRequest,
                                    locationUpdatingCallback,
                                    Looper.getMainLooper());
                        }
                    }
                });
    }

    // делает запрос на получение геолокации устройства и сохраняет ее в data в случае успеха
    @SuppressLint("MissingPermission")
    private void requestLocation() {
        // создаем токен, по которому в теории можно прекратить вызов getCurrentLocation
        // методом .cancel()
        // (здесь не используется)
        CancellationTokenSource cts = new CancellationTokenSource();
        this.fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(this.activity, new OnSuccessListener<android.location.Location>() {
                    // в случае удачного получения обновляем liveData, иначе ничего не делаем
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {
                            Location loc = new Location();
                            loc.latitude = location.getLatitude();
                            loc.longitude = location.getLongitude();
                            data.postValue(loc);
                        }
                    }
                });
    }

    // проверяет, возможно ли получить геолокацию устройства
    // (разрешены ли права и включена ли геолокация на устройстве)
    private void checkLocationRequesting(Runnable task) {
        // проверка, есть ли права доступа у activity
        if (ActivityCompat.checkSelfPermission(
                this.activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // проверка, включена ли геолокация
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            SettingsClient client = LocationServices.getSettingsClient(this.activity);
            client.checkLocationSettings(builder.build())
                    .addOnSuccessListener(this.activity, new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            task.run();
                        }
                    });
        }
    }


}
