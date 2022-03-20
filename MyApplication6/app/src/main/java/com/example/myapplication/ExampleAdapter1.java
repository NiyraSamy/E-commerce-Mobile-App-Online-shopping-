package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter1 extends RecyclerView.Adapter<ExampleAdapter1.ExampleViewHolder1> {
    private ArrayList<ExampleItem1> mExampleList ;
    private OnItemClickListner mListener ;

    public interface OnItemClickListner {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onIncreaseClick(int position );
        void onDecreaseClick(int position );
    }
    public void setOnItemClickListner( OnItemClickListner listner) {
        mListener = listner ;
    }

    public static class ExampleViewHolder1 extends RecyclerView.ViewHolder {

        public ImageView mImageView ;
        public TextView mTextView1 ;
        public TextView mTextView2 ;
        public TextView remove ;
        public Button inc ;
        public Button dec ;
        public TextView count_txt ;

        public ExampleViewHolder1(View v, OnItemClickListner mListener) {
            super(v);

            mImageView = v.findViewById(R.id.imageView_cart);
            mTextView1 = v.findViewById(R.id.line1_cart);
            mTextView2 = v.findViewById(R.id.line2_cart);
            remove = v.findViewById(R.id.remove) ;
            inc = v.findViewById(R.id.increase) ;
            dec = v.findViewById(R.id.decrease) ;
            count_txt = v.findViewById(R.id.count) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mListener != null) {
                        int position = getAdapterPosition() ;
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mListener != null) {
                        int position = getAdapterPosition() ;
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mListener != null) {
                        int position = getAdapterPosition() ;
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onIncreaseClick(position);
                            int count = Integer.parseInt( count_txt.getText().toString() ) ;
                            count = count + 1 ;
                            count_txt.setText( String.valueOf(count) );

                        }
                    }
                }
            });

            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mListener != null) {
                        int position = getAdapterPosition() ;
                        if(position != RecyclerView.NO_POSITION) {

                            int count = Integer.parseInt( count_txt.getText().toString() ) ;
                            if ( count == 1 ) { count_txt.setText("1"); }
                            else {
                                count = count - 1 ;
                                count_txt.setText( String.valueOf(count) );
                                mListener.onDecreaseClick(position);
                            }

                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter1(ArrayList<ExampleItem1> exampleList)
    {
        mExampleList = exampleList ;
    }
    @NonNull
    @Override
    public ExampleViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item1 , parent , false );
        ExampleAdapter1.ExampleViewHolder1 evh = new ExampleViewHolder1(v , mListener);
        return evh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder1 holder, int position) {
        ExampleItem1 currentItem = mExampleList.get(position);

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
