package net.crevion.autocomplete;

import android.view.View;

public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position, String message);
}
