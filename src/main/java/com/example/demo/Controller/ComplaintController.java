package com.example.demo.Controller;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.Tfidf;
import com.example.demo.Repository.ComplaintRepository;
import com.example.demo.Repository.IdfRepository;
import com.example.demo.Repository.TfRepository;
import com.example.demo.Repository.TfidfRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Cloie Andrea on 15/07/2018.
 */

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/apithree")
//@ResponseBody
public class ComplaintController {

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    TfRepository tfRepository;

    @Autowired
    IdfRepository idfRepository;

    @Autowired
    TfidfRepository tfidfRepository;

    //Save the uploaded file to this folder

//    private static String UPLOADED_FOLDER = "C://School//Divulgo_Uploads//";
    private static String UPLOADED_FOLDER="C://Users//Cloie Andrea//IdeaProjects//GovtDivulgo//src//main//resources//static//images//divulgomobuploads//";
    public static byte[] readFileToByteArray(File file) throws IOException {
        return null;
    }

    @CrossOrigin(origins = {"http://172.30.14.116:8100","file://"})
    @PostMapping(value="/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam(name="ionicfile") MultipartFile file,
                                   @RequestParam(name="user_id") Long user_id,
                                   @RequestParam(name="user_complaint") String user_complaint,
                                   @RequestParam(name="lat") Double lat,
                                   @RequestParam(name="long") Double lng,
                                   Complaint complaint,
                                   RedirectAttributes redirectAttributes) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
//        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String dateee = formatter.format(date);
        String time = formatter2.format(date);
        System.out.println("-----------------FILE UPLOAD-----------------");

        complaint.setUserId(user_id);
        complaint.setDate(dateee);
        complaint.setTime(time);
        complaint.setUser_complaint(user_complaint);
        complaint.setUser_lat(lat);
        complaint.setUser_long(lng);
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return null;
        }

        try {

            byte[] bytes = file.getBytes();

//            String filepath = "C:/School/Divulgo_Uploads/"+file.getOriginalFilename();
            String filepath = "images/"+file.getOriginalFilename();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            complaint.setFile_path(filepath);
            complaintRepository.save(complaint);
//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            System.out.println("File Path: "+complaint.getFile_path());
            System.out.println("Complaint Id: "+complaint.getComplaint_id());
            System.out.println("Date: "+complaint.getDate());
            System.out.println("Time: "+complaint.getTime());
            System.out.println("Complaint: "+complaint.getUser_complaint());
            System.out.println("User Latitude: "+complaint.getUser_lat());
            System.out.println("User Longitude: "+complaint.getUser_long());
            System.out.println("User Id: "+complaint.getUserId());
            System.out.println("---------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get Complaints
    @CrossOrigin(origins = {"http://172.30.14.116:8100","file://"})
    @GetMapping("/complaints/{user_id}")
    public List<Complaint> getComplaintById(@PathVariable(value = "user_id") Long user_id) {
        System.out.println("------------DISPLAYING COMPLAINT-------------");
        System.out.println("RESULT: SUCCESSFULLY DISPLAYED");
        System.out.println("---------------------------------------------");
        return complaintRepository.findByUserId(user_id);
    }

    @RequestMapping("/submitTest")
    public void goSubmit(HttpServletRequest request) throws IOException {

        String content = request.getParameter("textarea-input");
        String agency = request.getParameter("agency");

//        article.setTitle(title);
//        article.setTitle(url);
//        article.setAgency(agency);
//        article.setContent(content);
//        articleService.save(article);
//        cleanContent(content);
        //stemming
        String[] words = content.replaceAll("[^a-zA-Z ]", "").split("\\s+");

        ArrayList<String> stemList = new ArrayList<>();

        for (String a : words) {
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem = stemmer.getCurrent();
            stemList.add(steem);
            System.out.println("stemmer: " + steem);
        }

        System.out.println("-----STEM WORDS");
        for (String s : stemList) {
            System.out.print(s + "->");
        }

        List<Tfidf> tfidf6 = tfidfRepository.findAll();
        Double love = 0.0;
        Double lra = 0.0;
        Double lto = 0.0;
        Double sss = 0.0;
        HashMap<String, Double> result = new HashMap<>();
        HashMap<String, Double> entry = new HashMap<>();
        for (int i = 0; i < tfidf6.size(); i++) {
            Tfidf tfidf = tfidfRepository.findByTfidfId(tfidf6.get(i).getTfidfId());

            if (stemList.contains(tfidf.getWord())) {
                if (tfidf.getAgency().equals("LTO")) {
                    lto = lto + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("LTO value computation" + lto);

                }
                if (tfidf.getAgency().equals("LRA")) {
                    lra = lra + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("LRA value computation" + lra);
                }
                if (tfidf.getAgency().equals("PAG-IBIG")) {
                    love = love + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("PAG-IBIG value computation" + love);
                }
                if (tfidf.getAgency().equals("SSS")) {
                    sss = sss + tfidf.getTfidfVal();
                    System.out.println(tfidf.getWord() + "<------>" + tfidf.getAgency());
                    System.out.println("SSS value computation" + sss);
                }

            }
            entry.put("LTO", lto);
            entry.put("LRA", lra);
            entry.put("PAG-IBIG", love);
            entry.put("SSS", sss);
        }
        result = maxVal(entry);

        System.out.println("-----------RESULT-------------");
        for (Map.Entry<String, Double> e : result.entrySet()) {
//            Test test = new Test();
//            Article article = new Article();
            if (e.getKey().equals(agency)) {
//                test.setAgency(e.getKey());
//                test.setContent(content);
//                testService.save(test);
                System.out.println("RESULT IS CORRECT");
                System.out.println("SCORE: " + e.getValue());
                System.out.println("AGENCY RESULT: " + e.getKey());
                System.out.println("DECLARED AGENCY: " + agency);

                //Addition to dataset
//                article.setAgency(agency);
//                article.setContent(content);
//                articleService.save(article);
//                cleanContent(content);
            } else {
//                test.setAgency(agency);
//                test.setContent(content);
//                testService.save(test);
                System.out.println("RESULT IS INCORRECT");
                System.out.println("SCORE: " + e.getValue());
                System.out.println("AGENCY RESULT: " + e.getKey());
                System.out.println("DECLARED AGENCY: " + agency);

                //Addition to dataset
//                article.setAgency(agency);
//                article.setContent(content);
//                articleService.save(article);
//                cleanContent(content);
            }
        }
    }
//        return "test";
//    }

    public HashMap<String, Double> maxVal(HashMap<String, Double> values){
        HashMap<String, Double> max = new HashMap<>();
        Double maxval = 0.0;
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if(entry.getValue()>maxval){
                maxval = entry.getValue();
            }
        }

        for (Map.Entry<String, Double> entry : values.entrySet())
            if(entry.getValue()==maxval)
                max.put(entry.getKey(),entry.getValue());
        return max;
    }


}


