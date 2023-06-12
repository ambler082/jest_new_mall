package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository aRepo;
	
	public void create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.aRepo.save(answer); //DB에 답변을 저장한다
	}
	
	 public Answer getAnswer(Integer id) {
	        Optional<Answer> answer = this.aRepo.findById(id);
	        if (answer.isPresent()) {
	            return answer.get();
	        } else {
	            throw new DataNotFoundException("answer not found");
	        }
	    }

	    public void modify(Answer answer, String content) {
	        answer.setContent(content);
	        answer.setModifyDate(LocalDateTime.now());
	        this.aRepo.save(answer);
	    }
	    
	    public void delete(Answer answer) {
	        this.aRepo.delete(answer);
	    }

	    public void vote(Answer answer, SiteUser siteUser) {
	        answer.getVoter().add(siteUser);
	        this.aRepo.save(answer);
	    }

}
