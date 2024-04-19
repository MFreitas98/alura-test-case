package br.com.alura.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NetPromoterScoreDto {

    private String courseName;

    private String courseCode;

    private double score;
}
