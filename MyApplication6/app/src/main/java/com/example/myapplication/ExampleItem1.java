package com.example.myapplication;

public class ExampleItem1 {

    private byte[] mImageResource ;
    private String mText1 ;
    private String mText2 ;


    public ExampleItem1( byte[] imageResource , String text1 , String text2 )
    {
        mImageResource = imageResource ;
        mText1 = text1 ;
        mText2 = text2 ;
    }
    public byte[] getmImageResource()
    {
        return mImageResource ;
    }

    public String getmText1()
    {
        return mText1 ;
    }

    public String getmText2()
    {
        return mText2 ;
    }
}
