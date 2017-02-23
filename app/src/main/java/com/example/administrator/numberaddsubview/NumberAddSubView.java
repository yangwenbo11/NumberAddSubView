package com.example.administrator.numberaddsubview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/23.
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Button mAddBtn;
    private Button mSubBtn;
    private TextView mNumTv;

    private int value;//当前值
    private int minValue;//最小值
    private int maxValue;//最大值

    private OnButtonClickListener mOnButtonClickListener;

    public void setmOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.mOnButtonClickListener = onButtonClickListener;
    }

    //获得当前值
    public int getValue() {
        String val=mNumTv.getText().toString();
        if(val!=null&&!"".equals(val)){
            this.value=Integer.parseInt(val);
        }
        return value;
    }

    //设置值
    public void setValue(int value) {
        mNumTv.setText(value+"");
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public NumberAddSubView(Context context) {
        this(context,null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater=LayoutInflater.from(context);
        initView();

        if(attrs!=null){
            TintTypedArray tta=TintTypedArray.obtainStyledAttributes(
                    context,attrs,R.styleable.NumberAddSubView,
                    defStyleAttr,0);
            int val=tta.getInt(R.styleable.NumberAddSubView_value,0);
            setValue(val);

            int minVal=tta.getInt(R.styleable.NumberAddSubView_minValue,0);
            setMinValue(minVal);

            int maxVal=tta.getInt(R.styleable.NumberAddSubView_maxValue,0);
            setMaxValue(maxVal);

            Drawable addBtnDrawable=tta.getDrawable(
                    R.styleable.NumberAddSubView_addBtnBg);

            Drawable subBtnDrawable=tta.getDrawable(
                    R.styleable.NumberAddSubView_subBtnBg);

            setAddBtnBackGround(addBtnDrawable);
            setSubBtnBackGround(subBtnDrawable);

            int numTvDrawable=tta.getResourceId(R.styleable.NumberAddSubView_numTvBg
            ,android.R.color.transparent);
            setNumTvBackground(numTvDrawable);
            //回收
            tta.recycle();
        }
    }

    private void setNumTvBackground(int numTvDrawable) {
        this.mNumTv.setBackgroundResource(numTvDrawable);
    }

    private void setSubBtnBackGround(Drawable subBtnDrawable) {
        this.mSubBtn.setBackground(subBtnDrawable);
    }

    public void setAddBtnBackGround(Drawable addBtnDrawable) {
        this.mAddBtn.setBackground(addBtnDrawable);
    }

    private void initView(){
        View view=mInflater.inflate(R.layout.weight_number_add_sub,
                this,true);
        mAddBtn= (Button) view.findViewById(R.id.add_btn);
        mSubBtn= (Button) view.findViewById(R.id.sub_btn);
        mNumTv= (TextView) view.findViewById(R.id.num_tv);

        mAddBtn.setOnClickListener(this);
        mSubBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.add_btn){//点击加
            numAdd();
            if(mOnButtonClickListener!=null){
                mOnButtonClickListener.onButtonAddClick(view,value);
            }
        }else if(view.getId()==R.id.sub_btn){//单击减
            numSub();
            if(mOnButtonClickListener!=null){
                mOnButtonClickListener.onButtonSubClick(view,value);
            }
        }
    }

    //增加
    private void numAdd(){
        //判断是否小于最大值
        if(value<maxValue){
            value++;
        }
        //设置值
        mNumTv.setText(value+"");
    }
    //减小
    private void numSub(){
        //判断是否大于最小值
        if(value>minValue){
            value--;
        }
        //设置值
        mNumTv.setText(value+"");
    }



    public interface OnButtonClickListener{
        void onButtonAddClick(View view,int value);//监听加
        void onButtonSubClick(View view,int value);//监听减
    }
}
