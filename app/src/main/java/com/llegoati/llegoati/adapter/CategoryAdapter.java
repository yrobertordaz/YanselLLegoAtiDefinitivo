package com.llegoati.llegoati.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.ProductListActivity;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.models.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 17/10/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> mCategories;

    public CategoryAdapter() {
        mCategories = new ArrayList<>();
    }

    public void clear() {
        this.mCategories.clear();
        this.notifyDataSetChanged();
    }

    public void addCategory(Category category) {
        this.mCategories.add(category);
        this.notifyItemInserted(this.mCategories.size() - 1);
    }

    public CategoryAdapter(List<Category> mCategories) {

        this.mCategories = mCategories;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_catalogo, viewGroup, false);
        return new CategoryViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        final int categoryPosition = position;
        holder.mGridLayout.setOnItemClickListener(null);
        holder.mName.setText(mCategories.get(position).getCategoryName());

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(holder.itemView.getContext(), mCategories.get(position).getSubcategories());
        holder.mGridLayout.setAdapter(subCategoryAdapter);
        subCategoryAdapter.notifyDataSetChanged();
        holder.mGridLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Callback method to be invoked when an item in this AdapterView has
             * been clicked.
             * <p/>
             * Implementers can call getItemAtPosition(position) if they need
             * to access the data associated with the selected item.
             *
             * @param parent   The AdapterView where the click happened.
             * @param view     The view within the AdapterView that was clicked (this
             *                 will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id       The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Context context = view.getContext();

                Intent intent = new Intent(context, ProductListActivity.class);

                intent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_ID, mCategories.get(categoryPosition).getSubcategories().get(position).getId());
                intent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, mCategories.get(categoryPosition).getSubcategories().get(position).getNameSubcategory());
                context.startActivity(intent);

//                Toast.makeText(view.getContext(), mCategories.get(categoryPosition).getSubcategories().get(position).getNameSubcategory(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    @Override
    public long getItemId(int position) {
        return mCategories.get(position).getCategoryId();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public RecyclerView listSubcategories;

        public com.llegoati.llegoati.infrastructure.models.MyGridView mGridLayout;


        public CategoryViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.content_catalogo_title);
            mGridLayout = (com.llegoati.llegoati.infrastructure.models.MyGridView) itemView.findViewById(R.id.gridViewSubCategories);

        }
    }
}
