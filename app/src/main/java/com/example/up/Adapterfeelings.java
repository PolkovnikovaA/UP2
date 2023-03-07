package com.example.up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class Adapterfeelings extends RecyclerView.Adapter<Adapterfeelings.ViewHolder> {

    private List<Maskfeelings> dataModalArrayList;
    private Context context;

    public Adapterfeelings(List<Maskfeelings> dataModalArrayList, Context context) {
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapterfeelings.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapterfeelings.ViewHolder(LayoutInflater.from(context).inflate(R.layout.mask_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterfeelings.ViewHolder holder, int position) {
        final Maskfeelings modal = dataModalArrayList.get(position);
        holder.title.setText(modal.getTitle());

        if(modal.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.icon);
        }
        else
        {
            new Adapterfeelings.DownloadImageTask((ImageView) holder.image)
                    .execute(modal.getImage());
        }
    }

    public void notifyDataSetInvalidated() {
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    @Override
    public int getItemCount() {
        return dataModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textNastroenie2);
            image = itemView.findViewById(R.id.foto2);
        }
    }
}
