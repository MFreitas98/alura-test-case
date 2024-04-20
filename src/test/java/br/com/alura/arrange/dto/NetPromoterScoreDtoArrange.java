package br.com.alura.arrange.dto;

import br.com.alura.model.dto.NetPromoterScoreDto;

import java.util.ArrayList;
import java.util.List;

public class NetPromoterScoreDtoArrange {

    private NetPromoterScoreDtoArrange() {

    }

    public static List<NetPromoterScoreDto> getNetPromoterScoreDtoList() {

        List<NetPromoterScoreDto> netPromoterScoreList = new ArrayList<>();
        netPromoterScoreList.add(NetPromoterScoreDto.builder().courseCode("java-adv").score(8).build());
        netPromoterScoreList.add(NetPromoterScoreDto.builder().courseCode("junit-adv").score(9).build());

        return netPromoterScoreList;
    }
}
