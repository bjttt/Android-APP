package cn.edu.bupt.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRGeneratorFragment extends Fragment {

    private EditText input;
    private Button generateButton;
    private ImageView output;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_generator, container, false);

        input = view.findViewById(R.id.et_input);
        generateButton = view.findViewById(R.id.btn_generate);
        output = view.findViewById(R.id.iv_output);

        generateButton.setOnClickListener(v -> {
            String inputValue = input.getText().toString();
            if(inputValue.isEmpty()){
                Toast.makeText(getActivity(), "请输入要生成二维码的内容", Toast.LENGTH_SHORT).show();
                return;
            }
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            try {
                Bitmap bitmap = toBitmap(qrCodeWriter.encode(inputValue, BarcodeFormat.QR_CODE, 200, 200));
                output.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });

        return view;
    }

    // 这个方法用于将 BitMatrix 转换为 Bitmap
    private Bitmap toBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        return bitmap;
    }
}
