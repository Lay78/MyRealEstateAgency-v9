package fr.efrei.reagency.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import org.jetbrains.annotations.NotNull;

import fr.efrei.reagency.R;
import fr.efrei.reagency.adapter.PropertiesAdapter.PropertyViewHolder;
import fr.efrei.reagency.bo.Property;
import fr.efrei.reagency.tools.PropertyViewHolderGroup;
import fr.efrei.reagency.view.PropertiesActivity;
import fr.efrei.reagency.view.PropertyDetailActivity;

import static fr.efrei.reagency.adapter.PropertiesAdapter.PropertyViewHolder.curSelectedProperty;

public  final class PropertiesAdapter
        extends Adapter<PropertyViewHolder>
{
    //instance variable
    //private List<View>itemViewList = new ArrayList<>();
    //public static List<PropertyViewHolderGroup> propertyViewHolderList = new ArrayList<>();

    public static final class PropertyViewHolder
            extends ViewHolder
    {

        private final ImageView propImage;
        private final TextView propType;
        private final TextView propPrice;
        private final TextView propSurface;
        private final TextView propAgent;
        private final TextView propAddress;
        private final TextView propRoom;
        private final TextView propStatus;
        private final TextView propDescription;

        public static View itemViewPropertySelected;
        public static View curPropertySelectedView = null;
        public static Property curSelectedProperty = null;
        public static boolean viewPropertySelected = false;

        public PropertyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //We find the references of the widgets
            propImage = itemView.findViewById(R.id.viewholderImage);
            propType = itemView.findViewById(R.id.viewholderType);
            propPrice = itemView.findViewById(R.id.viewholderPrice);
            propSurface = itemView.findViewById(R.id.viewholderSurface);
            propAgent = itemView.findViewById(R.id.viewholderAgent);
            propRoom = itemView.findViewById(R.id.viewholderRoom);
            propAddress = itemView.findViewById(R.id.viewholderAddress);
            propDescription = itemView.findViewById(R.id.viewholderDescription);
            propStatus = itemView.findViewById(R.id.viewholderStatus);
        }

        public void update(@NotNull final Property property)
        {
            //We update the UI binding the current property to the current item
            Bitmap bitmap = BitmapFactory.decodeByteArray(property.imageBlob, 0, property.imageBlob.length);

            propImage.setImageBitmap(bitmap);
            propType.setText(property.propertyType);
            propPrice.setText(String.valueOf(property.propertyPrice) + " €");
            propSurface.setText(String.valueOf(property.propertySurface) + " m2");
            propRoom.setText(String.valueOf(property.propertyRoom));
            propAgent.setText(property.propertyAgentName);
            propAgent.setTextColor(itemView.getResources().getColor(R.color.colorBlue));
            propAddress.setText(property.propertyAddress);
            if (property.propertyStatus.equals("Sold")) {
                propStatus.setBackgroundResource(R.color.solid_red);
            } else {
                propStatus.setBackgroundResource(R.color.solid_green);
            }
            propStatus.setTextColor(Color.WHITE);
            propStatus.setText(property.propertyStatus);
            propDescription.setText(property.propertyDescription);

            //We handle the click on the current item in order to display a new activity
            //itemView.set
            itemView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the PropertyDetailActivity.
                    //The current user is added as an extra
                    //The Property class implement the "Serializable" interface so I can put the whole object as an extra
//                    final Intent intent = new Intent(itemView.getContext(), PropertyDetailActivity.class);
//                    intent.putExtra(PropertyDetailActivity.PROPERTY_EXTRA, property);
//                    itemView.getContext().startActivity(intent);

                    itemViewPropertySelected = itemView;
                    curSelectedProperty = property;
                    if (viewPropertySelected == false) {
                        curPropertySelectedView = v;
                        viewPropertySelected = true;
                        v.setBackgroundResource(R.color.solid_gray);
                    } else {
                        if (curPropertySelectedView == v) {
                            v.setBackgroundResource(R.color.solid_white);
                            viewPropertySelected = false;
                        }
                        else {
                            curPropertySelectedView.setBackgroundResource(R.color.solid_white);
                            viewPropertySelected = true;
                            curPropertySelectedView = v;
                            v.setBackgroundResource(R.color.solid_gray);
                        }
                    }
                }

            });
        }
    }

    private final List<Property> properties;

    public PropertiesAdapter(List<Property> properties)
    {
        this.properties = properties;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We create the ViewHolder
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_property, parent, false);
        return new PropertyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position)
    {
//        int found = 0;
//        for (int i = 0; i < propertyViewHolderList.size(); i++) {
//            if (propertyViewHolderList.get(i).getPosition() == position) {
//                found = 1;
//                break;
//            }
//        }
//        if (found == 0)
//            propertyViewHolderList.add(new PropertyViewHolderGroup(position, holder, properties.get(position)));
        //We update the ViewHolder
        holder.update(properties.get(position));
    }

    @Override
    public int getItemCount()
    {
        return properties.size();
    }

}
