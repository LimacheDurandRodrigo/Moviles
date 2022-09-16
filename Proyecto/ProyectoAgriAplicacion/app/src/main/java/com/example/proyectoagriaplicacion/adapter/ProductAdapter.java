package com.example.proyectoagriaplicacion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoagriaplicacion.R;
import com.example.proyectoagriaplicacion.model.ListProduct;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ListProduct> mData;
    private LayoutInflater mInflater;
    private Context context;
    public ProductAdapter(List<ListProduct> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;


    }
    @Override
    public int getItemCount(){return mData.size();

    }
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = mInflater.inflate(R.layout.marketlist, null);
        return new ProductAdapter.ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItem(List<ListProduct> items){mData=items;   }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, description,status;
        ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.nameProduct);
            description = itemView.findViewById(R.id.description);
            status = itemView.findViewById(R.id.estado);


        }

        void  bindData(final ListProduct item){
            image.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            description.setText(item.getDescription());
            status.setText(item.getStatus());
        }
    }


}
