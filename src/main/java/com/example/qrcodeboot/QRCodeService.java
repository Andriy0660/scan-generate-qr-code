package com.example.qrcodeboot;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {
    @Autowired
    private Repository repository;

    public String generateQRCode(String qrCodeData) {
        try {
            int width = 200;
            int height = 200;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", outputStream);
            byte[] qrCodeBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrCodeBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] getProjectQRCode(Integer projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return  Base64.getDecoder().decode(project.getQr());
    }
    public ResponseEntity<?> scanQRCode() {
        Webcam webcam = Webcam.getDefault();
        if(!webcam.close())webcam.close();
        try {
            long startTime = System.currentTimeMillis();
            long maxSearchTime = 2000;
            webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
            while (System.currentTimeMillis() - startTime < maxSearchTime) {
                Result result = null;
                BufferedImage image = null;

                if ((image = webcam.getImage()) == null) {
                    continue;
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = new QRCodeReader().decode(bitmap);
                } catch (NotFoundException e) {
                }

                if (result != null) {
                    webcam.close();
                    boolean success = true;
                    String qrCodeContent = result.getText();

                    Map<String, Object> response = new HashMap<>();
                    response.put("success", success);
                    response.put("qrCodeContent", qrCodeContent);

                    return ResponseEntity.ok(response);
                }
            }
            webcam.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = false;
        if(webcam!=null)webcam.close();
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return ResponseEntity.ok(response);
    }
}
