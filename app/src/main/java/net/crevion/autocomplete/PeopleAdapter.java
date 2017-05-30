package net.crevion.autocomplete;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yusuf on 5/30/17.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> implements Filterable {

    private List<People> peopleList;
    private List<People> peopleListTemp;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_people, null);

        return (new PeopleAdapter.ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.ViewHolder holder, final int position) {
        if (position < peopleListTemp.size()) {
            holder.textViewName.setText(peopleListTemp.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewItemClickListener.onItemClick(v, position, peopleListTemp.get(position).getName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return peopleListTemp.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.label_name);
        }
    }

    PeopleAdapter(List<People> listPerson, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.peopleList = listPerson;
        this.peopleListTemp = listPerson;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new FilterPeopleName();
    }

    public class FilterPeopleName extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            ArrayList<People> filteredPeople = new ArrayList<>();
            String filterString = constraint.toString().toLowerCase();
            String filterableString;

            for (int i = 0; i < peopleList.size(); i++) {
                filterableString = peopleList.get(i).getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    filteredPeople.add(peopleList.get(i));
                }
            }
            filterResults.values = filteredPeople;
            filterResults.count = filteredPeople.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            peopleListTemp = (ArrayList<People>) results.values;
            notifyDataSetChanged();
        }
    }
}
