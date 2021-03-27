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


interface Expression {
    void performTask();
}

public class DeviceLocationRepo implements LocationRepo {
    private final MutableLiveData<Location> data = new MutableLiveData<>();

    // запушен ли startLoopUpdateLocation
    private boolean isLocationUpdating = false;

    // получена ли когда-либо локация
    private boolean isLocationReceivedOnce = false;

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
        Expression expr = this::requestLocation;
        // проверка, есть ли права доступа у activity и включена ли геолокация
        // если нет, то не обновляем data
        checkLocationRequesting(expr);
        return data;
    }

    // начать обновлять геолокацию устройства
    public void startUpdateLocation(int timeUpdateLocation) {
        // проверяем, получалась ли когда-либо геолокация, если нет, то не запускаем обновление
        // (в документации рекомендуют вначале вызвать хотя бы один раз геолокацию,
        // а уже потом обновлять ее)
        if (isLocationReceivedOnce) {
            // если время обновления > 0 и цикл обновления геолокации еще не запущен
            if (timeUpdateLocation > 0 && !this.isLocationUpdating) {
                this.timeUpdateLocation = timeUpdateLocation;
                Expression expr = this::updatingLocation;
                checkLocationRequesting(expr);
            }
        }
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
        this.isLocationUpdating = true;
        LocationRequest locationRequest = LocationRequest.create();
        // задаем время обновления геолокации
        locationRequest.setInterval(this.timeUpdateLocation);
        // выставляем точность рассчета геолокации
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.fusedLocationClient.requestLocationUpdates(locationRequest,
                this.locationUpdatingCallback,
                Looper.getMainLooper());
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
                            isLocationReceivedOnce = true;

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
    private void checkLocationRequesting(Expression onSuccessFunc) {
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
                            onSuccessFunc.performTask();
                        }
                    });
        }
    }


}
