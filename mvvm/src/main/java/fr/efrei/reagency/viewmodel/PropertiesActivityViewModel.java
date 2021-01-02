package fr.efrei.reagency.viewmodel;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.repository.PropertyRepository;

public final class PropertiesActivityViewModel
        extends AndroidViewModel
{

    public enum OrderState
    {
        sortIdAsc, sortIdDes, sortId, sortHouse, sortFlat, sortOffice, sortStudent, sortPriceAsc,
        sortPriceDes, sortSurfaceAsc, sortSurfaceDes
    }

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    private OrderState currentOrderState = OrderState.sortId;

    public PropertiesActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void updateOrder(OrderState orderState)
    {
        currentOrderState = orderState;
        loadProperties();
    }

    public void loadPropertiesSearch(String searchCriteria) {
        properties.postValue(PropertyRepository.getInstance(getApplication()).getPropertiesSearch(searchCriteria));
    }
    public void loadProperties()
    {
        switch (currentOrderState) {
            case sortId:
                properties.postValue(PropertyRepository.getInstance(getApplication()).getProperties());
                break;
            case sortIdAsc:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByIdAsc());
                break;
            case sortIdDes:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByIdDes());
                break;
            case sortHouse:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByHouse());
                break;
            case sortFlat:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByFlat());
                break;
            case sortOffice:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByOffice());
                break;
            case sortStudent:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByStudent());
                break;
            case sortPriceAsc:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByPriceAsc());
                break;
            case sortPriceDes:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesByPriceDes());
                break;
            case sortSurfaceAsc:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesBySurfaceAsc());
                break;
            case sortSurfaceDes:
                properties.postValue(PropertyRepository.getInstance(getApplication()).sortPropertiesBySurfaceDes());
                break;
        }
    }

}
