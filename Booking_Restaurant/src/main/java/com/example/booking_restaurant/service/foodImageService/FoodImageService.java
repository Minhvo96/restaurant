package com.example.booking_restaurant.service.foodImageService;

import com.cloudinary.Cloudinary;
import com.example.booking_restaurant.domain.FoodImage;
import com.example.booking_restaurant.repository.FoodImageRepository;
import com.example.booking_restaurant.util.UploadUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@Service
@AllArgsConstructor
@Transactional
public class FoodImageService {
    private final Cloudinary cloudinary;

    private final FoodImageRepository fileRepository;

    private final UploadUtil uploadUtil;
    public FoodImage saveAvatar(MultipartFile avatar) throws IOException {
        var file = new FoodImage();
        fileRepository.save(file);

        var uploadResult = cloudinary.uploader().upload(avatar.getBytes(), uploadUtil.buildImageUpLoadParams(file));

        String fileUrl = (String) uploadResult.get("secure_url");
        String fileFormat = (String) uploadResult.get("format");

        file.setFileName(file.getId() + "." + fileFormat);
        file.setFileUrl(fileUrl);
        file.setFileFolder(UploadUtil.IMAGE_UPLOAD_FOLDER);
        file.setCloudId(file.getFileFolder() + "/" + file.getId());

        fileRepository.save(file);
        return file;
    }

    public void delete(String fileUrl) {
        fileRepository.deleteBookImageByFileUrl(fileUrl);
    }

    public void deleteById(String id) {
        fileRepository.deleteById(id);
    }
}
