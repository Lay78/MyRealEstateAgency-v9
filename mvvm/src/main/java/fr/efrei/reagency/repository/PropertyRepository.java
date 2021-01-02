package fr.efrei.reagency.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;

import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.database.PropertyDatabase;
import fr.efrei.reagency.tools.Utils;

//This class implement the singleton pattern
public final class PropertyRepository {

    private static volatile PropertyRepository instance;

    // We accept the "out-of-order writes" case
    public static PropertyRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (PropertyRepository.class) {
                if (instance == null) {
                    instance = new PropertyRepository(context);
                }
            }
        }

        return instance;
    }

    private final PropertyDatabase propertyDatabase;

    private PropertyRepository(Context context) {
        propertyDatabase = Room.databaseBuilder(context, PropertyDatabase.class, "property-database").allowMainThreadQueries().build();
    }

    public List<Property> getProperties() {
        return propertyDatabase.propertyDao().getProperties();
    }

    @NotNull
    public List<Property> getPropertiesSearch(@NotNull String searchCriteria) {
        List<Property> properties = propertyDatabase.propertyDao().getProperties();
        List<Property> newProperties = getPropertiesFromCriteria(properties, searchCriteria);
        return newProperties;
    }

    public void deleteProperty(Property property) {
        propertyDatabase.propertyDao().deleteProperty(property);
    }

    public void addProperty(Property property) {
        propertyDatabase.propertyDao().addProperty(property);
    }

    public List<Property> sortPropertiesByIdDes() {
        return propertyDatabase.propertyDao().sortPropertiesByIdDes();
    }

    public List<Property> sortPropertiesByIdAsc() {
        return propertyDatabase.propertyDao().sortPropertiesByIdAsc();
    }

    public List<Property> sortPropertiesByHouse() {
        return propertyDatabase.propertyDao().sortPropertiesByHouse();
    }

    public List<Property> sortPropertiesByFlat() {
        return propertyDatabase.propertyDao().sortPropertiesByFlat();
    }

    public List<Property> sortPropertiesByOffice() {
        return propertyDatabase.propertyDao().sortPropertiesByOffice();
    }

    public List<Property> sortPropertiesByStudent() {
        return propertyDatabase.propertyDao().sortPropertiesByStudent();
    }

    public List<Property> sortPropertiesByPriceAsc() {
        return propertyDatabase.propertyDao().sortPropertiesByPriceAsc();
    }

    public List<Property> sortPropertiesByPriceDes() {
        return propertyDatabase.propertyDao().sortPropertiesByPriceDes();
    }

    public List<Property> sortPropertiesBySurfaceAsc() {
        return propertyDatabase.propertyDao().sortPropertiesBySurfaceAsc();
    }

    public List<Property> sortPropertiesBySurfaceDes() {
        return propertyDatabase.propertyDao().sortPropertiesBySurfaceDes();
    }
    @NotNull
    private List<Property> getPropertiesFromCriteria(List<Property> properties, @NotNull String searchCriteria) {
        List<Property> newProperties = new ArrayList<Property>();
        String[] parts = searchCriteria.split(";");
        String sPriceMin = "", sPriceMax = "", sSurfaceMin = "", sSurfaceMax = "", sRoomMin = "", sRoomMax = "";
        byte[] types = {0, 0, 0, 0};
        byte[] status = {0, 0};
        boolean[] bType = new boolean[4];
        boolean[] bStatus = new boolean[2];
        int priceMin = 0, priceMax = 0, surfaceMin = 0, surfaceMax = 0, roomMin = 0, roomMax = 0;
        boolean matchDelaySold = false;
        String sDateSold = "";
        for (int k = 0; k < parts.length; k++) {
            if (parts[k].contains("delaySold")) {
                String[] sType;
                sType = parts[k].split("=");
                sDateSold = sType[1];
                if (!sDateSold.equals("-1")) {
                    matchDelaySold = true;
//                    break;
                }
            } else if (parts[k].contains("type")) {
                String[] sType;
                sType = parts[k].split("=");
                types = sType[1].getBytes();
                if (types[0] == '1') bType[0] = true;
                if (types[1] == '1') bType[1] = true;
                if (types[2] == '1') bType[2] = true;
                if (types[3] == '1') bType[3] = true;
            } else if (parts[k].contains("status")) {
                String[] sType;
                sType = parts[k].split("=");
                status = sType[1].getBytes();
                if (status[0] == '1') bStatus[0] = true;
                if (status[1] == '1') bStatus[1] = true;
            } else if (parts[k].contains("priceMin")) {
                String[] sType;
                sType = parts[k].split("=");
                sPriceMin = sType[1];
                if (!sPriceMin.equals("-1"))
                    priceMin = Integer.parseInt(sType[1]);
            } else if (parts[k].contains("priceMax")) {
                String[] sType;
                sType = parts[k].split("=");
                sPriceMax = sType[1];
                if (!sPriceMax.equals("-1"))
                    priceMax = Integer.parseInt(sType[1]);
            } else if (parts[k].contains("surfaceMin")) {
                String[] sType;
                sType = parts[k].split("=");
                sSurfaceMin = sType[1];
                if (!sSurfaceMin.equals("-1"))
                    surfaceMin = Integer.parseInt(sType[1]);
            } else if (parts[k].contains("surfaceMax")) {
                String[] sType;
                sType = parts[k].split("=");
                sSurfaceMax = sType[1];
                if (!sSurfaceMax.equals("-1"))
                    surfaceMax = Integer.parseInt(sType[1]);
            } else if (parts[k].contains("roomMin")) {
                String[] sType;
                sType = parts[k].split("=");
                sRoomMin = sType[1];
                if (!sRoomMin.equals("-1"))
                    roomMin = Integer.parseInt(sType[1]);
            } else if (parts[k].contains("roomMax")) {
                String[] sType;
                sType = parts[k].split("=");
                sRoomMax = sType[1];
                if (!sRoomMax.equals("-1"))
                    roomMax = Integer.parseInt(sType[1]);
            }
        }
//        if (matchDelaySold) {
//            Date dateSold = StringToDate(sDateSold);
//            for (int j = 0; j < properties.size(); j++) {
//                if (properties.get(j).propertyStatus.equals("Sold")) {
//                    Date propertyDateSold = StringToDate(properties.get(j).propertyDateSold);
//                    if (propertyDateSold.after(dateSold)) {
//                        newProperties.add(properties.get(j));
//                    }
//                }
//            }
//        } else {
            for (int j = 0; j < properties.size(); j++) {
                boolean matchType = checkTypeCriteria(properties.get(j).propertyType, types, bType);
                boolean matchStatus = checkStatusCriteria(properties.get(j).propertyStatus, status, bStatus);
                boolean matchPrice = checkValueCriteria(properties.get(j).propertyPrice, sPriceMin, sPriceMax, priceMin, priceMax);
                boolean matchSurface = checkValueCriteria(properties.get(j).propertySurface, sSurfaceMin, sSurfaceMax, surfaceMin, surfaceMax);
                boolean matchRoom = checkValueCriteria(properties.get(j).propertyRoom, sRoomMin, sRoomMax, roomMin, roomMax);
                if (matchDelaySold) {
                    if (properties.get(j).propertyStatus.equals("Sold") && matchType && matchPrice && matchSurface && matchRoom) {
                        Date dateSold = StringToDate(sDateSold);
                        Date propertyDateSold = StringToDate(properties.get(j).propertyDateSold);
                        if (propertyDateSold.after(dateSold)) {
                            newProperties.add(properties.get(j));
                        }
                    }
                }
                else {
                    if (matchType && matchStatus && matchPrice && matchSurface && matchRoom) {
                        newProperties.add(properties.get(j));
                    }
                }
            }
//        }
        return newProperties;
    }
    private Date StringToDate(String s){
        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            result  = dateFormat.parse(s);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return result;
    }
    private boolean checkTypeCriteria(String propertyType, @NotNull byte[] types, @NotNull boolean[] bType)
    {
        boolean match;
        if ((bType[0] && propertyType.equals("House")) ||
                (bType[1] && propertyType.equals("Flat")) ||
                (bType[2] && propertyType.equals("Office")) ||
                (bType[3] && propertyType.equals("Student Room"))) {
            match = true;
        } else match = false;
        return match;
    }
    private boolean checkStatusCriteria(String propertyStatus, @NotNull byte[] status, @NotNull boolean[] bStatus)
    {
        boolean match;
        if ((bStatus[0] && propertyStatus.equals("Not Sold")) ||
                (bStatus[1] && propertyStatus.equals("Sold"))) {
            match = true;
        } else match = false;
        return match;
    }
    private boolean checkValueCriteria(int value, @NotNull String sMin, @NotNull String sMax, int minVal, int maxVal)
    {
        boolean match = false;
        if (!sMin.equals("-1") && !sMax.equals("-1")) {
            if (value >= minVal &&
                    value <= maxVal) {
                match = true;
            }
        } else if (!sMin.equals("-1")) {
            if (value >= minVal) {
                match = true;
            }

        } else if (!sMax.equals("-1")) {
            if (value <= maxVal)
                match = true;
        } else {
            match = true;
        }
        return match;
    }

}
