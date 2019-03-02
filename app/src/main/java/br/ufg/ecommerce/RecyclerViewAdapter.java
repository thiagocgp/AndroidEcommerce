package br.ufg.ecommerce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Product> products;
    private Context context;

    public RecyclerViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ProductViewHolder holder = (ProductViewHolder) viewHolder;
        Product product  = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView price;

        public ProductViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txt_productName_vh);
            price = view.findViewById(R.id.txt_productPrice_vh);
        }
    }

}
