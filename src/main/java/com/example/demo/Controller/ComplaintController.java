package com.example.demo.Controller;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.Complaintr;
import com.example.demo.Entity.Stopwords;
import com.example.demo.Entity.Tfidf;
import com.example.demo.Repository.*;
import com.example.demo.Service.StopwordsService;
import com.example.demo.Service.TfidfService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Cloie Andrea on 15/07/2018.
 */
@SuppressWarnings("Duplicates")
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/apithree")
//@ResponseBody
public class ComplaintController {

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    TfidfService tfidfService;

    @Autowired
    TfidfRepository tfidfRepository;

    @Autowired
    StopwordsService stopwordsService;

    @Autowired
    ComplaintrRepository complaintrRepository;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER="C://Users//Cloie Andrea//IdeaProjects//Divulgo-master//src//main//resources//static//images//divulgomobuploads//";
    public static byte[] readFileToByteArray(File file) throws IOException {
        return null;
    }

    @CrossOrigin(origins = {"http://192.168.1.2:8100","file://"})
    @PostMapping(value="/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam(name="ionicfile") MultipartFile file,
                                   @RequestParam(name="user_id") Long user_id,
                                   @RequestParam(name="user_complaint") String user_complaint,
                                   @RequestParam(name="lat") Double lat,
                                   @RequestParam(name="long") Double lng,
                                   @RequestParam(name="address") String address,
                                   Complaint complaint,
                                   RedirectAttributes redirectAttributes) throws IOException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
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
        complaint.setAddress(address);
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return null;
        }
//        classifier(user_complaint);

        try {
            byte[] bytes = file.getBytes();
            String filepath = "images/divulgomobuploads/"+file.getOriginalFilename();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            complaint.setFile_path(filepath);


            String[] words = user_complaint.replaceAll("[^a-zA-Z ]", "").split("\\s+");
            ArrayList<String> stemList = new ArrayList<>();
            ArrayList<String> wordsList = new ArrayList<>();
            ArrayList<String> ngramsss = new ArrayList<String>();
//        ArrayList<String> wordsList = new ArrayList<String>();
            String regex = "[A-Z]+";
            Pattern r = Pattern.compile(regex);

            for (String word : words) {
//            System.out.println(word);
                wordsList.add(word);
            }

            Iterator<String> itr =wordsList.iterator();

            while (itr.hasNext()) {

                String w = itr.next();
                Matcher m = r.matcher(w);
                Stopwords sampleStopword = stopwordsService.findByStopwords(w);
                if (m.find()) {
                    itr.remove();
                }

                else if (sampleStopword != null) {
                    itr.remove();
                }
            }

            for (String a : wordsList) {
                PorterStemmer stemmer = new PorterStemmer();
                stemmer.setCurrent(a);
                stemmer.stem();
                String steem = stemmer.getCurrent();
                stemList.add(steem);
                System.out.println(steem);
            }

            String str = String.join(" ", stemList);
            for (int n = 1; n <=3; n++) {
                for (String ngram : ngrams(n, str)){
                    ngramsss.add(ngram);
                }
            }

            System.out.println("WORDS: ");
            for(String x : ngramsss){
                System.out.println(x);
            }
            System.out.println("------------");

            List<Tfidf> tfidf6 = tfidfService.findAll();
            Double love = 0.0;
            Double lra = 0.0;
            Double lto = 0.0;
            Double sss = 0.0;
            HashMap<String, Double> result = new HashMap<>();
            HashMap<String, Double> entry = new HashMap<>();

            for (int i = 0; i < ngramsss.size(); i++) {
                Tfidf tfidf1 = tfidfService.findByWordAndAgency(ngramsss.get(i),"LTO");
                Tfidf tfidf2 = tfidfService.findByWordAndAgency(ngramsss.get(i),"LRA");
                Tfidf tfidf3 = tfidfService.findByWordAndAgency(ngramsss.get(i),"PAG-IBIG");
                Tfidf tfidf4 = tfidfService.findByWordAndAgency(ngramsss.get(i),"SSS");

                if(tfidf1!=null){
                    lto = lto + tfidf1.getTfidfVal();
                    System.out.println("word: "+tfidf1.getWord());
                    System.out.println("tf = "+tfidf1.getTfVal());
                    System.out.println("idf = "+tfidf1.getIdfVal());
                    System.out.println("tf-idf = "+tfidf1.getTfidfVal());
                    System.out.println("lto computation: "+lto);
                    System.out.println("------------");
                }
                if(tfidf2!=null){
                    lra = lra + tfidf2.getTfidfVal();
                    System.out.println("word: "+tfidf2.getWord());
                    System.out.println("tf = "+tfidf2.getTfVal());
                    System.out.println("idf = "+tfidf2.getIdfVal());
                    System.out.println("tf-idf = "+tfidf2.getTfidfVal());
                    System.out.println("lra computation: "+lra);
                    System.out.println("------------");
                }
                if(tfidf3!=null){
                    love = love + tfidf3.getTfidfVal();
                    System.out.println("word: "+tfidf3.getWord());
                    System.out.println("tf = "+tfidf3.getTfVal());
                    System.out.println("idf = "+tfidf3.getIdfVal());
                    System.out.println("tf-idf = "+tfidf3.getTfidfVal());
                    System.out.println("love computation: "+love);
                    System.out.println("------------");
                }
                if(tfidf4!=null){
                    sss = sss + tfidf4.getTfidfVal();
                    System.out.println("word: "+tfidf4.getWord());
                    System.out.println("tf = "+tfidf4.getTfVal());
                    System.out.println("idf = "+tfidf4.getIdfVal());
                    System.out.println("tf-idf = "+tfidf4.getTfidfVal());
                    System.out.println("sss computation: "+sss);
                    System.out.println("------------");
                }

                entry.put("LTO", lto);
                entry.put("LRA", lra);
                entry.put("PAG-IBIG", love);
                entry.put("SSS", sss);
            }

            result = maxVal(entry);


            for (Map.Entry<String, Double> entryy : result.entrySet()) {
                System.out.println("Result: "+entryy.getKey()+" : "+entryy.getValue());
            }

            System.out.println("-----------RESULT-------------");
            for (Map.Entry<String, Double> e : result.entrySet()) {
                if(e.getKey()!=null) {
                    complaint.setAgency(e.getKey());
                }
                else{
                    complaint.setAgency("undefined");
                }
            }
            complaintRepository.save(complaint);
            System.out.println("File Path: "+complaint.getFile_path());
            System.out.println("Complaint Id: "+complaint.getComplaint_id());
            System.out.println("Date: "+complaint.getDate());
            System.out.println("Time: "+complaint.getTime());
            System.out.println("Complaint: "+complaint.getUser_complaint());
            System.out.println("User Latitude: "+complaint.getUser_lat());
            System.out.println("User Longitude: "+complaint.getUser_long());
            System.out.println("User Id: "+complaint.getUserId());
            System.out.println("Agency: "+complaint.getAgency());
            System.out.println("---------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Complaints
    @CrossOrigin(origins = {"http://192.168.1.2:8100","file://"})
    @GetMapping("/complaints/{user_id}")
    public List<Complaint> getComplaintById(@PathVariable(value = "user_id") Long user_id) {
        System.out.println("------------DISPLAYING COMPLAINT-------------");
        System.out.println("RESULT: SUCCESSFULLY DISPLAYED");
        System.out.println("---------------------------------------------");
        return complaintRepository.findByUserId(user_id);
    }

    // Get Complaints
    @CrossOrigin(origins = {"http://192.168.1.2:8100","file://"})
    @GetMapping("/replies/{user_id}")
    public List<Complaintr> getReplies(@PathVariable(value = "user_id") Long user_id) {
        System.out.println("------------DISPLAYING REPLIES-------------");
        System.out.println("RESULT: SUCCESSFULLY DISPLAYED");
        System.out.println("---------------------------------------------");
//        return replyRepository.findByUserId(user_id);
        return complaintrRepository.findByUserid(user_id);
//        return complaintRepository.findByUserId(user_id);
    }

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


    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));

        return ngrams;
    }
    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
}


