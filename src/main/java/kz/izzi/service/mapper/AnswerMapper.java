package kz.izzi.service.mapper;

import kz.izzi.domain.*;
import kz.izzi.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO toDto(Answer answer); 

    @Mapping(source = "questionId", target = "question")
    @Mapping(target = "filters", ignore = true)
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
