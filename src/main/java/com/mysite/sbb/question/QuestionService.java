package com.mysite.sbb.question;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository qRepo;
	
	//모든 질문을 검색하여 리스트로 리턴
	public Page<Question> getList(int page){
		//현재 페이지와 페이지당 10개를 입력하여 가져올 데이터를 pageable에 설정
		Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending());
		return this.qRepo.findAll(pageable); //현재 페이지에 표시할 데이터 가져옴
	}
	
    public Question getQuestion(Integer id) {  
        Optional<Question> question = this.qRepo.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    
    // 질문을 저장한다
    public void create(String subject, String content, SiteUser siteUser,MultipartFile file) throws Exception{
        Question q = new Question();
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        String filename = UUID.randomUUID().toString() + file.getOriginalFilename();
        File saveFile = new File(projectPath, filename);
        file.transferTo(saveFile);
        // Process the uploaded file (e.g., save it to a directory or perform further operations)
        if (!file.isEmpty()) {
            // Process the file here
            q.setFilename(filename);  
            q.setFilepath("/files/"+filename);
        }
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(siteUser);
        this.qRepo.save(q);
    }
    
    //질문을 수정한다.
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.qRepo.save(question);
    }
    
    //질문을 삭제한다.
    public void delete(Question question) {
        this.qRepo.delete(question);
    }
    
    //추천인을 저장하는 서비스
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser); //set리스트에 추천 유저 추가
        this.qRepo.save(question); // 추천유저가 업데이트
    }
}









