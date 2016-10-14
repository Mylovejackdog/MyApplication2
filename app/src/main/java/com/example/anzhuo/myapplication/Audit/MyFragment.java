package com.example.anzhuo.myapplication.Audit;

        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.example.anzhuo.myapplication.Infor.FragmentInfo;
        import com.example.anzhuo.myapplication.R;
        import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class MyFragment extends Fragment {
    FragmentInfo fragmentInfo=new FragmentInfo();
    //com.facebook.drawee.view.SimpleDraweeView iv_photo;
    ImageView iv_photo;
    TextView tv_from;
    ImageView iv_praise;
    int i=0;
    public MyFragment(FragmentInfo fragmentInfo){
        this.fragmentInfo=fragmentInfo;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.audit_my_layout,null);
        iv_photo= (ImageView) view.findViewById(R.id.iv_photo);
       // iv_photo= (SimpleDraweeView) view.findViewById(R.id.iv_photo);
        tv_from= (TextView) view.findViewById(R.id.tv_from);
        iv_praise= (ImageView) view.findViewById(R.id.iv_praise);
        Uri uri=Uri.parse(fragmentInfo.getPhoto());
        Glide.with(this).load(fragmentInfo.getPhoto()).asBitmap().override(1000,600).fitCenter().into(iv_photo);
        iv_praise.setImageResource(fragmentInfo.getPraise());
        tv_from.setText(fragmentInfo.getConment());
        iv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i%2==0){
                    Toast.makeText(getActivity(),"您取消了点赞",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"您点赞了",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
