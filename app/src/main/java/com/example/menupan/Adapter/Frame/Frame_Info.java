package com.example.menupan.Adapter.Frame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menupan.R;

public class Frame_Info extends Fragment {

    private View view;
    ImageButton store_info;
    TextView infoText;

    public static Frame_Info newInstance(){
        Frame_Info frame_info = new Frame_Info();
        return frame_info;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frame_frag_info, container, false);

        store_info = view.findViewById(R.id.store_call);
        store_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678"));
                startActivity(intent);
            }
        });

        infoText = view.findViewById(R.id.info_text);
        infoText.setText("맛집은 당연히 브리또인");

        return view;
    }
}
