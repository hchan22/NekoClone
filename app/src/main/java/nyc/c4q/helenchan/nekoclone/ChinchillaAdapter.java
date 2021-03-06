package nyc.c4q.helenchan.nekoclone;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.helenchan.nekoclone.model.Chinchilla;

/**
 * Created by helenchan on 12/15/16.
 */

public class ChinchillaAdapter extends RecyclerView.Adapter<ChinchillaAdapter.ChinChillViewHolder> {

    private Listener listener;
    List<Chinchilla> mChinchillaList = new ArrayList<>();

    public ChinchillaAdapter(List<Chinchilla> chinchillas, Listener listener) {
        mChinchillaList = chinchillas;
        this.listener=listener;
    }


    @Override
    public ChinChillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ChinChillViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ChinChillViewHolder holder, int position) {
        Chinchilla chinViewHolder = mChinchillaList.get(position);
        holder.bind(chinViewHolder);

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

        TextView chin_name;
        ImageView chin_image;

        public ChinChillViewHolder(View itemView) {
            super(itemView);
            chin_name = (TextView) itemView.findViewById(R.id.name_textview);
            chin_image = (ImageView) itemView.findViewById(R.id.chin_imageview);
        }

        public void bind(final Chinchilla chin) {
            final Chinchilla chinchilla = chin;
            chin_name.setText(chinchilla.getName());
            Glide.with(itemView.getContext())
                    .load(chinchilla.getImage_url())
                    .fitCenter()
                    .into(chin_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onChinchillaClicked(chinchilla);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onChinchillaLongClicked(chinchilla);
                    return true;
                }
            });
        }
    }

    interface Listener {
        void onChinchillaClicked(Chinchilla chinClick);

        void onChinchillaLongClicked(Chinchilla chinLongClick);
    }
}
