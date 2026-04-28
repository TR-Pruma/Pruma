package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.dto.update.FeedbackUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {
    FeedbackResponseDTO create(FeedbackRequestDTO dto);
    FeedbackResponseDTO getById(Integer id);
    List<FeedbackResponseDTO> listAll();
    Page<FeedbackResponseDTO> list(Pageable pageable);
    FeedbackResponseDTO update(Integer id, FeedbackUpdateDTO dto);
    void delete(Integer id);
}
