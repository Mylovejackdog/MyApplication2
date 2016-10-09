package com.example.anzhuo.myapplication.ReflashListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Home.TextFragment;
import com.example.anzhuo.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class ReflashListView extends ListView implements AbsListView.OnScrollListener {
    View header; // 顶部布局文件
    View footer; // 底部布局文件
    int headerHeight; //顶部布局文件的高度
    int firstVisibleItem; //当前第一个可见的item的位置
    boolean isRemark;// 标记，当前是在listview最顶端按下的
    int startY;// 按下时的Y的位置
    int state; //当前状态
    final int NONE = 0;// 正常状态
    final int PULL = 1;// 提示下拉刷新状态
    final int RELESE = 2;// 提示释放状态
    final int REFLASHING = 3;// 正在刷新状态
    int scrollState; // listview 当前滚动的状态
    int lastVisibleItem;
    int totalItemCount;
    boolean isLoad;
    IReflashListener iReflashListener;

    public ReflashListView(Context context) {
        super(context);
        initView(context);
    }

    public ReflashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ReflashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    // 初始化界面，添加顶部布局到listview里面
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.home_text_listview_header_layout, null);
        footer = inflater.inflate(R.layout.home_text_listview_footer_layout, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        footer.findViewById(R.id.ll_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.addHeaderView(header);
        this.setOnScrollListener(this);

    }

    // 通知父布局，占用的宽度和高度

    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
            int height;
            int tempHeight = p.height;
            if (tempHeight > 0) {
                height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
            } else {
                height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            view.measure(width, height);
        }

    }

    // 设置header布局 上边距
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
             if(!isLoad) {
                 footer.findViewById(R.id.ll_layout).setVisibility(View.VISIBLE);
                 //加载更多
                 iReflashListener.onLoad();
                 isLoad=true;
             }
            }



    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    // 加载最新数据
                    iReflashListener.onReflash();
                    reflashViewByState();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();

                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }

    //  根据当前的状态来显示界面
    private void reflashViewByState() {
        TextView tv_fresh = (TextView) header.findViewById(R.id.tv_fresh);
        ImageView iv_arrow = (ImageView) header.findViewById(R.id.iv_arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
        RotateAnimation rotate = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setFillAfter(true);
        RotateAnimation rotate1 = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate1.setDuration(500);
        rotate1.setFillAfter(true);
        switch (state) {
            case NONE:
                topPadding(-headerHeight);
                iv_arrow.clearAnimation();
                break;
            case PULL:
                iv_arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tv_fresh.setText("下拉可以刷新");
                iv_arrow.clearAnimation();
                iv_arrow.setAnimation(rotate1);
                break;
            case RELESE:
                iv_arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tv_fresh.setText("松开可以刷新");
                iv_arrow.clearAnimation();
                iv_arrow.setAnimation(rotate);
                break;
            case REFLASHING:
                topPadding(50);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tv_fresh.setText("正在刷新");
                break;
        }
    }

    //   获取完数据
    public void reflashComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
        TextView time = (TextView) header.findViewById(R.id.tv_time);
        TextView last = (TextView) header.findViewById(R.id.tv_last);
        Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
        last.setText("上次刷新时间：");
        time.setText(ReflashCompleteTime());
    }

    //加载完数据
    public void onLoadComplete() {
        footer.findViewById(R.id.ll_layout).setVisibility(View.GONE);
        isLoad=false;

    }

    public void setInterface(IReflashListener iReflashListener) {
        this.iReflashListener = iReflashListener;
    }

    public String ReflashCompleteTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newtime = format.format(System.currentTimeMillis());
        return newtime;
    }


    // 刷新数据接口
    public interface IReflashListener {
        public void onReflash();

        public void onLoad();

    }
}