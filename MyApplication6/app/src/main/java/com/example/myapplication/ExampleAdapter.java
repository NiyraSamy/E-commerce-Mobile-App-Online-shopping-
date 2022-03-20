package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList ;
    private OnItemClickListner mListener ;

    public interface OnItemClickListner {
        void onItemClick(int position);
    }
    public void setOnItemClickListner( OnItemClickListner listner) {
        mListener = listner ;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mImageView ;
        public TextView mTextView1 ;
        public TextView mTextView2 ;

        public ExampleViewHolder(@NonNull View itemView , OnItemClickListner listner ) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.line1);
            mTextView2 = itemView.findViewById(R.id.line2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listner != null) {
                        int position = getAdapterPosition() ;
                        if(position != RecyclerView.NO_POSITION) {
                            listner.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList)
    {
        mExampleList = exampleList ;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item , parent , false );
        ExampleViewHolder evh = new ExampleViewHolder(v , mListener );
        return evh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        Bitmap bmp = BitmapFactory.decodeByteArray(currentItem.getmImageResource(), 0, (currentItem.getmImageResource()).length);
        holder.mImageView.setImageBitmap(bmp);
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size() ;
    }


}
