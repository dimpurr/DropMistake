package bupt.dropmistake.tool;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.meetsl.scardview.SCardView;
import com.squareup.picasso.Picasso;

import bupt.dropmistake.R;

public class ProblemAdapter extends ArrayAdapter<Problem> {

    public ProblemAdapter(Context context) {
        super(context, -1);
    }

    @NonNull
    @Override
    public View getView(int position, View container, ViewGroup parent) {
        if (container == null) {
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            container = inflater.inflate(R.layout.list_problem, null);
        }
        final Problem entry = getItem(position);

        TextView v = null;
        v = (TextView) container.findViewById(R.id.list_title);
        v.setText("错题" + String.valueOf(position + 1) + "--" + entry.getMode());
        v = (TextView) container.findViewById(R.id.list_date);
        v.setText(entry.getDate());
        v = (TextView) container.findViewById(R.id.list_diff);
        v.setText("  " + String.valueOf(entry.getDifficulty()));
        v = (TextView) container.findViewById(R.id.list_kp);
        v.setText(entry.getKlgStr());
        ImageView img = (ImageView) container.findViewById(R.id.list_proimg);
        Picasso.with(getContext()).load(entry.getProblemURL()).into(img);
        ImageView ans = (ImageView) container.findViewById(R.id.ans);
        Picasso.with(getContext()).load(entry.getAnswerURL()).into(ans);

        Button seeAns = (Button) container.findViewById(R.id.pro_see_ans);
        Button remove = (Button) container.findViewById(R.id.remove_btn);
        SCardView sCardView = (SCardView) container.findViewById(R.id.bookcard);
        seeAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DMINFO", "ProblemAdapter.seeANS设置监听函数");
                if (ans.getVisibility() == View.GONE) {
                    Toast.makeText(getContext(), "显示解析", Toast.LENGTH_LONG).show();
                    ans.setVisibility(View.VISIBLE);

                    seeAns.setText("隐藏解析");
                    return;
                } else {
                    Toast.makeText(getContext(), "收起解析", Toast.LENGTH_LONG).show();
                    ans.setVisibility(View.GONE);
                    seeAns.setText("查看解析");
                    return;
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DMINFO", "ProblemAdapter.remove监听函数");
                Toast.makeText(getContext(), remove.getText().toString(), Toast.LENGTH_LONG).show();

                //数据库操作
                Neo neo = new Neo();
                String result = neo.removeFromBook(String.valueOf(entry.getId()));
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                Log.i("DMINFO", result);
                sCardView.setVisibility(View.GONE);
                try {
                    neo.close();
                    neo = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return container;
    }

    private void click(View v) {
        //Log.i("DMINFO","内部的按钮"+problemDataArrayList.get((Integer)v.getTag())+"查看解析被点击，位置："+v.getTag());
        Toast.makeText(getContext(), getCount() + "被点击", Toast.LENGTH_LONG).show();
    }


}
