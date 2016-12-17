package nyc.c4q.helenchan.nekoclone;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nyc.c4q.helenchan.nekoclone.model.Chinchilla;

/**
 * Created by helenchan on 12/15/16.
 */

public class ChinchillaAdapter extends RecyclerView.Adapter<ChinchillaAdapter.ChinChillViewHolder> {


    List<Chinchilla> mChinchillaList = new ArrayList<>();


    @Override
    public ChinChillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ChinChillViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ChinChillViewHolder holder, int position) {
        Chinchilla chin = mChinchillaList.get(position);
        holder.bind(chin);

    }

    @Override
    public int getItemCount() {
        return mChinchillaList.size();
    }

    public void setData(List<Chinchilla> chinchillaList) {
        mChinchillaList.clear();
        mChinchillaList.addAll(chinchillaList);
        notifyDataSetChanged();
    }



    public class ChinChillViewHolder extends RecyclerView.ViewHolder {

        @Inject
        TextView chin_name;
        ImageView chin_image;

        public ChinChillViewHolder(View itemView) {
            super(itemView);
            chin_name = (TextView) itemView.findViewById(R.id.name_textview);
            chin_image = (ImageView) itemView.findViewById(R.id.chin_imageview);
        }

        public void bind(Chinchilla chin) {
        chin_name.setText(chin.getName());
            Picasso.with(itemView.getContext())
                    .load(chin.getImage_url())
                    .into(chin_image);
        }
    }
}
