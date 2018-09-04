package studioproject.first.my.locationmessageapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;
import java.util.Locale;

public class GPSTracker implements LocationListener
{
    static private Context ctx;
    private static boolean isNetworkEnabled=false;
    private static boolean isGPSEnabled=false;
    private static boolean allDisabled=true;
    private static final long MIN=1000*60;
    private static final long DISTANCE=10;
    static double lat,lng;
    GPSTracker(Context ctx)
    {
        this.ctx=ctx;
        getLocation();
    }
    public void getLocation()
    {
        Location location;
        LocationManager locationManager = (LocationManager) ctx.getSystemService(
                    Context.LOCATION_SERVICE);
        isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isNetworkEnabled&&!isGPSEnabled){

        }else {
            allDisabled=false;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN,
                        DISTANCE, this);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
            }
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN,
                        DISTANCE, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
            }
        }
    }
    public static double getLat()
    {
        return  lat;
    }
    public static double getLng()
    {
        return lng;
    }
    public static String getAddress()
    {
        String finalAddress="";
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.ENGLISH);
            List<Address> addressList = geocoder.getFromLocation(getLat(), getLng(), 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                finalAddress = sb.toString();
        }}catch(Exception e)
        {
            System.out.println(e);
        }
        return  finalAddress;
    }
    public boolean isAllDisabled()
    {
        return allDisabled;
    }
    @Override
    public void onLocationChanged(Location location) {
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
