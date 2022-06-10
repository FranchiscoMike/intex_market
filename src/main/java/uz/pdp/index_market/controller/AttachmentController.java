package uz.pdp.index_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.index_market.entity.Attachment;
import uz.pdp.index_market.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachment")
public class AttachmentController {

    private final AttachmentRepository attachmentRepository;
    private static final String uploadingDirectory = "src/main/resources/uploaded_files";



    // upload to the system :
    @PostMapping("/system_upload")
    public String system_upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        String fileName = fileNames.next();

        MultipartFile file = request.getFile(fileName);
        assert file != null;
        long size = file.getSize();
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        Attachment attachment = new Attachment();
        attachment.setSize(size);
        attachment.setFileOriginalName(originalFilename);
        attachment.setContentType(contentType);
        String[] split = originalFilename.split("\\.");
        String name = UUID.randomUUID() + "." + split[split.length - 1];
        attachment.setName(name); // fayl nomi saqlanadi

        Attachment save = attachmentRepository.save(attachment);
        // serverga yuklash

//        Path path = Path.of(uploadingDirectory + "/" + name);
        String s = uploadingDirectory + "/" + name;
        Files.copy(file.getInputStream(), Path.of(s));

        return "saqlandi id : " + save.getId();

    }

    /**
     * getting file from system why ? photos do not take more space from system and more fast than DB connection
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/system_download/{id}")
    public void getFilesfromsystem(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            // attachmentga nom  o'rnatish yani uni kop'chirib olsa bo'ladimi yo'qmi
            // faqat ko'rish uchunmi yoki :

            // fayl nomi
            response.setHeader("Content-Disposition",
                    "attachment; filename = \""
                            + attachment.getFileOriginalName() + "\"");

            //type:
            response.setContentType(attachment.getContentType());// nima bo'lasa shuni ayatadi
            // content :
            FileInputStream fileInputStream = new FileInputStream(uploadingDirectory + "/" + attachment.getName());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());

        }else
            System.out.println("not found");
    }
}
