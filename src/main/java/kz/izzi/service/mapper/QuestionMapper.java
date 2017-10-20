package kz.izzi.service.mapper;

import kz.izzi.domain.*;
import kz.izzi.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    

    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
