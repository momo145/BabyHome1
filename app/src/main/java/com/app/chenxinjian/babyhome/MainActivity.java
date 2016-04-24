package com.app.chenxinjian.babyhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.chenxinjian.babyhome.activitys.CameraActivity;
import com.app.chenxinjian.babyhome.activitys.RecordActivity;
import com.app.chenxinjian.babyhome.activitys.base.BaseActivity;
import com.app.chenxinjian.babyhome.event.MessageEvent;
import com.gc.materialdesign.views.ButtonFloat;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends BaseActivity {

    @ViewInject(R.id.btn_record)
    ButtonFloat btn_record;
    @ViewInject(R.id.btn_takePhotos)
    ButtonFloat btn_takePhotos;
    @ViewInject(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
    }

    /**
     * 1. 方法必须私有限定,
     * 2. 方法参数形式必须和type对应的Listener接口一致.
     * 3. 注解参数value支持数组: value={id1, id2, id3}
     **/
    @Event(value = {R.id.btn_record, R.id.btn_takePhotos}, type = View.OnClickListener.class)
    private void ClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_record:
                startActivity(new Intent(getApplicationContext(), RecordActivity.class));
                break;
            case R.id.btn_takePhotos:
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
                break;
        }
    }


    @Override
    protected void init() {

    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void unRegisterListener() {

    }

    @Override
    public void onEventMainThread(MessageEvent event) {

    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class Holder extends RecyclerView.ViewHolder {


            public Holder(View view) {
                super(view);
            }
        }
    }

}
