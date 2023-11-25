package com.example.qrcodeboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Controller {
    @Autowired
    private Repository repository;
    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("create/{text}")
    public ResponseEntity<?> createProject(@PathVariable(name = "text")String text){
        Project project = new Project();
        project.setQr(qrCodeService.generateQRCode(text));
        repository.save(project);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{projectId}/qrcode")
    public ResponseEntity<byte[]> getProjectQRCode(@PathVariable(name = "projectId") Integer projectId) {
        byte[] qrCodeBytes = qrCodeService.getProjectQRCode(projectId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeBytes.length);
        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/scan")
    public ResponseEntity<?> scanQRCode() {
        return qrCodeService.scanQRCode();
    }
}
